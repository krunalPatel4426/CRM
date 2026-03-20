$(document).ready(function() {
    $('#campaignTable').DataTable({
        "processing": true,
        "serverSide": true,      // Tells DataTables to use server-side pagination
        "ordering": false,       // Disabled sorting for now to keep the native query simple
        "searching": false,      // Disabled search for now (requires updating the native query with LIKE clauses)
        "ajax": {
            "url": "/api/campaign",
            "type": "GET",
            "data": function(d) {
                // DataTables sends 'start' (index) and 'length' (page size).
                // We convert these into standard Spring 'page' and 'size' parameters.
                return {
                    page: Math.floor(d.start / d.length) + 1,
                    size: d.length
                };
            },
            "dataSrc": function(json) {
                if (json.success === "true") {
                    // Tell DataTables how many total records exist so it can draw the pagination buttons
                    json.recordsTotal = json.data.totalElements;
                    json.recordsFiltered = json.data.totalElements;
                    return json.data.referList; // Return the actual array of campaigns
                } else {
                    return [];
                }
            }
        },
        "columns": [
            {
                "data": null,
                "render": function (data, type, row, meta) {
                    // Calculate sequential row number across pages
                    return meta.row + meta.settings._iDisplayStart + 1;
                }
            },
            { "data": "utmSource" },
            { "data": "utmMedium" },
            { "data": "utmCampaign" },
            {
                "data": null,
                "render": function(data, type, row) {
                    // The button that redirects the admin to the detailed view page
                    return `<button class="btn btn-sm btn-info text-white fw-bold" 
                            onclick="window.location.href='/admin/campaign-view?id=${row.referId}'">
                            View Details
                            </button>`;
                }
            }
        ]
    });
});