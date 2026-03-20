<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Book Your Free Demo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body class="bg-light d-flex align-items-center justify-content-center" style="min-height: 100vh;">

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-6 col-lg-5">
            <div class="card shadow-lg border-0 rounded-3">
                <div class="card-header bg-primary text-white text-center py-4 rounded-top-3">
                    <h3 class="mb-0">Request a Free Demo</h3>
                    <p class="mb-0 text-white-50">Fill out the form below and our team will contact you shortly.</p>
                </div>
                <div class="card-body p-4 p-md-5">

                    <form id="enquiryForm">
                        <div class="mb-4">
                            <label for="customerName" class="form-label fw-bold text-secondary">*Full Name</label>
                            <input type="text" class="form-control form-control-lg" id="customerName" placeholder="e.g. John Doe" required>
                        </div>

                        <div class="mb-4">
                            <label for="phoneNumber" class="form-label fw-bold text-secondary">*Phone Number</label>
                            <input type="tel" class="form-control form-control-lg" id="phoneNumber" placeholder="e.g. 9845782569" required>
                        </div>

                        <button type="submit" id="submitBtn" class="btn btn-primary btn-lg w-100 fw-bold">Submit Enquiry</button>
                    </form>

                </div>
            </div>
            <div class="text-center mt-3 text-muted small">
                &copy; 2026 Your CRM Company. All rights reserved.
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="/js/enquiry-form.js"></script>

</body>
</html>