<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>CRM Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light d-flex align-items-center" style="height: 100vh;">

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-5">
            <div class="card shadow-sm">
                <div class="card-body p-5">
                    <h3 class="text-center mb-4">CRM Login</h3>

                    <% if (request.getParameter("error") != null) { %>
                    <div class="alert alert-danger text-center" role="alert">
                        Invalid Username/Email or Password.
                    </div>
                    <% } %>
                    <% if (request.getParameter("logout") != null) { %>
                    <div class="alert alert-success text-center" role="alert">
                        You have been successfully logged out.
                    </div>
                    <% } %>

                    <form action="/perform_login" method="POST">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                        <div class="mb-3">
                            <label for="username" class="form-label">Username or Email</label>
                            <input type="text" class="form-control" id="username" name="username" required autofocus>
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Password</label>
                            <input type="password" class="form-control" id="password" name="password" required>
                        </div>
                        <button type="submit" class="btn btn-primary w-100 mb-3">Login</button>
                    </form>

                    <div class="text-center">
                        <small>Don't have an account? Contact Administrator.</small>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>