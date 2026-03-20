$(document).ready(function() {

    // Setup CSRF tokens for the AJAX request
    const csrfToken = $("meta[name='_csrf']").attr("content");
    const csrfHeader = $("meta[name='_csrf_header']").attr("content");

    // 1. Extract UTM parameters from the browser's URL bar
    const urlParams = new URLSearchParams(window.location.search);
    const utmSource = urlParams.get('utm_source');
    const utmMedium = urlParams.get('utm_medium');
    const utmCampaign = urlParams.get('utm_campaign');

    // Optional: If someone visits the page without UTM params, you could redirect them or show an error.
    // However, usually, we just let them submit and handle missing UTMs in the backend if necessary.

    // 2. Handle Form Submission
    $('#enquiryForm').on('submit', function(e) {
        e.preventDefault();

        // Get user input
        const customerName = $('#customerName').val().trim();
        const phoneNumber = $('#phoneNumber').val().trim();

        // Basic validation for the "dumb user"
        if (!customerName || !phoneNumber) {
            Swal.fire('Warning', 'Please enter your name and phone number.', 'warning');
            return;
        }

        if(phoneNumber.length != 10){
            Swal.fire('Warning', 'Please enter a valid phone number.', 'warning');
            return;
        }

        // Create the JSON payload that matches your LeadRequestDto
        const payload = {
            customerName: customerName,
            phoneNumber: phoneNumber
        };

        // Construct the API URL by appending the UTM parameters we extracted earlier
        // We use encodeURIComponent to ensure special characters in UTMs don't break the URL
        const apiUrl = `http://localhost:8080/api/free/enquiry?utm_source=${encodeURIComponent(utmSource || '')}&utm_medium=${encodeURIComponent(utmMedium || '')}&utm_campaign=${encodeURIComponent(utmCampaign || '')}`;

        // Disable button to prevent double-submissions
        $('#submitBtn').prop('disabled', true).text('Submitting...');

        // 3. Send data to REST API
        $.ajax({
            url: apiUrl,
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(payload),
            beforeSend: function(xhr) {
                if(csrfHeader && csrfToken) {
                    xhr.setRequestHeader(csrfHeader, csrfToken);
                }
            },
            success: function(response) {
                if(response.success === "true") {
                    // Success!
                    // The backend has successfully saved the lead, triggered Round Robin, and evicted the Redis cache.
                    Swal.fire({
                        title: 'Thank You!',
                        text: 'Your enquiry has been submitted. Our team will reach out to you shortly.',
                        icon: 'success',
                        confirmButtonText: 'Okay'
                    }).then(() => {
                        // Clear the form after successful submission
                        $('#enquiryForm')[0].reset();
                        $('#submitBtn').prop('disabled', false).text('Submit Enquiry');
                    });
                } else {
                    Swal.fire('Error', response.message, 'error');
                    $('#submitBtn').prop('disabled', false).text('Submit Enquiry');
                }
            },
            error: function(xhr) {
                // Catch backend InvalidArgumentException (e.g., if UTM params are wrong/missing)
                let errorMsg = "An unexpected error occurred. Please try again later.";
                if(xhr.responseJSON && xhr.responseJSON.message) {
                    errorMsg = xhr.responseJSON.message;
                }
                Swal.fire('Submission Failed', errorMsg, 'error');
                $('#submitBtn').prop('disabled', false).text('Submit Enquiry');
            }
        });
    });
});