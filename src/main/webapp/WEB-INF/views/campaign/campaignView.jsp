<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Campaign Details</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body class="bg-light">
<jsp:include page="../components/admin-navbar.jsp" />
<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Campaign Overview</h2>
        <button class="btn btn-secondary" onclick="window.location.href='/admin/campaign-list'">← Back to List</button>
    </div>

    <div class="card shadow-sm mb-4">
        <div class="card-header bg-dark text-white">
            <h5 class="mb-0">UTM Parameters</h5>
        </div>
        <div class="card-body">
            <div class="row fs-5">
                <div class="col-md-4">
                    <span class="text-muted fw-bold">Source:</span> <span id="viewUtmSource" class="text-primary fw-bold">Loading...</span>
                </div>
                <div class="col-md-4">
                    <span class="text-muted fw-bold">Medium:</span> <span id="viewUtmMedium" class="text-primary fw-bold">Loading...</span>
                </div>
                <div class="col-md-4">
                    <span class="text-muted fw-bold">Campaign:</span> <span id="viewUtmCampaign" class="text-primary fw-bold">Loading...</span>
                </div>
            </div>
            <hr>
            <div class="mt-2">
                <span class="text-muted fw-bold">Generated Link:</span>
                <a href="#" id="viewReferUrl" target="_blank" class="text-break">Loading...</a>
            </div>
        </div>
    </div>

    <div class="card shadow-sm">
        <div class="card-header bg-primary text-white">
            <h5 class="mb-0">Assigned Sales Agents</h5>
        </div>
        <div class="card-body p-0">
            <table class="table table-hover mb-0">
                <thead class="table-light">
                <tr>
                    <th>Agent ID</th>
                    <th>Username</th>
                    <th>Email</th>
                    <th>Assigned Lead Source Name</th>
                </tr>
                </thead>
                <tbody id="agentMappingTableBody">
                <tr>
                    <td colspan="4" class="text-center text-muted py-3">Loading agents...</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="/js/campaign-view.js"></script>
</body>
</html>