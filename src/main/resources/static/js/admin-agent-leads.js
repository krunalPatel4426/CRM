$(document).ready(function() {

    // 1. Extract parameters from the URL
    const urlParams = new URLSearchParams(window.location.search);
    const agentId = urlParams.get('id');
    const agentName = urlParams.get('name');

    if (!agentId) {
        Swal.fire('Error', 'No Agent ID provided.', 'error').then(() => {
            window.location.href = '/admin/users';
        });
        return;
    }

    // Set the display name on the page
    if(agentName) {
        $('#agentNameDisplay').text(decodeURIComponent(agentName));
    } else {
        $('#agentNameDisplay').text("ID #" + agentId);
    }

    // 2. Initial Fetch
    fetchAgentLeads(agentId);

    // 3. Refresh Button
    $('#refreshBtn').on('click', function() {
        fetchAgentLeads(agentId);
    });
});

function fetchAgentLeads(agentId) {
    const $tableBody = $('#leadsTableBody');
    $tableBody.html('<tr><td colspan="5" class="text-center text-muted">Loading leads...</td></tr>');

    $.ajax({
        // Call the specific Admin API endpoint we created
        url: `/api/v1/admin/users/${agentId}/leads`,
        type: 'GET',
        contentType: 'application/json',
        success: function(apiResponse) {
            if (apiResponse.success === "true") {
                populateTable(apiResponse.data);
            } else {
                Swal.fire('Error', apiResponse.message, 'error');
                $tableBody.html('<tr><td colspan="5" class="text-center text-danger">Failed to load data.</td></tr>');
            }
        },
        error: function() {
            Swal.fire('Error', 'A network error occurred.', 'error');
            $tableBody.html('<tr><td colspan="5" class="text-center text-danger">Network error.</td></tr>');
        }
    });
}

function populateTable(leadsData) {
    const $tableBody = $('#leadsTableBody');
    $tableBody.empty();

    if (!leadsData || leadsData.length === 0) {
        $tableBody.html('<tr><td colspan="5" class="text-center text-muted">No leads assigned to this agent yet.</td></tr>');
        return;
    }

    $.each(leadsData, function(index, lead) {
        let badgeClass = 'bg-secondary';
        if (lead.callStatus === 'NEW') badgeClass = 'bg-primary';
        else if (lead.callStatus === 'DIALING') badgeClass = 'bg-warning text-dark';
        else if (lead.callStatus === 'COMPLETED') badgeClass = 'bg-success';

        let sourceBadgeClass = lead.leadSourceName === 'Manual/Direct' ? 'bg-secondary' : 'bg-info text-dark';

        const row = `
        <tr>
            <td>${lead.id}</td>
            <td class="fw-bold">${lead.customerName}</td>
            <td>${lead.phoneNumber}</td>
            <td><span class="badge ${sourceBadgeClass}">${lead.leadSourceName}</span></td>
            <td><span class="badge ${badgeClass}">${lead.callStatus}</span></td>
        </tr>
    `;
        $tableBody.append(row);
    });
}