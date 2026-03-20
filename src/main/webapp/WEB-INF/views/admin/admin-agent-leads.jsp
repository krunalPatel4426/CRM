<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin - View Agent Leads</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body class="bg-light">
<jsp:include page="../components/admin-navbar.jsp" />
<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Leads for Agent: <span id="agentNameDisplay" class="text-primary">Loading...</span></h2>
        <div>
            <button class="btn btn-secondary me-2" onclick="window.location.href='/admin/users'">← Back to Agents</button>
            <button class="btn btn-primary" id="refreshBtn">Refresh Data</button>
        </div>
    </div>

    <div class="card shadow-sm">
        <div class="card-body">
            <table class="table table-hover">
                <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Customer Name</th>
                    <th>Phone Number</th>
                    <th>Lead Source</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody id="leadsTableBody">
                <tr>
                    <td colspan="5" class="text-center text-muted" id="loadingRow">Loading leads...</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="/js/admin-agent-leads.js?v=1.0"></script>

</body>
</html>