Employee Attendance System

A comprehensive Java (Maven) + Spring Boot project with a modern web frontend for managing employee attendance.

## Features

- ✅ SQLite database for persistent storage
- ✅ Employee registration and management
- ✅ Real-time check-in and check-out functionality
- ✅ Attendance history and reporting
- ✅ Beautiful, responsive web UI
- ✅ RESTful API backend
- ✅ Unit tests for core functionality

## Build & Run

### Build the project:

```bash
mvn clean package
```

### Run the Spring Boot application:

```bash
mvn spring-boot:run
```

Or after building:

```bash
java -jar target/employee-attendance-0.1.0.jar
```

The application will start on `http://localhost:8080`

### Open in browser:

Navigate to `http://localhost:8080` in your web browser to access the web interface.

## Features Overview

### 🏢 Dashboard
- View system statistics and summary

### ⏱️ Check In/Out
- Employees can check in and check out with a single click
- View attendance history for any employee

### 👤 Add Employee
- Register new employees in the system
- Assign unique employee IDs

### 📋 Reports
- Generate attendance reports for a specific date range
- View detailed check-in/check-out times and durations

## API Endpoints

- `POST /api/employee` - Add a new employee
- `POST /api/checkin` - Check in an employee
- `POST /api/checkout` - Check out an employee
- `GET /api/attendance?employeeId=X` - Get attendance for an employee
- `GET /api/attendance/range?from=YYYY-MM-DDTHH:MM&to=YYYY-MM-DDTHH:MM` - Get attendance for a date range

## Running Tests

```bash
mvn test
```

## Database

The system uses SQLite with a database file created at `~/attendance.db`

### Schema

- **employees**: id (PK), name
- **attendance**: id (PK, auto-increment), employee_id (FK), check_in, check_out

## Notes

- The frontend uses vanilla JavaScript (no external dependencies required)
- Database connection is initialized on first request
- All timestamps are in ISO format (YYYY-MM-DDTHH:MM:SS)
- The system supports multiple employees with independent attendance tracking
