# Library Management System

## Description
A simple Library Management System API built using Spring Boot.

## Requirements
- Java 11 or higher
- Maven

## Setup
1. Clone the repository:
    ```sh
    git clone <repository-url>
    ```
2. Navigate to the project directory:
    ```sh
    cd library-management-system
    ```
3. Build the project:
    ```sh
    mvn clean install
    ```
4. Run the application:
    ```sh
    mvn spring-boot:run
    ```

## API Endpoints

### Book Management
- `GET /api/books` - Retrieve a list of all books.
- `GET /api/books/{id}` - Retrieve details of a specific book by ID.
- `POST /api/books` - Add a new book to the library.
- `PUT /api/books/{id}` - Update an existing book's information.
- `DELETE /api/books/{id}` - Remove a book from the library.

### Patron Management
- `GET /api/patrons` - Retrieve a list of all patrons.
- `GET /api/patrons/{id}` - Retrieve details of a specific patron by ID.
- `POST /api/patrons` - Add a new patron to the system.
- `PUT /api/patrons/{id}` - Update an existing patron's information.
- `DELETE /api/patrons/{id}` - Remove a patron from the system.

### Borrowing Management
- `POST /api/borrow/{bookId}/patron/{patronId}` - Allow a patron to borrow a book.
- `PUT /api/return/{bookId}/patron/{patronId}` - Record the return of a borrowed book by a patron.

## Testing
To run the tests:
```sh
mvn test
