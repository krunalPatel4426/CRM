<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<nav class="navbar navbar-expand-lg navbar-light bg-white shadow-sm border-bottom mb-4 py-3">
    <div class="container-fluid px-4">

        <a class="navbar-brand fw-bold text-primary fs-4" href="/admin/users">
            CRM Admin Portal
        </a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#adminNavbarNav" aria-controls="adminNavbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="adminNavbarNav">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0 fw-semibold">

                <li class="nav-item">
                    <a class="nav-link text-dark px-3" href="/admin/users">Manage Agents</a>
                </li>

                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle text-dark px-3" href="#" id="campaignDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Campaign Management
                    </a>
                    <ul class="dropdown-menu shadow-sm border-0 mt-2" aria-labelledby="campaignDropdown">
                        <li><a class="dropdown-item fw-bold" href="/admin/campaign-list">View All Campaigns</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item text-primary" href="/admin/campaign-setup">+ Setup New Campaign</a></li>
                    </ul>
                </li>
            </ul>

            <div class="d-flex align-items-center">
                <button class="btn btn-outline-danger fw-bold px-4" onclick="document.getElementById('navLogoutForm').submit();">
                    Logout
                </button>
            </div>
        </div>
    </div>
</nav>

<form id="navLogoutForm" action="/perform_logout" method="POST" class="d-none">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>