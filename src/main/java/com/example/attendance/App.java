package com.example.attendance;

import com.example.attendance.db.Database;
import com.example.attendance.dao.AttendanceDao;
import com.example.attendance.report.ReportGenerator;
import com.example.attendance.service.AttendanceService;

import java.nio.file.Path;
import java.sql.Connection;
import java.time.LocalDateTime;

public class App {
    public static void main(String[] args) throws Exception {
        Path dbFile = Path.of(System.getProperty("user.home"), "attendance.db");
        String url = "jdbc:sqlite:" + dbFile.toString();
        Database db = new Database(url);
        db.init();
        try (Connection c = db.getConnection()) {
            AttendanceDao dao = new AttendanceDao(c);
            AttendanceService svc = new AttendanceService(dao);
            svc.registerEmployee(1, "Alice");
            svc.registerEmployee(2, "Bob");

            svc.checkIn(1, LocalDateTime.now().minusHours(8));
            svc.checkOut(1, LocalDateTime.now().minusHours(1));

            svc.checkIn(2, LocalDateTime.now().minusHours(7));

            ReportGenerator rg = new ReportGenerator();
            String report = rg.generateSummary(svc.getAttendanceBetween(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1)));
            System.out.println(report);
        }
    }
}
