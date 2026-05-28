package com.example.attendance.model;

import java.time.LocalDateTime;

public class AttendanceRecord {
    private final int id;
    private final int employeeId;
    private final LocalDateTime checkIn;
    private final LocalDateTime checkOut;

    public AttendanceRecord(int id, int employeeId, LocalDateTime checkIn, LocalDateTime checkOut) {
        this.id = id;
        this.employeeId = employeeId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public int getId() {
        return id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }
}
