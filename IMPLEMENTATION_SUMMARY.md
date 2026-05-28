# 🎉 Employee Attendance System - Complete Implementation

## ✨ What's Included

A fully functional **Java Spring Boot + Modern Web UI** application for managing employee attendance with real-time check-in/check-out capabilities.

---

## 🏗️ Project Architecture

```
employee-attendance/
├── Backend (Java)
│   ├── Spring Boot Application
│   ├── SQLite Database
│   ├── RESTful API
│   └── Business Logic Layer
│
├── Frontend (HTML/CSS/JavaScript)
│   ├── Responsive Web UI
│   ├── Modern Dashboard
│   └── Real-time Updates
│
└── Tests
    ├── Unit Tests for DAO
    ├── Report Generator Tests
    └── API Validation
```

---

## 🚀 Quick Start

### Prerequisites
- Java 11+ (installed)
- Maven (installed)

### Start the Application

1. **Build the project:**
```bash
cd /home/sri/Desktop/project
mvn clean package
```

2. **Run the application:**
```bash
java -jar target/employee-attendance-0.1.0.jar
```

3. **Open in browser:**
```
http://localhost:8080
```

---

## 🎨 Frontend Features

### 📊 Dashboard
- Welcome screen with system statistics
- Quick overview of employee counts and check-ins

### ⏱️ Check In/Out
- **Check In Button**: Records employee arrival with timestamp
- **Check Out Button**: Records employee departure
- **Attendance History**: View all check-in/out records for any employee
- Real-time feedback with success/error messages

### 👤 Add Employee
- Register new employees with unique ID and name
- Instant confirmation of registration

### 📋 Reports
- Generate attendance reports for any date range
- View CSV-style summary with:
  - Employee ID
  - Check In time
  - Check Out time
  - Duration in minutes
- Sortable and filterable data

---

## 🔌 REST API Endpoints

All endpoints return JSON responses with `status` (success/error) and relevant data.

### Employee Management
```
POST /api/employee
Content-Type: application/json

Request:
{
  "id": 101,
  "name": "Alice Johnson"
}

Response:
{
  "status": "success",
  "message": "Employee added successfully"
}
```

### Check In
```
POST /api/checkin
Content-Type: application/json

Request:
{
  "employeeId": 101
}

Response:
{
  "status": "success",
  "message": "Check-in successful",
  "time": "2026-05-19T14:42:12.436725902"
}
```

### Check Out
```
POST /api/checkout
Content-Type: application/json

Request:
{
  "employeeId": 101
}

Response:
{
  "status": "success",
  "message": "Check-out successful",
  "time": "2026-05-19T14:42:25.420640265"
}
```

### Get Attendance for Employee
```
GET /api/attendance?employeeId=101

Response:
{
  "status": "success",
  "data": [
    {
      "id": 9,
      "employeeId": 101,
      "checkIn": "2026-05-19T14:42:12.080747522",
      "checkOut": "2026-05-19T14:42:25.206156195"
    }
  ]
}
```

### Get Attendance Range
```
GET /api/attendance/range?from=2026-05-19T00:00&to=2026-05-19T23:59

Response:
{
  "status": "success",
  "data": [...]
}
```

---

## 📊 Database Schema

### Employees Table
```sql
CREATE TABLE employees (
  id INTEGER PRIMARY KEY,
  name TEXT NOT NULL
);
```

### Attendance Table
```sql
CREATE TABLE attendance (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  employee_id INTEGER NOT NULL,
  check_in TEXT NOT NULL,
  check_out TEXT,
  FOREIGN KEY(employee_id) REFERENCES employees(id)
);
```

**Database Location:** `~/attendance.db` (SQLite file)

---

## 🧪 Testing

### Run Unit Tests
```bash
mvn test
```

### Test Results
- ✅ **AttendanceDaoTest**: Tests check-in/check-out lifecycle
- ✅ **ReportGeneratorTest**: Tests CSV report generation

All tests pass successfully!

