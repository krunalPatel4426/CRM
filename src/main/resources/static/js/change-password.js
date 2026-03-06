$('#changePasswordForm').on('submit', function(e) {
    e.preventDefault();

    const newPassword = $('#newPassword').val();
    const confirmPassword = $('#confirmPassword').val();

    if (newPassword !== confirmPassword) {
        Swal.fire('Error', 'Passwords do not match!', 'error');
        return;
    }

    const csrfToken = $("meta[name='_csrf']").attr("content");
    const csrfHeader = $("meta[name='_csrf_header']").attr("content");
    const $btn = $('#submitBtn');

    $btn.prop('disabled', true).text('Updating...');

    $.ajax({
        url: '/api/v1/auth/change-password',
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify({ newPassword: newPassword, confirmPassword: confirmPassword }),
        beforeSend: function(xhr) { xhr.setRequestHeader(csrfHeader, csrfToken); },
        success: function(response) {
            if(response.success === "true") {
                Swal.fire({
                    title: 'Success!',
                    text: response.message,
                    icon: 'success',
                    confirmButtonText: 'Go to Login',
                    allowOutsideClick: false // Force them to click the button
                }).then(() => {
                    // THE FIX: Submit the hidden POST form instead of a GET redirect
                    $('#logoutForm').submit();
                });
            } else {
                Swal.fire('Error', response.message, 'error');
                $btn.prop('disabled', false).text('Update Password');
            }
        },
        error: function() {
            Swal.fire('Error', 'Failed to update password. Please try again.', 'error');
            $btn.prop('disabled', false).text('Update Password');
        }
    });
});