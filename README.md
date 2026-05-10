# StayEase

A Spring Boot REST API backend for a hotel/accommodation booking platform. StayEase provides secure user authentication, property management, and booking workflows backed by a MySQL database.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17+ |
| Framework | Spring Boot 2.7.18 |
| Security | Spring Security + JWT (JJWT 0.11.5) |
| Persistence | Spring Data JPA + MySQL |
| Validation | Spring Boot Validation |
| Session | Spring Session Core |
| Build Tool | Gradle (Wrapper included) |
| Testing | JUnit 5 + Spring Security Test + Python (assessment suite) |
| Utilities | Lombok, Spring Boot DevTools |

---

## Project Structure

```
StayEase/
├── src/
│   └── main/               # Application source code (Java)
├── assessment/             # Python-based test suite
├── gradle/wrapper/         # Gradle wrapper binaries
├── build.gradle            # Build configuration & dependencies
├── settings.gradle         # Gradle project settings
├── setupDB.sh              # MySQL database initialisation script
├── runAssessment.sh        # Script to start the app and run tests
├── gradlew / gradlew.bat   # Gradle wrapper executables
└── gradle_output.log       # Runtime build/boot log (auto-generated)
```

---

## Prerequisites

- Java 17 or higher
- MySQL 8.x running locally
- Gradle (or use the included `./gradlew` wrapper)
- Python 3 (for running the assessment test suite)

---

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/xtmat/StayEase.git
cd StayEase
```

### 2. Set up the database

Run the provided setup script to create the MySQL user and database:

```bash
chmod +x setupDB.sh
./setupDB.sh
```

This will:
- Create a MySQL user `assessment` with password `redrum`
- Grant all privileges to that user
- Create the database `test_db`

> **Note:** The script requires `sudo` access to run MySQL commands as root.

### 3. Configure the application

Update `src/main/resources/application.properties` (or `application.yml`) with your database credentials if they differ from the defaults:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/test_db
spring.datasource.username=assessment
spring.datasource.password=redrum
```

### 4. Build and run

```bash
./gradlew bootRun
```

The server starts on **port 8081** by default.

---

## Running Tests

The project includes a Python-based assessment suite. Use the provided script to start the application and run all tests automatically:

```bash
chmod +x runAssessment.sh
./runAssessment.sh
```

The script will:
1. Clean up any existing processes on port 8081
2. Start Spring Boot in the background
3. Wait up to 120 seconds for the server to be ready
4. Execute `assessment/tests.py`
5. Exit with the test result code and clean up processes

To run only the Java unit tests:

```bash
./gradlew test
```

---

## Authentication

StayEase uses **JWT-based stateless authentication** via Spring Security. Protected endpoints require a valid Bearer token in the `Authorization` header.

```
Authorization: Bearer <your_jwt_token>
```

---

## API Overview

> Base URL: `http://localhost:8081`

Typical resource endpoints follow REST conventions. Examples:

| Method | Endpoint | Description |
|---|---|---|
| POST | `/auth/register` | Register a new user |
| POST | `/auth/login` | Authenticate and receive a JWT |
| GET | `/hotels` | List available properties |
| POST | `/bookings` | Create a new booking |
| GET | `/bookings/{id}` | Get booking details |

*(Refer to the source code or assessment tests for the full and authoritative API surface.)*

---

## Development Notes

- **Lombok** is used extensively — ensure your IDE has the Lombok plugin installed.
- **DevTools** is included for hot reload during development.
- The `gradle_output.log` file is auto-generated at runtime and is `.gitignore`d.

---
