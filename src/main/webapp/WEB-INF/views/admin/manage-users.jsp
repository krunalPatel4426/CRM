<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin - Manage Sales Agents</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Manage Sales Agents</h2>
        <div>
            <button class="btn btn-success me-2" onclick="openAddAgentModal()">+ Add Sales Agent</button>
            <button class="btn btn-primary me-2" id="refreshAgentsBtn">Refresh Data</button>
            <button class="btn btn-danger" onclick="document.getElementById('logoutForm').submit();">Logout</button>
        </div>
    </div>

    <div class="card shadow-sm">
        <div class="card-body">
            <table class="table table-hover">
                <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Email Address</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody id="agentsTableBody">
                <tr>
                    <td colspan="4" class="text-center text-muted">Loading agents...</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="modal fade" id="agentModal" tabindex="-1" aria-labelledby="agentModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="agentModalLabel">Register New Sales Agent</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="agentForm">
                    <div class="mb-3">
                        <label for="agentUsername" class="form-label">Username</label>
                        <input type="text" class="form-control" id="agentUsername" required>
                    </div>
                    <div class="mb-3">
                        <label for="agentEmail" class="form-label">Email</label>
                        <input type="email" class="form-control" id="agentEmail" required>
                    </div>
                    <div class="mb-3">
                        <label for="agentPassword" class="form-label">Temporary Password</label>
                        <input type="password" class="form-control" id="agentPassword" required>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary" id="saveAgentBtn">Create Agent</button>
            </div>
        </div>
    </div>
</div>

<form id="logoutForm" action="/perform_logout" method="POST" class="d-none">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="/js/admin-users.js"></script>

</body>
</html>