$(document).ready(function() {

    const csrfToken = $("meta[name='_csrf']").attr("content");
    const csrfHeader = $("meta[name='_csrf_header']").attr("content");

    // Global variable to hold our agents so we don't spam the API
    let salesAgentsList = [];

    // 1. Fetch Agents on Page Load
    $.get("/api/v1/admin/users/sales", function(res) {
        if(res.success === "true") {
            salesAgentsList = res.data;
            // Add the first empty row automatically
            addAgentRow();
        } else {
            Swal.fire('Error', 'Could not load sales agents.', 'error');
        }
    });

    // 2. Button Click: Add a new row
    $('#addAgentBtn').on('click', function() {
        addAgentRow();
    });

    // 3. Button Click: Remove a row
    $(document).on('click', '.remove-agent-btn', function() {
        $(this).closest('.agent-mapping-row').remove();
    });

    // --- FUNCTION TO GENERATE A ROW ---
    function addAgentRow() {
        // Build the <option> list from our global array
        let optionsHtml = '<option value="">Search & Select Agent...</option>';
        salesAgentsList.forEach(agent => {
            optionsHtml += `<option value="${agent.id}">${agent.username} (${agent.email})</option>`;
        });

        // The HTML for one row
        const rowHtml = `
            <div class="row align-items-center mb-3 agent-mapping-row p-3 border rounded bg-white shadow-sm">
                <div class="col-md-5">
                    <label class="form-label text-muted small">*Sales Agent</label>
                    <select class="form-select agent-select" required>
                        ${optionsHtml}
                    </select>
                </div>
                <div class="col-md-5">
                    <label class="form-label text-muted small">*Unique Lead Source Name</label>
                    <input type="text" class="form-control lead-source-input" placeholder="e.g. Facebook_Krunal" required>
                </div>
                <div class="col-md-2 text-end mt-4">
                    <button type="button" class="btn btn-danger remove-agent-btn w-100">Remove</button>
                </div>
            </div>
        `;

        // Append it to the container
        const $newRow = $(rowHtml).appendTo('#agentRowsContainer');

        // Initialize Select2 on the newly created dropdown for a beautiful search bar
        $newRow.find('.agent-select').select2({
            theme: 'bootstrap-5',
            width: '100%'
        });
    }

    // 4. Submit Data via AJAX
    $('#campaignForm').on('submit', function(e) {
        e.preventDefault();

        let isValid = true;
        let agentMappings = [];
        let selectedAgentIds = new Set(); // To check for duplicates

        const $rows = $('.agent-mapping-row');

        if($rows.length === 0) {
            Swal.fire('Warning', 'You must add at least one sales agent to this campaign.', 'warning');
            return;
        }

        // Loop through each row and extract data
        $rows.each(function() {
            const agentId = $(this).find('.agent-select').val();
            const leadSourceName = $(this).find('.lead-source-input').val().trim();

            // Validate Empty Dropdown
            if (!agentId) {
                isValid = false;
                Swal.fire('Error', 'Please select an agent for all rows.', 'error');
                return false; // breaks the loop
            }

            // Validate Duplicate Agents
            if (selectedAgentIds.has(agentId)) {
                isValid = false;
                Swal.fire('Error', 'You have selected the same Sales Agent more than once. Please remove duplicates.', 'error');
                return false;
            }
            selectedAgentIds.add(agentId);

            // Validate Empty Text Input
            if (!leadSourceName) {
                isValid = false;
                $(this).find('.lead-source-input').addClass('is-invalid');
            } else {
                $(this).find('.lead-source-input').removeClass('is-invalid');
            }

            agentMappings.push({
                salesPersonId: parseInt(agentId),
                leadSourceName: leadSourceName
            });
        });

        if (!isValid) {
            if(agentMappings.length === $rows.length) { // Only show this if the error wasn't already caught by the duplicate/empty agent check
                Swal.fire('Error', 'Please fill out all Lead Source names.', 'error');
            }
            return;
        }

        const payload = {
            utmSource: $('#utmSource').val().trim(),
            utmMedium: $('#utmMedium').val().trim(),
            utmCampaign: $('#utmCampaign').val().trim(),
            agentMappings: agentMappings
        };
        if (payload.utmSource.length == 0 || payload.utmMedium.length == 0 || payload.utmCampaign.length == 0) {
            Swal.fire('Error', 'Please fill out all data properly.', 'error');
            return;
        }

        $('#submitBtn').prop('disabled', true).text('Saving...');

        $.ajax({
            url: '/api/campaign', // Ensure this matches your Controller endpoint
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(payload),
            beforeSend: function(xhr) { xhr.setRequestHeader(csrfHeader, csrfToken); },
            success: function(res) {
                if(res.success === "true") {
                    Swal.fire('Success!', res.message, 'success').then(() => {
                        window.location.href = '/admin/campaign-list';
                    });
                } else {
                    Swal.fire('Failed', res.message, 'error');
                    $('#submitBtn').prop('disabled', false).text('Save Campaign Routing');
                }
            },
            error: function(xhr) {
                let errorMsg = "An unexpected error occurred.";
                if(xhr.responseJSON && xhr.responseJSON.message) {
                    errorMsg = xhr.responseJSON.message;
                }
                Swal.fire('Failed', errorMsg, 'error');
                $('#submitBtn').prop('disabled', false).text('Save Campaign Routing');
            }
        });
    });
});