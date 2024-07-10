package com.emp_attendence.employee_attendence.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp_attendence.employee_attendence.model.OvertimeRecord;
import com.emp_attendence.employee_attendence.repository.OvertimeRecordRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OvertimeRecordService {

    @Autowired
    private OvertimeRecordRepository overtimeRecordRepository;

    public OvertimeRecord saveOvertimeRecord(OvertimeRecord overtimeRecord) {
        return overtimeRecordRepository.save(overtimeRecord);
    }

    public List<OvertimeRecord> getAllOvertimeRecords() {
        return overtimeRecordRepository.findAll();
    }

    public Optional<OvertimeRecord> getOvertimeRecordById(int overtimeID) {
        return overtimeRecordRepository.findById(overtimeID);
    }

    //getting overtime records by employee id
    public List<OvertimeRecord> getOvertimeRecordsByEmployeeId(int employeeID) {
        return overtimeRecordRepository.findByEmployee_EmployeeID(employeeID);
    }

    public OvertimeRecord updateOvertimeRecord(int overtimeID, OvertimeRecord overtimeRecordDetails) {
        Optional<OvertimeRecord> overtimeRecordOptional = overtimeRecordRepository.findById(overtimeID);

        if (overtimeRecordOptional.isPresent()) {
            OvertimeRecord overtimeRecord = overtimeRecordOptional.get();
            overtimeRecord.setOvertimeDate(overtimeRecordDetails.getOvertimeDate());
            overtimeRecord.setOvertimeHours(overtimeRecordDetails.getOvertimeHours());
            overtimeRecord.setReason(overtimeRecordDetails.getReason());
            overtimeRecord.setEmployee(overtimeRecordDetails.getEmployee());
            return overtimeRecordRepository.save(overtimeRecord);
        } else {
            throw new RuntimeException("Overtime record not found with ID: " + overtimeID);
        }
    }
    //NEED............
    public List<Map<String, Object>> getOvertimeDetails() {
        List<Object[]> results = overtimeRecordRepository.findOvertimeDetails();
        return results.stream().map(result -> {
            Map<String, Object> details = new HashMap<>();
            details.put("employeeID", result[0]);
            details.put("empName", result[1]);
            details.put("overtimeDate", result[2]);
            details.put("overtimeHours", result[3]);
            details.put("reason", result[4]);
            return details;
        }).toList();
    }

    public void deleteOvertimeRecord(int overtimeID) {
        if (overtimeRecordRepository.existsById(overtimeID)) {
            overtimeRecordRepository.deleteById(overtimeID);
        } else {
            throw new RuntimeException("Overtime record not found with ID: " + overtimeID);
        }
    }
}
