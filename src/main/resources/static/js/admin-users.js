$(document).ready(function() {
    fetchSalesAgents();

    $('#refreshAgentsBtn').on('click', function() {
        fetchSalesAgents();
    });
});

const csrfToken = $("meta[name='_csrf']").attr("content");
const csrfHeader = $("meta[name='_csrf_header']").attr("content");

function fetchSalesAgents() {
    const $tableBody = $('#agentsTableBody');
    $tableBody.html('<tr><td colspan="4" class="text-center text-muted">Loading agents...</td></tr>');

    $.ajax({
        url: '/api/v1/admin/users/sales',
        type: 'GET',
        contentType: 'application/json',
        success: function(response) {
            if (response.success === "true") {
                populateAgentsTable(response.data);
            } else {
                Swal.fire('Error', response.message, 'error');
                $tableBody.html('<tr><td colspan="4" class="text-center text-danger">Failed to load data.</td></tr>');
            }
        },
        error: function() {
            Swal.fire('Error', 'Network error or unauthorized access.', 'error');
            $tableBody.html('<tr><td colspan="4" class="text-center text-danger">Network error.</td></tr>');
        }
    });
}

function populateAgentsTable(agentsData) {
    const $tableBody = $('#agentsTableBody');
    $tableBody.empty();

    if (!agentsData || agentsData.length === 0) {
        $tableBody.html('<tr><td colspan="4" class="text-center text-muted">No sales agents found.</td></tr>');
        return;
    }

    $.each(agentsData, function(index, agent) {
        const row = `
            <tr>
                <td>${agent.id}</td>
                <td class="fw-bold">${agent.username}</td>
                <td>${agent.email}</td>
                <td>
                    <button class="btn btn-sm btn-outline-danger" onclick="deactivateAgent(${agent.id})">Deactivate</button>
                </td>
            </tr>
        `;
        $tableBody.append(row);
    });
}

function openAddAgentModal() {
    $('#agentUsername').val('');
    $('#agentEmail').val('');
    $('#agentPassword').val('');
    $('#agentModal').modal('show');
}

$('#saveAgentBtn').on('click', function() {
    const usernameVal = $('#agentUsername').val().trim();
    const emailVal = $('#agentEmail').val().trim();
    const passwordVal = $('#agentPassword').val().trim();

    // Validation check
    if (!usernameVal || !emailVal || !passwordVal) {
        Swal.fire({
            icon: 'warning',
            title: 'Missing Information',
            text: 'Please fill in all fields (Username, Email, and Password).',
            confirmButtonColor: '#3085d6'
        });
        return;
    }

    const payload = {
        username: usernameVal,
        email: emailVal,
        password: passwordVal
    };

    $.ajax({
        url: '/api/v1/admin/users/add-sales',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(payload),
        beforeSend: function(xhr) { xhr.setRequestHeader(csrfHeader, csrfToken); },
        success: function(response) {
            if(response.success === "true") {
                $('#agentModal').modal('hide');
                Swal.fire('Created!', response.message, 'success');
                fetchSalesAgents();
            } else {
                Swal.fire('Error', response.message, 'error');
            }
        },
        error: function(xhr) {
            Swal.fire('Error', 'Failed to create sales agent.', 'error');
        }
    });
});

function deactivateAgent(id) {
    Swal.fire({
        title: 'Are you sure?',
        text: "This will deactivate the sales agent and immediately prevent them from logging in.",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Yes, deactivate them!'
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                url: `/api/v1/admin/users/${id}`,
                type: 'DELETE',
                beforeSend: function(xhr) { xhr.setRequestHeader(csrfHeader, csrfToken); },
                success: function(response) {
                    if(response.success === "true") {
                        Swal.fire('Deactivated!', response.message, 'success');
                        // Refresh the table so the deactivated user disappears from the active list
                        fetchSalesAgents();
                    } else {
                        Swal.fire('Error', response.message, 'error');
                    }
                },
                error: function() {
                    Swal.fire('Error', 'Failed to deactivate agent.', 'error');
                }
            });
        }
    });
}