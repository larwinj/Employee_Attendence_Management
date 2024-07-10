package com.emp_attendence.employee_attendence.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.emp_attendence.employee_attendence.model.AttendanceRecord;
import com.emp_attendence.employee_attendence.model.Employee;
import com.emp_attendence.employee_attendence.repository.AttendenceRecordRepository;
import com.emp_attendence.employee_attendence.repository.EmployeeRepository;
// import com.emp_attendence.employee_attendence.model.Employee;
import com.emp_attendence.employee_attendence.service.AttendanceRecordService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/attendancerecords")
public class AttendanceRecordController {

    @Autowired
    private AttendanceRecordService attendanceRecordService;

    @Autowired
    private AttendenceRecordRepository attendanceRecordRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping
    public AttendanceRecord createAttendanceRecord(@RequestBody AttendanceRecord attendanceRecord) {
        return attendanceRecordService.saveAttendanceRecord(attendanceRecord);
    }


    @PostMapping("/addData/{date}/{currentTime}")
    public void addDataToAttendanceRecord(
            @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable("currentTime") String currentTime,
            @RequestBody Map<String, Object>[] records) {

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime checkInTime = LocalTime.parse(currentTime, timeFormatter);

        for (Map<String, Object> record : records) {
            String employeeIDStr = record.get("id").toString();
            int employeeID = Integer.parseInt(employeeIDStr);
            // String employeeName = record.get("name").toString();
            // String department = record.get("department").toString();
            // String status = record.get("status").toString();

            // Fetch the employee from the database
            Optional<Employee> optionalEmployee = employeeRepository.findById(employeeID);

            if (optionalEmployee.isPresent()) {
                Employee employee = optionalEmployee.get();

                // Create a new AttendanceRecord object
                AttendanceRecord attendanceRecord = new AttendanceRecord();
                attendanceRecord.setEmployee(employee);
                attendanceRecord.setAttendanceDate(date);
                attendanceRecord.setCheckInTime(checkInTime);
                attendanceRecord.setAttendanceType("Manual");

                // Save the record to the database
                attendanceRecordRepository.save(attendanceRecord);
            } else {
                System.out.println("Employee with ID " + employeeID + " not found.");
            }
        }
    }

    @GetMapping
    public List<AttendanceRecord> getAllAttendanceRecords() {
        return attendanceRecordService.getAllAttendanceRecords();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceRecord> getAttendanceRecordById(@PathVariable int id) {
        Optional<AttendanceRecord> attendanceRecord = attendanceRecordService.getAttendanceRecordById(id);

        return attendanceRecord.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/date/{attendanceDate}")
    public List<AttendanceRecord> getAttendanceRecordsByDate(@PathVariable String attendanceDate) {
        LocalDate date = LocalDate.parse(attendanceDate);
        return attendanceRecordService.getAttendanceRecordsByDate(date);
    }

    //checking emp is present on particular data
    @GetMapping("/checkAttendance")
    public ResponseEntity<String> checkEmployeeAttendance(@RequestParam int employeeID, @RequestParam String date) {
        try {
            LocalDate attendanceDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
            boolean isPresent = attendanceRecordService.isEmployeePresent(employeeID, attendanceDate);
            String status = isPresent ? "Present" : "Absent";
            return ResponseEntity.ok("Employee is " + status + " on " + attendanceDate);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Invalid date format. Please use YYYY-MM-DD.");
        }
    }

    @GetMapping("/employee/{employeeId}")
    public List<AttendanceRecord> getAttendanceRecordsByEmployeeId(@PathVariable int employeeId) {
        return attendanceRecordService.getAttendanceRecordsByEmployeeId(employeeId);
    }

    // Get attendance records by employee ID and date
    @GetMapping("/employee/{employeeId}/date/{attendanceDate}")
    public Optional<AttendanceRecord> getAttendanceRecordsByEmployeeIdAndDate(
            @PathVariable int employeeId, @PathVariable String attendanceDate) {
        LocalDate date = LocalDate.parse(attendanceDate);
        return attendanceRecordService.getAttendanceRecordsByEmployeeIdAndDate(employeeId, date);
    }

    // Update an attendance record
    @PutMapping("/{id}")
    public ResponseEntity<AttendanceRecord> updateAttendanceRecord(
            @PathVariable int id, @RequestBody AttendanceRecord attendanceRecordDetails) {

        try {
            AttendanceRecord updatedAttendanceRecord = attendanceRecordService.updateAttendanceRecord(id, attendanceRecordDetails);
            return ResponseEntity.ok(updatedAttendanceRecord);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/attendanceStatus")
    public ResponseEntity<Map<String, List<Map<String, String>>>> getAttendanceStatus(@RequestParam String date) {
            LocalDate attendanceDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
            Map<String, List<Map<String, String>>> attendanceStatus = attendanceRecordService.getAttendanceStatusByDate(attendanceDate);
            return ResponseEntity.ok(attendanceStatus);
    }
    //NEED......
    @GetMapping("/detailsByDate")
    public ResponseEntity<List<Map<String, Object>>> getAttendanceDetailsByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Map<String, Object>> attendanceDetails = attendanceRecordService.getAttendanceDetailsByDate(date);
        return ResponseEntity.ok(attendanceDetails);
    }

    // ------------------------------------------------------------
    //NEED......
    @GetMapping("/detailsByName")
    public ResponseEntity<List<Map<String, Object>>> getAttendanceDetailsByempName(
            @RequestParam("employeeName") String name) {
        List<Map<String, Object>> attendanceDetails = attendanceRecordService.getAttendanceDetailsByEmpName(name);
        return ResponseEntity.ok(attendanceDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendanceRecord(@PathVariable int id) {
        try {
            attendanceRecordService.deleteAttendanceRecord(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
