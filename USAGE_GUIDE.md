==============================================================================
  EMPLOYEE ATTENDANCE SYSTEM - USAGE GUIDE
==============================================================================

QUICK START:
-----------

1. Build the project:
   $ mvn clean package

2. Start the application:
   $ java -jar target/employee-attendance-0.1.0.jar

3. Open in browser:
   http://localhost:8080

FEATURES WALKTHROUGH:
--------------------

DASHBOARD TAB:
- Shows real-time statistics
- Total Employees: Unique employee count with records today
- Today's Check-ins: Total check-in records for today
- Currently Checked In: Employees still working (no checkout yet)
- Stats auto-update when viewing the tab

ADD EMPLOYEE TAB:
- Register new employees in the system
- Enter Employee ID (numeric)
- Enter Employee Name (text)
- Click "Add Employee" button
- Success/error message displays

CHECK IN/OUT TAB:
- Employee Check-in/Check-out:
  * Enter Employee ID
  * Click "✓ Check In" to record arrival
  * Click "✗ Check Out" to record departure
  * Timestamp automatically recorded

- View Attendance History:
  * Enter Employee ID
  * Click "View History"
  * Table shows all check-in/check-out records for that employee
  * Displays check-in time, check-out time, and duration in minutes

REPORTS TAB:
- Generate attendance reports for date ranges
- Select "From" date and time
- Select "To" date and time
- Click "Generate Report"
- View table with all attendance records in that period
- Shows Employee ID, check-in, check-out, and duration

API ENDPOINTS:
--------------

Add Employee:
  POST /api/employee
  Body: {"id": 101, "name": "John Doe"}
  Response: {"status": "success", "message": "Employee added successfully"}

Check In:
  POST /api/checkin
  Body: {"employeeId": 101}
  Response: {"status": "success", "message": "Check-in successful", "time": "2026-05-19T14:55:27.410844286"}

Check Out:
  POST /api/checkout
  Body: {"employeeId": 101}
  Response: {"status": "success", "message": "Check-out successful", "time": "2026-05-19T14:56:27.104861348"}

Get Employee Attendance:
  GET /api/attendance?employeeId=101
  Response: {"status": "success", "data": [...attendance records...]}

Get Attendance Range:
  GET /api/attendance/range?from=2026-05-19T00:00&to=2026-05-20T00:00
  Response: {"status": "success", "data": [...attendance records...]}

EXAMPLE CURL COMMANDS:
---------------------

1. Add an employee:
   curl -X POST http://localhost:8080/api/employee \
     -H "Content-Type: application/json" \
     -d '{"id": 101, "name": "John Doe"}'

2. Check in an employee:
   curl -X POST http://localhost:8080/api/checkin \
     -H "Content-Type: application/json" \
     -d '{"employeeId": 101}'

3. Check out an employee:
   curl -X POST http://localhost:8080/api/checkout \
     -H "Content-Type: application/json" \
     -d '{"employeeId": 101}'

4. Get employee's attendance history:
   curl "http://localhost:8080/api/attendance?employeeId=101"

5. Get attendance for a date range:
   curl "http://localhost:8080/api/attendance/range?from=2026-05-19T00:00&to=2026-05-20T00:00"

DATA PERSISTENCE:
-----------------
- All data is stored in SQLite database
- Database file location: ~/.attendance.db
- Tables:
  * employees (id, name)
  * attendance (id, employee_id, check_in, check_out)
- Data persists between application restarts

FEATURES:
---------
✓ Real-time check-in/check-out tracking
✓ Employee management
✓ Attendance reports with duration calculation
✓ Dashboard with live statistics
✓ REST API for integration
✓ Responsive web interface
✓ Date range reporting
✓ Individual employee history

NOTES:
------
- Employee IDs must be unique
- Timestamps are stored in ISO format (YYYY-MM-DDTHH:MM:SS)
- Check-out automatically updates the latest check-in without checkout
- Dashboard statistics update in real-time
- Multiple concurrent check-ins allowed for same employee

ERROR HANDLING:
--------------
- Missing employee ID: Shows error message
- Check-out without check-in: Shows error message
- Invalid date ranges: Shows empty results
- Server errors: Displays error details

==============================================================================
