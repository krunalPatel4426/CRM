<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Setup Campaign</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/select2-bootstrap-5-theme@1.3.0/dist/select2-bootstrap-5-theme.min.css" rel="stylesheet" />

    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body class="bg-light">
<jsp:include page="../components/admin-navbar.jsp" />
<div class="container mt-5">
    <div class="card shadow">
        <div class="card-header bg-dark text-white d-flex justify-content-between align-items-center">
            <h5 class="mb-0">Map Campaign to Agents</h5>
        </div>
        <div class="card-body">
            <form id="campaignForm">
                <div class="row mb-4">
                    <div class="col-md-4">
                        <label class="form-label fw-bold">*UTM Source</label>
                        <input type="text" id="utmSource" class="form-control" placeholder="e.g. facebook" required>
                    </div>
                    <div class="col-md-4">
                        <label class="form-label fw-bold">*UTM Medium</label>
                        <input type="text" id="utmMedium" class="form-control" placeholder="e.g. cpc" required>
                    </div>
                    <div class="col-md-4">
                        <label class="form-label fw-bold">*UTM Campaign</label>
                        <input type="text" id="utmCampaign" class="form-control" placeholder="e.g. summer_sale" required>
                    </div>
                </div>

                <hr>

                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h5 class="fw-bold text-secondary">Sales Agent Mappings</h5>
                    <button type="button" class="btn btn-outline-primary" id="addAgentBtn">
                        + Add Sales Agent
                    </button>
                </div>

                <div id="agentRowsContainer">
                </div>

                <hr class="mt-4">
                <button type="submit" class="btn btn-success w-100 fw-bold py-2" id="submitBtn">Save Campaign Routing</button>
            </form>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="/js/campaign-setup.js"></script>
</body>
</html>