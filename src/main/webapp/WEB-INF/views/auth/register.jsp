<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>CRM Register</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body class="bg-light d-flex align-items-center" style="height: 100vh;">

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-5">
            <div class="card shadow-sm">
                <div class="card-body p-5">
                    <h3 class="text-center mb-4">Create Account</h3>

                    <div id="alertBox" class="alert d-none" role="alert"></div>

                    <form id="registerForm">
                        <div class="mb-3">
                            <label for="username" class="form-label">Username</label>
                            <input type="text" class="form-control" id="username" required>
                        </div>
                        <div class="mb-3">
                            <label for="email" class="form-label">Email Address</label>
                            <input type="email" class="form-control" id="email" required>
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Password</label>
                            <input type="password" class="form-control" id="password" required>
                        </div>
                        <div class="mb-4">
                            <label for="role" class="form-label">Account Type</label>
                            <select class="form-select" id="role" required>
                                <option value="ROLE_SALES">Sales Agent</option>
                                <option value="ROLE_ADMIN">Administrator</option>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-success w-100 mb-3" id="submitBtn">Register</button>
                    </form>

                    <div class="text-center">
                        <small>Already have an account? <a href="/login">Login here</a></small>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    document.getElementById('registerForm').addEventListener('submit', function(e) {
        e.preventDefault(); // Stop standard form submission

        const btn = document.getElementById('submitBtn');
        const alertBox = document.getElementById('alertBox');

        // Grab CSRF tokens from meta tags
        const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
        const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

        // Build our JSON payload
        const payload = {
            username: document.getElementById('username').value,
            email: document.getElementById('email').value,
            password: document.getElementById('password').value,
            role: document.getElementById('role').value
        };

        btn.disabled = true;
        btn.innerText = 'Registering...';

        // AJAX Fetch Call
        fetch('/api/v1/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken // Crucial for Spring Security
            },
            body: JSON.stringify(payload)
        })
            .then(response => response.json())
            .then(data => {
                alertBox.classList.remove('d-none', 'alert-danger', 'alert-success');

                if (data.success === "true") {
                    alertBox.classList.add('alert-success');
                    alertBox.innerText = data.message;
                    document.getElementById('registerForm').reset();
                    // Optional: redirect to login page after 2 seconds
                    setTimeout(() => window.location.href = '/login', 2000);
                } else {
                    alertBox.classList.add('alert-danger');
                    alertBox.innerText = data.message;
                }
            })
            .catch(error => {
                alertBox.classList.remove('d-none');
                alertBox.classList.add('alert-danger');
                alertBox.innerText = "A network error occurred.";
            })
            .finally(() => {
                btn.disabled = false;
                btn.innerText = 'Register';
            });
    });
</script>

</body>
</html>