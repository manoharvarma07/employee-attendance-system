package com.example.attendance;

import com.example.attendance.model.AttendanceRecord;
import com.example.attendance.report.ReportGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReportGeneratorTest {
    @Test
    public void generateSummaryProducesCsv() {
        LocalDateTime in = LocalDateTime.of(2023,1,1,9,0);
        LocalDateTime out = LocalDateTime.of(2023,1,1,17,0);
        AttendanceRecord r = new AttendanceRecord(1, 5, in, out);
        ReportGenerator rg = new ReportGenerator();
        String csv = rg.generateSummary(List.of(r));
        assertTrue(csv.contains("EmployeeID,CheckIn,CheckOut,Duration"));
        assertTrue(csv.contains("5,2023-01-01 09:00,2023-01-01 17:00,480"));
    }
}
