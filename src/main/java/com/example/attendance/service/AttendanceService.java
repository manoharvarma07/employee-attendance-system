package com.example.attendance.service;

import com.example.attendance.dao.AttendanceDao;
import com.example.attendance.model.AttendanceRecord;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class AttendanceService {
    private final AttendanceDao dao;

    public AttendanceService(AttendanceDao dao) {
        this.dao = dao;
    }

    public void registerEmployee(int id, String name) throws SQLException {
        dao.addEmployee(id, name);
    }

    public void checkIn(int employeeId, LocalDateTime when) throws SQLException {
        dao.checkIn(employeeId, when);
    }

    public void checkOut(int employeeId, LocalDateTime when) throws SQLException {
        dao.checkOut(employeeId, when);
    }

    public List<AttendanceRecord> getEmployeeAttendance(int employeeId) throws SQLException {
        return dao.getAttendanceForEmployee(employeeId);
    }

    public List<AttendanceRecord> getAttendanceBetween(LocalDateTime from, LocalDateTime to) throws SQLException {
        return dao.getAttendanceBetween(from, to);
    }
}
