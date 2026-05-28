const API_URL = 'http://localhost:8080/api';

// Tab switching
function showTab(tabName) {
    // Hide all tabs
    const tabs = document.querySelectorAll('.tab-content');
    tabs.forEach(tab => tab.classList.remove('active'));
    
    // Show selected tab
    document.getElementById(tabName).classList.add('active');
    
    // Update nav buttons
    const navBtns = document.querySelectorAll('.nav-btn');
    navBtns.forEach(btn => btn.classList.remove('active'));
    event.target.classList.add('active');
    
    // Load dashboard data if dashboard tab is selected
    if (tabName === 'dashboard') {
        loadDashboard();
    }
}

// Load Dashboard Data
async function loadDashboard() {
    try {
        // Get attendance for today
        const now = new Date();
        const todayStart = new Date(now.getFullYear(), now.getMonth(), now.getDate());
        const todayEnd = new Date(todayStart.getTime() + 24*60*60*1000);
        
        const from = todayStart.toISOString().slice(0, 16);
        const to = todayEnd.toISOString().slice(0, 16);
        
        const response = await fetch(`${API_URL}/attendance/range?from=${from}&to=${to}`);
        const data = await response.json();
        
        if (data.status === 'success' && data.data) {
            const records = data.data;
            
            // Calculate unique employees
            const uniqueEmployees = new Set(records.map(r => r.employeeId));
            document.getElementById('totalEmployees').textContent = uniqueEmployees.size;
            
            // Count today's check-ins
            document.getElementById('todayCheckins').textContent = records.length;
            
            // Count currently checked in (no checkout)
            const currentlyChecked = records.filter(r => !r.checkOut).length;
            document.getElementById('currentlyChecked').textContent = currentlyChecked;
        } else {
            document.getElementById('totalEmployees').textContent = '0';
            document.getElementById('todayCheckins').textContent = '0';
            document.getElementById('currentlyChecked').textContent = '0';
        }
    } catch (error) {
        console.error('Dashboard error:', error);
        document.getElementById('totalEmployees').textContent = '0';
        document.getElementById('todayCheckins').textContent = '0';
        document.getElementById('currentlyChecked').textContent = '0';
    }
}

// Add Employee
async function addEmployee() {
    const id = document.getElementById('newEmpId').value;
    const name = document.getElementById('newEmpName').value;
    
    if (!id || !name) {
        showResult('employeeResult', 'Please fill in all fields', 'error');
        return;
    }
    
    try {
        const response = await fetch(`${API_URL}/employee`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ id: parseInt(id), name: name })
        });
        
        const data = await response.json();
        
        if (data.status === 'success') {
            showResult('employeeResult', `✓ ${data.message}`, 'success');
            document.getElementById('newEmpId').value = '';
            document.getElementById('newEmpName').value = '';
        } else {
            showResult('employeeResult', `✗ ${data.message}`, 'error');
        }
    } catch (error) {
        showResult('employeeResult', `✗ Error: ${error.message}`, 'error');
    }
}

// Check In
async function handleCheckIn() {
    const employeeId = document.getElementById('employeeId').value;
    
    if (!employeeId) {
        showResult('checkinResult', 'Please enter an employee ID', 'error');
        return;
    }
    
    try {
        const response = await fetch(`${API_URL}/checkin`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ employeeId: parseInt(employeeId) })
        });
        
        const data = await response.json();
        
        if (data.status === 'success') {
            const time = new Date(data.time).toLocaleString();
            showResult('checkinResult', `✓ ${data.message} at ${time}`, 'success');
            document.getElementById('employeeId').value = '';
        } else {
            showResult('checkinResult', `✗ ${data.message}`, 'error');
        }
    } catch (error) {
        showResult('checkinResult', `✗ Error: ${error.message}`, 'error');
    }
}

