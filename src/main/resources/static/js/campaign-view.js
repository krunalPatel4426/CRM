$(document).ready(function() {
    // Extract the 'id' parameter from the URL (e.g., /admin/campaign/view?id=5)
    const urlParams = new URLSearchParams(window.location.search);
    const campaignId = urlParams.get('id');

    if (!campaignId) {
        Swal.fire('Error', 'No campaign ID provided in URL.', 'error').then(() => {
            window.location.href = '/admin/campaigns';
        });
        return;
    }

    // Fetch the data from REST API
    fetchCampaignDetails(campaignId);
});

function fetchCampaignDetails(id) {
    $.ajax({
        url: `/api/campaign/${id}`,
        type: 'GET',
        success: function(response) {
            if (response.success === "true") {
                populateCampaignDetails(response.data);
            } else {
                Swal.fire('Error', response.message, 'error');
            }
        },
        error: function(xhr) {
            Swal.fire('Failed', 'Could not load campaign details. The campaign might not exist.', 'error').then(() => {
                window.location.href = '/admin/campaigns';
            });
        }
    });
}

function populateCampaignDetails(data) {
    // 1. Populate the top card with UTM parameters
    $('#viewUtmSource').text(data.utmSource);
    $('#viewUtmMedium').text(data.utmMedium);
    $('#viewUtmCampaign').text(data.utmCampaign);

    // Set the hyperlink URL and Text
    $('#viewReferUrl')
        .attr('href', data.referUrl)
        .text(data.referUrl);

    // 2. Populate the table with sales agents
    const $tableBody = $('#agentMappingTableBody');
    $tableBody.empty(); // Clear the "Loading..." text

    if (!data.agentMappings || data.agentMappings.length === 0) {
        $tableBody.html('<tr><td colspan="4" class="text-center text-muted">No agents mapped to this campaign.</td></tr>');
        return;
    }

    // Loop through the agents and build table rows
    $.each(data.agentMappings, function(index, agent) {
        const rowHtml = `
            <tr>
                <td class="fw-bold">${agent.salesPersonId}</td>
                <td>${agent.username}</td>
                <td>${agent.email}</td>
                <td><span class="badge bg-success fs-6">${agent.leadSourceName}</span></td>
            </tr>
        `;
        $tableBody.append(rowHtml);
    });
}