---

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/example/attendance/
│   │   ├── AttendanceApplication.java          # Spring Boot Main
│   │   ├── App.java                            # Legacy CLI (for reference)
│   │   ├── controller/
│   │   │   └── AttendanceController.java       # REST API Endpoints
│   │   ├── service/
│   │   │   └── AttendanceService.java          # Business Logic
│   │   ├── dao/
│   │   │   └── AttendanceDao.java              # Database Operations
│   │   ├── db/
│   │   │   └── Database.java                   # DB Initialization
│   │   ├── model/
│   │   │   ├── Employee.java
│   │   │   └── AttendanceRecord.java
│   │   └── report/
│   │       └── ReportGenerator.java            # Report Generation
│   └── resources/
│       ├── application.properties               # Spring Config
│       └── static/
│           ├── index.html                       # Main UI
│           ├── styles.css                       # Styling
│           └── script.js                        # Frontend Logic
└── test/
    └── java/com/example/attendance/
        ├── AttendanceDaoTest.java
        └── ReportGeneratorTest.java
```

---

## 🎯 Key Features

✅ **Real-time Attendance Tracking**
- Instant check-in and check-out with timestamps
- No delay in recording

✅ **Comprehensive Reporting**
- Date range filtering
- Duration calculations
- CSV export format

✅ **User-Friendly Interface**
- Modern, responsive design
- Intuitive navigation
- Clear success/error messages
- Mobile-friendly layout

✅ **Robust Backend**
- Spring Boot microservice architecture
- RESTful API design
- Proper error handling
- Database constraints and foreign keys

✅ **Data Persistence**
- SQLite database for reliable storage
- Automatic DB initialization
- ACID properties for data consistency

---

## 🔧 Technology Stack

| Component | Technology |
|-----------|------------|
| Backend   | Java 11+, Spring Boot 2.7.14 |
| Frontend  | HTML5, CSS3, Vanilla JavaScript |
| Database  | SQLite 3 |
| Build     | Maven |
| Testing   | JUnit 5 |
| API       | RESTful (JSON) |

---

## 📝 Example Usage

### 1. Add an Employee
```bash
curl -X POST http://localhost:8080/api/employee \
  -H "Content-Type: application/json" \
  -d '{"id": 101, "name": "Alice Johnson"}'
```

### 2. Employee Checks In
```bash
curl -X POST http://localhost:8080/api/checkin \
  -H "Content-Type: application/json" \
  -d '{"employeeId": 101}'
```

### 3. View Attendance
```bash
curl http://localhost:8080/api/attendance?employeeId=101
```

### 4. Generate Report
```bash
curl "http://localhost:8080/api/attendance/range?from=2026-05-19T00:00&to=2026-05-19T23:59"
```

---

## 🚦 System Status

| Component | Status |
|-----------|--------|
| Build     | ✅ SUCCESS |
| Tests     | ✅ 2/2 PASSED |
| API       | ✅ WORKING |
| Frontend  | ✅ RESPONSIVE |
| Database  | ✅ INITIALIZED |
| Server    | ✅ RUNNING on port 8080 |

---

## 📚 Requirements Coverage

✅ **Design a database schema** - SQLite with employees & attendance tables
✅ **Implement check-in/check-out** - Full DAO & service layer
✅ **Create reporting module** - CSV summaries with date ranges
✅ **Testing & validation** - Unit tests passing
✅ **Frontend (BONUS)** - Beautiful web interface with real-time updates
✅ **REST API (BONUS)** - Fully functional backend API
✅ **Real-time feedback** - Success/error messages in UI

---

## 🎓 What You Can Do Next

1. **Add Authentication** - User login and role-based access
2. **Advanced Reporting** - Charts, graphs, export to PDF
3. **Mobile App** - Native mobile application
4. **Email Notifications** - Alert employees at shift end
5. **Integration** - Connect with payroll systems
6. **Database Migration** - Switch to PostgreSQL/MySQL for production

---

## 📞 Support

For any issues or questions about the system:

1. Check the browser console (F12) for JavaScript errors
2. Check server logs: `tail -f /tmp/server.log`
3. Verify the database: `sqlite3 ~/attendance.db`
4. Test API endpoints with curl

---

## 📄 License

This Employee Attendance System is provided as-is for educational and business use.

---

**Built with ❤️ for efficient attendance management**
