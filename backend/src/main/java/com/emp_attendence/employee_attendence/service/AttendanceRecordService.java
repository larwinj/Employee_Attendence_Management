package com.emp_attendence.employee_attendence.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp_attendence.employee_attendence.model.AttendanceRecord;
import com.emp_attendence.employee_attendence.model.Employee;
import com.emp_attendence.employee_attendence.repository.AttendenceRecordRepository;
import com.emp_attendence.employee_attendence.repository.EmployeeRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendanceRecordService {

    @Autowired
    private AttendenceRecordRepository attendanceRecordRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    //save...............
    public AttendanceRecord saveAttendanceRecord(AttendanceRecord attendanceRecord) {
        return attendanceRecordRepository.save(attendanceRecord);
    }

    public List<AttendanceRecord> getAllAttendanceRecords() {
        return attendanceRecordRepository.findAll();
    }

    public boolean isEmployeePresent(int employeeID, LocalDate date) {
        Optional<AttendanceRecord> attendanceRecord = attendanceRecordRepository.findByEmployee_EmployeeIDAndAttendanceDate(employeeID, date);
        return attendanceRecord.isPresent();
    }

    //getting attendance record by id...............
    public Optional<AttendanceRecord> getAttendanceRecordById(int attendanceID) {
        return attendanceRecordRepository.findById(attendanceID);
    }

    //getting attendance records by date...............
    public List<AttendanceRecord> getAttendanceRecordsByDate(LocalDate attendanceDate) {
        return attendanceRecordRepository.findByAttendanceDate(attendanceDate);
    }

    //getting attendance records by emp id...............
    public List<AttendanceRecord> getAttendanceRecordsByEmployeeId(int employeeID) {
        return attendanceRecordRepository.findByEmployee_EmployeeID(employeeID);
    }

    //getting attendance records by emp id and date...............
    public Optional<AttendanceRecord> getAttendanceRecordsByEmployeeIdAndDate(int employeeID, LocalDate attendanceDate) {
        return attendanceRecordRepository.findByEmployee_EmployeeIDAndAttendanceDate(employeeID, attendanceDate);
    }

    //updating attendance record's details...............
    public AttendanceRecord updateAttendanceRecord(int attendanceID, AttendanceRecord attendanceRecordDetails) {
        Optional<AttendanceRecord> attendanceRecordOptional = attendanceRecordRepository.findById(attendanceID);

        if (attendanceRecordOptional.isPresent()) {
            AttendanceRecord attendanceRecord = attendanceRecordOptional.get();
            attendanceRecord.setAttendanceDate(attendanceRecordDetails.getAttendanceDate());
            attendanceRecord.setCheckInTime(attendanceRecordDetails.getCheckInTime());
            attendanceRecord.setCheckOutTime(attendanceRecordDetails.getCheckOutTime());
            attendanceRecord.setAttendanceType(attendanceRecordDetails.getAttendanceType());
            attendanceRecord.setEmployee(attendanceRecordDetails.getEmployee());
            return attendanceRecordRepository.save(attendanceRecord);
        } else {
            throw new RuntimeException("Attendance record not found with ID: " + attendanceID);
        }
    }

    //getting the list of emp present and absent on a particular date
    public Map<String, List<Map<String, String>>> getAttendanceStatusByDate(LocalDate date) {

        List<Employee> allEmployees = employeeRepository.findAll();
        
        List<AttendanceRecord> attendanceRecords = attendanceRecordRepository.findByAttendanceDate(date);

        // Get the list of present employee IDs
        List<Integer> presentEmployeeIds = attendanceRecords.stream()
                .map(record -> record.getEmployee().getEmployeeID())
                .collect(Collectors.toList());

        // Split employees into present and absent lists
        List<Map<String, String>> presentEmployees = allEmployees.stream()
                .filter(employee -> presentEmployeeIds.contains(employee.getEmployeeID()))
                .map(employee -> Map.of(
                        "employeeID", String.valueOf(employee.getEmployeeID()),
                        "employeeName", employee.getFirstName() + " " + employee.getLastName()
                ))
                .collect(Collectors.toList());

        List<Map<String, String>> absentEmployees = allEmployees.stream()
                .filter(employee -> !presentEmployeeIds.contains(employee.getEmployeeID()))
                .map(employee -> Map.of(
                        "employeeID", String.valueOf(employee.getEmployeeID()),
                        "employeeName", employee.getFirstName() + " " + employee.getLastName()
                ))
                .collect(Collectors.toList());

        return Map.of(
                "present", presentEmployees,
                "absent", absentEmployees
        );
    }
    // ---------------------------------------------------------

    public List<Map<String, Object>> getAttendanceDetailsByDate(LocalDate date) {                 //NEED...............
        List<Object[]> results = attendanceRecordRepository.findAttendanceDetailsByDate(date);
        return results.stream().map(result -> {
            Map<String, Object> details = new HashMap<>();
            details.put("id", result[0]);
            details.put("name", result[1]);
            details.put("attendanceDate", result[2]);
            details.put("department", result[3]);
            details.put("status", result[4]);
            return details;
        }).toList();
    }

    //---------------------------------------------------------
    public List<Map<String, Object>> getAttendanceDetailsByEmpName(String name) {                 //NEED...............
        List<Object[]> results = attendanceRecordRepository.findAttendanceDetailsByEmpName(name);
        return results.stream().map(result -> {
            Map<String, Object> details = new HashMap<>();
            details.put("id", result[0]);
            details.put("name", result[1]);
            details.put("attendanceDate", result[2]);
            details.put("department", result[3]);
            details.put("status", result[4]);
            return details;
        }).toList();
    }

    //...............
    public void deleteAttendanceRecord(int attendanceID) {
        if (attendanceRecordRepository.existsById(attendanceID)) {
            attendanceRecordRepository.deleteById(attendanceID);
        } else {
            throw new RuntimeException("Attendance record not found with ID: " + attendanceID);
        }
    }
}
