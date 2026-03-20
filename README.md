# 🚀 Enterprise CRM System

## 📌 Overview
This repository contains a full-stack Customer Relationship Management (CRM) application designed to streamline lead management, inbound marketing attribution, and outbound sales operations. Built with a hybrid architecture, it uses Spring Boot MVC with JSP for server-side rendering, combined with RESTful APIs and asynchronous AJAX calls for a dynamic, single-page-like user experience.

The system is highly optimized for performance and security, featuring a custom Redis caching layer, Database-level Pessimistic Locking for race-condition prevention, Role-Based Access Control (RBAC), and real-time bidirectional communication via WebSockets.

## ✨ Key Features

### 🎯 Marketing & Lead Routing (New)
* **Intelligent Lead Distribution (Round Robin):** Automated, balanced lead assignment among sales agents. Utilizes **PostgreSQL Pessimistic Locking (`@Lock`)** to guarantee zero race conditions or duplicate assignments during high-traffic ad campaigns.
* **Dynamic Campaign Management:** Dedicated Admin portal to construct marketing campaigns with granular UTM tracking (Source, Medium, Campaign). Features dynamic UI rows to map multiple agents to a single campaign on the fly.
* **Granular Source Tracking:** Generates and tracks completely unique `lead_source_name` identifiers for individual agents within a shared campaign, allowing for precise performance analytics.
* **Public Lead Capture Engine:** Built-in public landing pages that automatically harvest URL UTM parameters and seamlessly inject incoming prospects directly into the Round Robin distribution queue.
* **High-Performance Data Grids:** Server-side paginated campaign dashboards powered by **DataTables**, utilizing Spring Data JPA Projections and Native SQL for memory-efficient rendering of massive datasets.

### 💼 Sales & Operations
* **Lead Management System:** Complete CRUD operations for sales agents to manage assigned leads, track phone numbers, view specific lead sources, and update call statuses (`NEW`, `DIALING`, `COMPLETED`).
* **Advanced Caching Layer:** Implements a custom Cache-Aside pattern using **Redis** to minimize PostgreSQL database hits, resulting in ultra-fast data retrieval for sales dashboards.
* **Real-Time Security & Session Management:** Utilizes **WebSockets (STOMP/SockJS)** to push real-time events from the server to the client. Includes an instant "Force Logout" mechanism that immediately locks the UI for deactivated users without requiring a page refresh.
* **Role-Based Access Control (RBAC):** Secure authentication and authorization powered by **Spring Security**, distinctly separating Admin portal privileges from Sales Agent dashboards.
* **AI Telephony Integration:** Prepared for integration with external telephony providers to initiate AI-driven outbound sales calls directly from the lead dashboard.
* **Interactive UI/UX:** Responsive frontend built with **Bootstrap 5**, leveraging **jQuery**, **Select2**, and **SweetAlert2** for seamless, non-blocking asynchronous data updates and dynamic form generation.

## 🛠️ Tech Stack

### Backend
* **Java 17+**
* **Spring Boot 3.x** (Web, Security, Data JPA, WebSocket)
* **PostgreSQL** (Relational Database)
* **Redis** (In-Memory Data Store & Caching)
* **Hibernate** (ORM)

### Frontend
* **JSP** (JavaServer Pages)
* **JavaScript** (jQuery, AJAX)
* **HTML5 / CSS3 / Bootstrap 5**
* **DataTables** (Server-Side Pagination & Data Grids)
* **Select2** (Searchable Dropdowns)
* **SweetAlert2** (Popups & Alerts)