package com.example.attendance.dao;

import com.example.attendance.model.AttendanceRecord;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDao {
    private final Connection conn;
    private final DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public AttendanceDao(Connection conn) {
        this.conn = conn;
    }

    public void addEmployee(int id, String name) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT OR IGNORE INTO employees(id,name) VALUES(?,?)")) {
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.executeUpdate();
        }
    }

    public void checkIn(int employeeId, LocalDateTime time) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO attendance(employee_id,check_in) VALUES(?,?)")) {
            ps.setInt(1, employeeId);
            ps.setString(2, time.format(fmt));
            ps.executeUpdate();
        }
    }

    public void checkOut(int employeeId, LocalDateTime time) throws SQLException {
        // find latest attendance row for employee with null check_out
        try (PreparedStatement ps = conn.prepareStatement("SELECT id FROM attendance WHERE employee_id = ? AND check_out IS NULL ORDER BY id DESC LIMIT 1")) {
            ps.setInt(1, employeeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    try (PreparedStatement upd = conn.prepareStatement("UPDATE attendance SET check_out = ? WHERE id = ?")) {
                        upd.setString(1, time.format(fmt));
                        upd.setInt(2, id);
                        upd.executeUpdate();
                    }
                } else {
                    throw new SQLException("No open check-in found for employee " + employeeId);
                }
            }
        }
    }

    public List<AttendanceRecord> getAttendanceForEmployee(int employeeId) throws SQLException {
        List<AttendanceRecord> out = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT id, employee_id, check_in, check_out FROM attendance WHERE employee_id = ? ORDER BY check_in")) {
            ps.setInt(1, employeeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(mapRow(rs));
                }
            }
        }
        return out;
    }

    public List<AttendanceRecord> getAttendanceBetween(LocalDateTime from, LocalDateTime to) throws SQLException {
        List<AttendanceRecord> out = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT id, employee_id, check_in, check_out FROM attendance WHERE check_in >= ? AND check_in <= ? ORDER BY check_in")) {
            ps.setString(1, from.format(fmt));
            ps.setString(2, to.format(fmt));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(mapRow(rs));
            }
        }
        return out;
    }

    private AttendanceRecord mapRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int emp = rs.getInt("employee_id");
        String in = rs.getString("check_in");
        String out = rs.getString("check_out");
        LocalDateTime checkIn = LocalDateTime.parse(in, fmt);
        LocalDateTime checkOut = out == null ? null : LocalDateTime.parse(out, fmt);
        return new AttendanceRecord(id, emp, checkIn, checkOut);
    }
}
