package com.example.attendance.controller;

import com.example.attendance.db.Database;
import com.example.attendance.db.DatabaseConfig;
import com.example.attendance.dao.AttendanceDao;
import com.example.attendance.service.AttendanceService;
import com.example.attendance.model.AttendanceRecord;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AttendanceController {
    
    private AttendanceService getService() throws Exception {
        Connection c = DatabaseConfig.getInstance().getConnection();
        AttendanceDao dao = new AttendanceDao(c);
        return new AttendanceService(dao);
    }

    @PostMapping("/employee")
    public Map<String, Object> addEmployee(@RequestBody Map<String, Object> payload) {
        Map<String, Object> response = new HashMap<>();
        try {
            Object idObj = payload.get("id");
            int id = idObj instanceof Double ? ((Double) idObj).intValue() : (Integer) idObj;
            String name = (String) payload.get("name");
            getService().registerEmployee(id, name);
            response.put("status", "success");
            response.put("message", "Employee added successfully");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }
        return response;
    }

    @PostMapping("/checkin")
    public Map<String, Object> checkIn(@RequestBody Map<String, Object> payload) {
        Map<String, Object> response = new HashMap<>();
        try {
            Object idObj = payload.get("employeeId");
            int employeeId = idObj instanceof Double ? ((Double) idObj).intValue() : (Integer) idObj;
            getService().checkIn(employeeId, LocalDateTime.now());
            response.put("status", "success");
            response.put("message", "Check-in successful");
            response.put("time", LocalDateTime.now().toString());
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }
        return response;
    }

    @PostMapping("/checkout")
    public Map<String, Object> checkOut(@RequestBody Map<String, Object> payload) {
        Map<String, Object> response = new HashMap<>();
        try {
            Object idObj = payload.get("employeeId");
            int employeeId = idObj instanceof Double ? ((Double) idObj).intValue() : (Integer) idObj;
            getService().checkOut(employeeId, LocalDateTime.now());
            response.put("status", "success");
            response.put("message", "Check-out successful");
            response.put("time", LocalDateTime.now().toString());
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }
        return response;
    }

    @GetMapping("/attendance")
    public Map<String, Object> getAttendance(@RequestParam int employeeId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<AttendanceRecord> records = getService().getEmployeeAttendance(employeeId);
            response.put("status", "success");
            response.put("data", records);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }
        return response;
    }

    @GetMapping("/attendance/range")
    public Map<String, Object> getAttendanceRange(@RequestParam String from, @RequestParam String to) {
        Map<String, Object> response = new HashMap<>();
        try {
            LocalDateTime fromDt = LocalDateTime.parse(from);
            LocalDateTime toDt = LocalDateTime.parse(to);
            List<AttendanceRecord> records = getService().getAttendanceBetween(fromDt, toDt);
            response.put("status", "success");
            response.put("data", records);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }
        return response;
    }
}
