# Library Management System (Spring Boot)

## Project Overview
The Library Management System is a comprehensive solution designed to manage library members and their book borrowings.
It automates processes such as tracking borrowed books, sending overdue email reminders, and managing member activities.

The system includes role-based access control, user activity logging, automated notifications, and integration with external services like RabbitMQ for background tasks and cloud storage for images.

It ensures smooth member management, keeps track of all borrowing activities, and automates communication with members to improve library operations.

---

## Key Features

### User Management
- Manage users with different roles (Admin, Librarian, Staff).  
- Update user profiles and profile images.  
- Role-based access control for secured operations.  

### Member Management
- Create, update, delete, and view library members.  
- Search members by name.
- Track borrowed books for each member.
- Send automated email reminders for overdue books.

### Book & Publisher Management
- Manage books: Create, update, delete, and view books with full validation.
- Manage multiple authors per book: Support for books with more than one author.
- Manage publishers: Create, update, delete, and view publishers.
- Search functionality: Search for books, authors, and publishers by name.
- Category/Genre Classification: Supports hierarchical categories for organizing books (e.g., Fiction → Sci-Fi).
- CRUD Operations with Validation: All operations include proper request validation and error handling.

### Role Management
- Admin can create roles and assign them to users.  
- Role-based access to APIs.  

### User Activity Logging
- Automatic logging of create, update, and delete actions.  
- Admin can view logs of all users or specific users.  

### Email Notifications
- Automatic email reminders for overdue books using scheduled tasks.  
- Send welcome emails to new users.  
- Generate and send codes for "Forgot Password" functionality.  
- Configurable email content and triggers.  

### RabbitMQ Integration
- Handles asynchronous messaging and background tasks.  
- Ensures smooth handling of email notifications and other queued tasks.  

### Image Upload & Cloud Storage
- Upload user profile images or book cover images.  
- Integrates with cloud storage for file management.  

### Validation & Exception Handling
- Global validation for request payloads.  
- Centralized exception handling with meaningful error messages.  

### Swagger Documentation
- Complete API documentation for easy testing and integration.  

### AOP Logging
- Tracks execution time and logs important events in services.  
- Captures exceptions and performance metrics for debugging.  

---

## Technologies Used
- **Backend:** Java, Spring Boot, Spring Security, Spring Data JPA  
- **Database:** MySQL  
- **Messaging Queue:** RabbitMQ  
- **Authentication:** JWT (JSON Web Tokens)  
- **Validation & Exception Handling:** Spring Validation, Controller Advice  
- **Scheduling:** Spring `@Scheduled` for automated tasks (e.g., overdue email notifications)  
- **Email Service:** JavaMailSender for sending emails (welcome email, forgot password, overdue reminders)  
- **File Storage:** MultipartFile + Cloud integration for profile and book cover images  
- **Documentation:** Swagger / OpenAPI for API documentation  
- **Logging:** SLF4J + AOP for execution time and exception logging  

---

## How It Works
1. Users and members are created by Admin. 
2. Members can borrow books.  
3. The system automatically tracks borrowings and overdue books.  
4. Scheduled tasks send email notifications to members with overdue books.  
5. Admins can view activity logs, manage users, roles, books, and publishers.  
6. All operations are secured by role-based access control.  

---

## API Endpoints (Examples)
- `/api/users` – Manage users  
- `/api/members` – Manage library members  
- `/api/books` – Manage books  
- `/api/publishers` – Manage publishers  
- `/api/roles` – Manage roles  
- `/api/logs` – View activity logs
- `/api/transactions` – Manage book borrow/return transactions

---

## Architecture
- Controllers: Handle API requests (UserController, MemberController, PublisherController, RoleController, UserActivityLogController, TransactionController)  
- Services: Business logic for all entities
- Repositories: JPA repositories for database access
- DTOs: Data Transfer Objects for request/response
- Entities: JPA entities representing database tables 
- AOP: LoggingAspect (execution time logging), ActivityLoggingAspect (create/update/delete logs) 
- Scheduler: Sends overdue book reminders automatically
- RabbitMQ Integration: Handles asynchronous messaging and background tasks
- Validation: DTO field validation using annotations
- Global Exception Handling: Handles exceptions and returns structured responses

---

## Setup & Run

1. Clone the repository
- git clone <repo-url>
- cd <repository-folder>

2. Configure application.properties
- `/api/logs` – View activity logs
- Database (MySQL) connection settings
- JWT secret key
- Cloud storage credentials for images
- Email SMTP settings for sending notifications
- RabbitMQ configuration

3. Run the project
mvn spring-boot:run

4. Access Swagger UI
   Open your browser:
   http://localhost:8080/swagger-ui.html


## Postman Collection

A Postman collection is included for easy API testing.

- Path: `Library Management.postman_collection.json`
- To use it:
  1. Open Postman.
  2. Go to File → Import → Choose File.
  3. Select `Library Management.postman_collection.json`.
  4. All API requests will be ready to use.

