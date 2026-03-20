# 🚀 Enterprise CRM System 

## 📌 Overview
This repository contains a full-stack Customer Relationship Management (CRM) application designed to streamline lead management and outbound sales operations. Built with a hybrid architecture, it uses Spring Boot MVC with JSP for server-side rendering, combined with RESTful APIs and asynchronous AJAX calls for a dynamic, single-page-like user experience.

The system is highly optimized for performance and security, featuring a custom Redis caching layer, Role-Based Access Control (RBAC), and real-time bidirectional communication via WebSockets for instant session management.

## ✨ Key Features
* **Lead Management System:** Complete CRUD operations for sales agents to manage assigned leads, track phone numbers, and update call statuses (`NEW`, `DIALING`, `COMPLETED`).
* **Advanced Caching Layer:** Implements a custom Cache-Aside pattern using **Redis** to minimize PostgreSQL database hits, resulting in ultra-fast data retrieval for sales dashboards.
* **Real-Time Security & Session Management:** Utilizes **WebSockets (STOMP/SockJS)** to push real-time events from the server to the client. Includes an instant "Force Logout" mechanism that immediately locks the UI for deactivated users without requiring a page refresh.
* **Role-Based Access Control (RBAC):** Secure authentication and authorization powered by **Spring Security**, distinctly separating Admin privileges from Sales Agent capabilities.
* **AI Telephony Integration:** Integrated with **ElevenLabs** and external telephony providers to initiate AI-driven outbound sales calls directly from the lead dashboard.
* **Interactive UI/UX:** Responsive frontend built with **Bootstrap 5**, leveraging **jQuery** and **SweetAlert2** for seamless, non-blocking asynchronous data updates.

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
* **SweetAlert2** (Popups & Alerts)
ick `docker-compose.yml` file as well so you can include it in the root of your project? It makes starting the database and Redis even easier for anyone cloning your repo!

```
