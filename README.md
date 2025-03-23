# Library Management System

This is a Spring Boot-based REST API designed to manage books, patrons, and borrowing records for a library. It’s a simple yet powerful system to help libraries keep track of their resources and users efficiently.

## Key Features
- **Book Management**: Easily add, update, delete, and retrieve book details.
- **Patron Management**: Add, update, delete, and retrieve patron information.
- **Borrowing Records**: Keep track of which books are borrowed by which patrons.
- **Input Validation**: Ensures all inputs for books, patrons, and borrowing records are validated before processing.
- **Error Handling**: Provides clear and meaningful error messages to make troubleshooting easier.

## Technologies Used
- **Spring Boot**: The backbone of the REST API, providing a robust and scalable framework.
- **Spring Data JPA**: Simplifies database interactions and makes it easy to manage data.
- **MySQL**: A reliable relational database used to store all the library data.
- **JUnit & Mockito**: Used for writing unit and integration tests to ensure the system works as expected.
- **Gradle**: Handles dependency management and builds the project seamlessly.

## How to Set Up and Run the Project

### Prerequisites
Before you get started, make sure you have the following installed on your machine:

- **Java 17**: This is the version used to build and run the application. You can download it from [Oracle](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) or use a package manager like Homebrew (for macOS) or SDKMAN!.
- **Gradle**: The build tool used for managing dependencies. Follow the installation guide [here](https://gradle.org/install/).
- **MySQL**: The application uses MySQL for database storage. I recommend setting it up with [phpMyAdmin](https://www.phpmyadmin.net/) for easier database management.

### Step-by-Step Setup

1. **Clone the Repository**:
   Open your terminal and run the following command to clone the project repository:

   ```bash
   git clone https://github.com/MohamadSalimFarhat/library-api.git
   ```

2. **Configure the Database**:
   Update the `application.properties` file with your MySQL database details. Here’s an example configuration:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/library_db
   spring.datasource.username=root
   spring.datasource.password=root
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.jpa.hibernate.ddl-auto=update
   ```

3. **Build the Project**:
   Run the following command to build the project using Gradle:

   ```bash
   ./gradlew build
   ```

4. **Run the Application**:
   Once the build is successful, start the application with:

   ```bash
   ./gradlew bootRun
   ```

5. **Test the Endpoints**:
   You can use a browser or tools like Postman to interact with the API. Here are some example endpoints:

   - **GET /api/books**: Retrieve a list of all books.
   - **POST /api/books**: Add a new book (send data in JSON format).
   - Similar endpoints are available for managing patrons and borrowing records.

## Testing
The project includes unit tests for various components, located in the `src/test` directory. You can run all the tests using the following command:

```bash
./gradlew test
```

---

That’s it! You’re all set to explore and use the Library Management System. If you have any questions or run into issues, feel free to reach out. Happy coding! 🚀
```
