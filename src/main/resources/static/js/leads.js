$(document).ready(function() {
    fetchLeadsAjax();

    $('#refreshBtn').on('click', function() {
        fetchLeadsAjax();
    });
});

function fetchLeadsAjax() {
    const $tableBody = $('#leadsTableBody');
    $tableBody.html('<tr><td colspan="5" class="text-center text-muted">Loading leads...</td></tr>');

    $.ajax({
        url: '/api/v1/leads',
        type: 'GET',
        contentType: 'application/json',
        success: function(apiResponse) {
            console.log(apiResponse)
            if (apiResponse.success === "true") {
                populateTable(apiResponse.data);
            } else {
                showError(apiResponse.message);
                $tableBody.html('<tr><td colspan="5" class="text-center text-danger">Failed to load data.</td></tr>');
            }
        },
        error: function(xhr, status, error) {
            console.error("AJAX Error:", error);
            console.error("AJAX status:", status);
            console.error("AJAX xhr:", xhr);
            showError("A network error occurred or your session expired. Please try logging in again.");
            $tableBody.html('<tr><td colspan="5" class="text-center text-danger">Network error.</td></tr>');
        }
    });
}

function populateTable(leadsData) {
    const $tableBody = $('#leadsTableBody');
    $tableBody.empty();

    if (!leadsData || leadsData.length === 0) {
        $tableBody.html('<tr><td colspan="6" class="text-center text-muted">No leads assigned to you yet.</td></tr>');
        return;
    }

    $.each(leadsData, function(index, lead) {
        let badgeClass = 'bg-secondary';
        if (lead.callStatus === 'NEW') badgeClass = 'bg-primary';
        else if (lead.callStatus === 'DIALING') badgeClass = 'bg-warning text-dark';
        else if (lead.callStatus === 'COMPLETED') badgeClass = 'bg-success';

        // Style the Lead Source badge (Grey for manual, Info/Blue for campaigns)
        let sourceBadgeClass = lead.leadSourceName === 'Manual/Direct' ? 'bg-secondary' : 'bg-info text-dark';

        const row = `
        <tr>
            <td>${lead.id}</td>
            <td class="fw-bold">${lead.customerName}</td>
            <td>${lead.phoneNumber}</td>
            <td><span class="badge ${sourceBadgeClass}">${lead.leadSourceName}</span></td>
            <td><span class="badge ${badgeClass}">${lead.callStatus}</span></td>
            <td>
                <button class="btn btn-sm btn-outline-success me-1" onclick="initiateCall(${lead.id})">Call</button>
                <button class="btn btn-sm btn-outline-primary me-1" onclick="openEditModal(${lead.id}, '${lead.customerName}', '${lead.phoneNumber}', '${lead.callStatus}')">Edit</button>
                <button class="btn btn-sm btn-outline-danger" onclick="deleteLead(${lead.id})">Delete</button>
            </td>
        </tr>
    `;
        $tableBody.append(row);
    });
}

function showError(message) {
    Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: message,
        confirmButtonColor: '#d33'
    });
}

function initiateCall(leadId) {
    Swal.fire({
        icon: 'info',
        title: 'Initiate Call',
        text: 'Call initiation for Lead ID ' + leadId + ' will be implemented next with Redis rate-limiting!',
        confirmButtonColor: '#3085d6'
    });
}

// --- MODAL & CRUD LOGIC ---

const csrfToken = $("meta[name='_csrf']").attr("content");
const csrfHeader = $("meta[name='_csrf_header']").attr("content");

function openAddModal() {
    $('#leadId').val('');
    $('#customerName').val('');
    $('#phoneNumber').val('');
    $('#callStatus').val('NEW');
    $('#leadModalLabel').text('Add New Lead');
    $('#leadModal').modal('show');
}

function openEditModal(id, name, phone, status) {
    $('#leadId').val(id); //document.getElementById("leadId").innerHtml = id;
    $('#customerName').val(name);
    $('#phoneNumber').val(phone);
    $('#callStatus').val(status);
    $('#leadModalLabel').text('Edit Lead');
    $('#leadModal').modal('show');
}

$('#saveLeadBtn').on('click', function() {
    const leadId = $('#leadId').val();

    const customerNameVal = $('#customerName').val().trim();
    const phoneNumberVal = $('#phoneNumber').val().trim();
    const callStatusVal = $('#callStatus').val();

    if (!customerNameVal || !phoneNumberVal) {
        Swal.fire({
            icon: 'warning',
            title: 'Missing Information',
            text: 'Please fill in both the Customer Name and Phone Number.',
            confirmButtonColor: '#3085d6'
        });
        return;
    }

    const isUpdate = leadId !== "";

    const payload = {
        customerName: customerNameVal,
        phoneNumber: phoneNumberVal,
        callStatus: callStatusVal
    };

    const url = isUpdate ? `/api/v1/leads/${leadId}` : '/api/v1/leads';
    const methodType = isUpdate ? 'PUT' : 'POST';

    $.ajax({
        url: url,
        type: methodType,
        contentType: 'application/json',
        data: JSON.stringify(payload),
        beforeSend: function(xhr) { xhr.setRequestHeader(csrfHeader, csrfToken); },
        success: function(response) {
            if(response.success === "true") {
                $('#leadModal').modal('hide');
                Swal.fire('Success!', response.message, 'success');
                fetchLeadsAjax();
            } else {
                showError(response.message);
            }
        },
        error: function() { showError("Failed to save lead."); }
    });
});

function deleteLead(id) {
    Swal.fire({
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                url: `/api/v1/leads/${id}`,
                type: 'DELETE',
                beforeSend: function(xhr) { xhr.setRequestHeader(csrfHeader, csrfToken); },
                success: function(response) {
                    if(response.success === "true") {
                        Swal.fire('Deleted!', 'The lead has been removed.', 'success');
                        fetchLeadsAjax();
                    } else {
                        showError(response.message);
                    }
                },
                error: function() { showError("Failed to delete lead."); }
            });
        }
    });
}