// Check Out
async function handleCheckOut() {
    const employeeId = document.getElementById('employeeId').value;
    
    if (!employeeId) {
        showResult('checkinResult', 'Please enter an employee ID', 'error');
        return;
    }
    
    try {
        const response = await fetch(`${API_URL}/checkout`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ employeeId: parseInt(employeeId) })
        });
        
        const data = await response.json();
        
        if (data.status === 'success') {
            const time = new Date(data.time).toLocaleString();
            showResult('checkinResult', `✓ ${data.message} at ${time}`, 'success');
            document.getElementById('employeeId').value = '';
        } else {
            showResult('checkinResult', `✗ ${data.message}`, 'error');
        }
    } catch (error) {
        showResult('checkinResult', `✗ Error: ${error.message}`, 'error');
    }
}

// View Attendance
async function viewAttendance() {
    const employeeId = document.getElementById('viewEmployeeId').value;
    
    if (!employeeId) {
        document.getElementById('attendanceTable').innerHTML = '';
        return;
    }
    
    try {
        const response = await fetch(`${API_URL}/attendance?employeeId=${employeeId}`);
        const data = await response.json();
        
        if (data.status === 'success' && data.data.length > 0) {
            let html = '<table><tr><th>Check In</th><th>Check Out</th><th>Duration (minutes)</th></tr>';
            
            data.data.forEach(record => {
                const checkIn = new Date(record.checkIn).toLocaleString();
                const checkOut = record.checkOut ? new Date(record.checkOut).toLocaleString() : 'Not checked out';
                let duration = '-';
                
                if (record.checkIn && record.checkOut) {
                    const diffMs = new Date(record.checkOut) - new Date(record.checkIn);
                    duration = Math.round(diffMs / 60000);
                }
                
                html += `<tr>
                    <td>${checkIn}</td>
                    <td>${checkOut}</td>
                    <td>${duration}</td>
                </tr>`;
            });
            
            html += '</table>';
            document.getElementById('attendanceTable').innerHTML = html;
        } else {
            document.getElementById('attendanceTable').innerHTML = '<p style="color: #666;">No attendance records found.</p>';
        }
    } catch (error) {
        document.getElementById('attendanceTable').innerHTML = `<p style="color: red;">Error: ${error.message}</p>`;
    }
}

// Generate Report
async function generateReport() {
    const from = document.getElementById('reportFrom').value;
    const to = document.getElementById('reportTo').value;
    
    if (!from || !to) {
        document.getElementById('reportTable').innerHTML = '';
        return;
    }
    
    try {
        const response = await fetch(`${API_URL}/attendance/range?from=${from}&to=${to}`);
        const data = await response.json();
        
        if (data.status === 'success' && data.data.length > 0) {
            let html = '<table><tr><th>Employee ID</th><th>Check In</th><th>Check Out</th><th>Duration (minutes)</th></tr>';
            
            data.data.forEach(record => {
                const checkIn = new Date(record.checkIn).toLocaleString();
                const checkOut = record.checkOut ? new Date(record.checkOut).toLocaleString() : 'Ongoing';
                let duration = '-';
                
                if (record.checkIn && record.checkOut) {
                    const diffMs = new Date(record.checkOut) - new Date(record.checkIn);
                    duration = Math.round(diffMs / 60000);
                }
                
                html += `<tr>
                    <td>${record.employeeId}</td>
                    <td>${checkIn}</td>
                    <td>${checkOut}</td>
                    <td>${duration}</td>
                </tr>`;
            });
            
            html += '</table>';
            document.getElementById('reportTable').innerHTML = html;
        } else {
            document.getElementById('reportTable').innerHTML = '<p style="color: #666;">No attendance records found for the selected period.</p>';
        }
    } catch (error) {
        document.getElementById('reportTable').innerHTML = `<p style="color: red;">Error: ${error.message}</p>`;
    }
}

// Show result message
function showResult(elementId, message, type) {
    const element = document.getElementById(elementId);
    element.textContent = message;
    element.className = `result-message ${type}`;
    
    // Auto-hide success messages after 5 seconds
    if (type === 'success') {
        setTimeout(() => {
            element.className = 'result-message';
        }, 5000);
    }
}

// Initialize date pickers with current date
window.addEventListener('DOMContentLoaded', function() {
    const now = new Date();
    const today = now.toISOString().slice(0, 16);
    const tomorrow = new Date(now.getTime() + 24*60*60*1000).toISOString().slice(0, 16);
    
    document.getElementById('reportFrom').value = today;
    document.getElementById('reportTo').value = tomorrow;
});
