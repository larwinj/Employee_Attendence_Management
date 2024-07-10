package com.emp_attendence.employee_attendence.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.emp_attendence.employee_attendence.model.OvertimeRecord;
import com.emp_attendence.employee_attendence.service.OvertimeRecordService;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/overtimerecords")
public class OvertimeRecordController {

    @Autowired
    private OvertimeRecordService overtimeRecordService;

    @PostMapping
    public OvertimeRecord createOvertimeRecord(@RequestBody OvertimeRecord overtimeRecord) {
        return overtimeRecordService.saveOvertimeRecord(overtimeRecord);
    }

    @GetMapping
    public List<OvertimeRecord> getAllOvertimeRecords() {
        return overtimeRecordService.getAllOvertimeRecords();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OvertimeRecord> getOvertimeRecordById(@PathVariable int id) {
        Optional<OvertimeRecord> overtimeRecord = overtimeRecordService.getOvertimeRecordById(id);

        return overtimeRecord.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //getting overtime records by emp id
    @GetMapping("/employee/{employeeId}")
    public List<OvertimeRecord> getOvertimeRecordsByEmployeeId(@PathVariable int employeeId) {
        return overtimeRecordService.getOvertimeRecordsByEmployeeId(employeeId);
    }

    //Update an overtime record
    @PutMapping("/{id}")
    public ResponseEntity<OvertimeRecord> updateOvertimeRecord(
            @PathVariable int id, @RequestBody OvertimeRecord overtimeRecordDetails) {

        try {
            OvertimeRecord updatedOvertimeRecord = overtimeRecordService.updateOvertimeRecord(id, overtimeRecordDetails);
            return ResponseEntity.ok(updatedOvertimeRecord);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    //NEED.....
    @GetMapping("/details")
    public ResponseEntity<List<Map<String, Object>>> getOvertimeDetails() {
        List<Map<String, Object>> overtimeDetails = overtimeRecordService.getOvertimeDetails();
        return ResponseEntity.ok(overtimeDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOvertimeRecord(@PathVariable int id) {
        try {
            overtimeRecordService.deleteOvertimeRecord(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
