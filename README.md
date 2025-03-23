# Library Management System

A Spring Boot-based REST API for managing books, patrons, and borrowing records in a library.

## Features
- Book Management: Add, update, delete, and retrieve books.
- Patron Management: Add, update, delete, and retrieve patrons.
- Borrowing Records: Track which books are borrowed by which patrons.
- Input Validation: Validate inputs for books, patrons, and borrowing records.
- Error Handling: Gracefully handle errors and return meaningful messages.

## Technologies Used
- Spring Boot: Backend framework for building the REST API.
- Spring Data JPA: For database interactions.
- MySQL: Relational database for storing data.
- JUnit & Mockito: For unit and integration testing.
- Gradle: Build tool for managing dependencies.

## Setup and Installation

### Prerequisites
Before running the application, make sure you have the following installed:

- **Java 17**: This is the version used for building and running the application. You can download it from [Oracle](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) or use a package manager like Homebrew (for macOS) or SDKMAN!.
- **Gradle**: Gradle is used as the build tool for managing dependencies and building the project. You can install Gradle by following the guide [here](https://gradle.org/install/).
- **MySQL (with phpMyAdmin)**: The application uses MySQL for database storage. I set up MySQL via [phpMyAdmin](https://www.phpmyadmin.net/), a web-based interface for managing MySQL databases.

### Steps to Set Up Locally

1. **Clone the repository**:
   Open a terminal and run the following command to clone the repository:

   ```bash
   git clone https://github.com/MohamadSalimFarhat/library-api.git

