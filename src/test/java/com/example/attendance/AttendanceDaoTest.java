package com.example.attendance;

import com.example.attendance.db.Database;
import com.example.attendance.dao.AttendanceDao;
import com.example.attendance.model.AttendanceRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AttendanceDaoTest {
    private Path tmpDb;
    private Database db;
    private Connection conn;

    @BeforeEach
    public void setup() throws Exception {
        tmpDb = Files.createTempFile("attendance-test", ".db");
        db = new Database("jdbc:sqlite:" + tmpDb.toString());
        db.init();
        conn = db.getConnection();
    }

    @AfterEach
    public void tearDown() throws Exception {
        conn.close();
        Files.deleteIfExists(tmpDb);
    }

    @Test
    public void checkInOutLifecycle() throws Exception {
        AttendanceDao dao = new AttendanceDao(conn);
        dao.addEmployee(10, "Test");
        LocalDateTime in = LocalDateTime.now().minusHours(2);
        LocalDateTime out = LocalDateTime.now();
        dao.checkIn(10, in);
        dao.checkOut(10, out);
        List<AttendanceRecord> recs = dao.getAttendanceForEmployee(10);
        assertEquals(1, recs.size());
        AttendanceRecord r = recs.get(0);
        assertEquals(10, r.getEmployeeId());
        assertNotNull(r.getCheckIn());
        assertNotNull(r.getCheckOut());
    }
}
