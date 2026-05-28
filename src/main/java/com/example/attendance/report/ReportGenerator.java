package com.example.attendance.report;

import com.example.attendance.model.AttendanceRecord;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportGenerator {
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public String generateSummary(List<AttendanceRecord> records) {
        StringBuilder sb = new StringBuilder();
        sb.append("EmployeeID,CheckIn,CheckOut,Duration(minutes)\n");
        Map<Integer, List<AttendanceRecord>> byEmp = records.stream().collect(Collectors.groupingBy(AttendanceRecord::getEmployeeId));
        for (Map.Entry<Integer, List<AttendanceRecord>> e : byEmp.entrySet()) {
            for (AttendanceRecord r : e.getValue()) {
                String in = r.getCheckIn() == null ? "" : r.getCheckIn().format(fmt);
                String out = r.getCheckOut() == null ? "" : r.getCheckOut().format(fmt);
                long mins = 0;
                if (r.getCheckIn() != null && r.getCheckOut() != null) {
                    mins = Duration.between(r.getCheckIn(), r.getCheckOut()).toMinutes();
                }
                sb.append(String.format("%d,%s,%s,%d\n", r.getEmployeeId(), in, out, mins));
            }
        }
        return sb.toString();
    }
}
