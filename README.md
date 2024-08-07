# Library Management System

## Overview

The Library Management System is a Java Spring Boot application designed to manage books, patrons, and borrowing records. It provides API endpoints for CRUD operations on these entities.

## Requirements
- Java 17 or higher
- Maven
- postgres

## Setting Up the Database

1. **Install postgres:** Download and install postgres on your Windows machine.
2. **Create Database:** Create a new database for your application.

    ```sql
    CREATE DATABASE librarydb;
    ```

3. **Clone the repository:**

   ```sh
   git clone https://github.com/aliayman-225/Library-Management-System
   ```
4. **Configure `application.properties`:** Update your `src/main/resources/application.properties` file with your database configuration.

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/library_management_system
    spring.datasource.username=root
    spring.datasource.password=yourpassword
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
    ```

## Running the Application

1. **Open Command Prompt:** Navigate to your project directory.

2. **Build the Project:**

    ```bash
    mvn clean install
    ```

3. **Run the Application:**

    ```bash
    mvn spring-boot:run
    ```

## Interacting with the API Endpoints

**Base URL:** `http://localhost:8090`

### Authentication

Use Basic Authentication with the following credentials:

- **Admin:**
   - Username: `admin`
   - Password: `adminpassword`
- **User:**
   - Username: `user`
   - Password: `userpassword`

### API Endpoints

#### Books

- **Get All Books (Admin only)**
   - `GET /api/books`

- **Get Book by ID (Admin only)**
   - `GET /api/books/{id}`

- **Add a New Book (Admin only)**
   - `POST /api/books`
   - **Content-Type:** `application/json`
   - **Body:**
     ```json
     {
       "title": "Book Title",
       "author": "Book Author",
       "publicationYear":1992,
       "isbn": "123-4567890123"
     }
     ```

- **Update a Book (Admin only)**
   - `PUT /api/books/{id}`
   - **Content-Type:** `application/json`
   - **Body:**
     ```json
     {
       "title": "Updated Title",
       "author": "Updated Author",
       "publicationYear":1992,
       "isbn": "123-4567890123"
     }
     ```

- **Delete a Book (Admin only)**
   - `DELETE /api/books/{id}`

#### Patrons

- **Get All Patrons (Admin only)**
   - `GET /api/patrons`

- **Get Patron by ID (Admin only)**
   - `GET /api/patrons/{id}`

- **Add a New Patron (Admin only)**
   - `POST /api/patrons`
   - **Content-Type:** `application/json`
   - **Body:**
     ```json
     {
       "name": "Patron Name",
       "contactInfo": "0123456789"
     }
     ```

- **Update a Patron (Admin only)**
   - `PUT /api/patrons/{id}`
   - **Content-Type:** `application/json`
   - **Body:**
     ```json
     {
       "name": "Updated Name",
       "contactInfo": "0987654321"
     }
     ```

- **Delete a Patron (Admin only)**
   - `DELETE /api/patrons/{id}`

#### Borrowing Records

- **Borrow a Book (Admin and User)**
   - `POST /api/borrow/{bookId}/patron/{patronId}`

- **Return a Book (Admin and User)**
   - `PUT /api/return/{bookId}/patron/{patronId}`

## Additional Notes

- **Logging:** logging was set up in file app-test.log to help with troubleshooting.
- **Security:** Basic Authentication is used for simplicity. Consider using more secure authentication mechanisms like OAuth2 or jwt for production environments.

## Postman Collection

To help you get started with testing the API, an exported Postman collection is provided. You can import this collection into Postman to easily test all the API endpoints.

- **Library_Management_SystemApis.postman_collection.json**

To import the collection into Postman:
1. Open Postman.
2. Click on the "Import" button in the top left corner.
3. Select the downloaded `.json` file.
4. Click "Import."

