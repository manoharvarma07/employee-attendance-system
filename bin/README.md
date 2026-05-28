Employee Attendance System

This is a small Java (Maven) project demonstrating an Employee Attendance System with:

- SQLite-based storage
- Check-in and check-out functionality
- A report generator that outputs CSV summaries

Build & run

To build:

```bash
mvn -q -DskipTests package
```

To run the demo Main which writes a SQLite DB to your home folder:

```bash
mvn -q exec:java -Dexec.mainClass="com.example.attendance.App"
```

Tests

```bash
mvn -q test
```

Notes

This is a minimal prototype. Next steps: add authentication, web UI, schedule jobs for daily reports, and better error handling.
