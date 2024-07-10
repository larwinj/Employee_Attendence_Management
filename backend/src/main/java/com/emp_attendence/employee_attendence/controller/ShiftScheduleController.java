package com.emp_attendence.employee_attendence.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.emp_attendence.employee_attendence.model.ShiftSchedule;
import com.emp_attendence.employee_attendence.service.ShiftScheduleService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/shiftschedules")
public class ShiftScheduleController {

    @Autowired
    private ShiftScheduleService shiftScheduleService;

    @PostMapping
    public ShiftSchedule createShiftSchedule(@RequestBody ShiftSchedule shiftSchedule) {
        return shiftScheduleService.saveShiftSchedule(shiftSchedule);
    }
    @PostMapping("/sequencedata")
    public String createShiftScheduleSeq(@RequestBody List<ShiftSchedule> shiftSchedule) {
        for(ShiftSchedule i:shiftSchedule){
            shiftScheduleService.saveShiftSchedule(i);
        }
        return "sequence data stored";
    }

    @GetMapping
    public List<ShiftSchedule> getAllShiftSchedules() {
        return shiftScheduleService.getAllShiftSchedules();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShiftSchedule> getShiftScheduleById(@PathVariable int id) {
        Optional<ShiftSchedule> shiftSchedule = shiftScheduleService.getShiftScheduleById(id);

        return shiftSchedule.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //getting shift schedules by emp id
    @GetMapping("/employee/{employeeId}")
    public List<ShiftSchedule> getShiftSchedulesByEmployeeId(@PathVariable int employeeId) {
        return shiftScheduleService.getShiftSchedulesByEmployeeId(employeeId);
    }

    //getting shift schedules by date
    @GetMapping("/date/{shiftDate}")
    public List<ShiftSchedule> getShiftSchedulesByDate(@PathVariable String shiftDate) {
        LocalDate date = LocalDate.parse(shiftDate);
        return shiftScheduleService.getShiftSchedulesByDate(date);
    }

    //getting shift schedules by emp id and date
    @GetMapping("/employee/{employeeId}/date/{shiftDate}")
    public List<ShiftSchedule> getShiftSchedulesByEmployeeIdAndDate(
            @PathVariable int employeeId, @PathVariable String shiftDate) {
        LocalDate date = LocalDate.parse(shiftDate);
        return shiftScheduleService.getShiftSchedulesByEmployeeIdAndDate(employeeId, date);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShiftSchedule> updateShiftSchedule(
            @PathVariable int id, @RequestBody ShiftSchedule shiftScheduleDetails) {

        try {
            ShiftSchedule updatedShiftSchedule = shiftScheduleService.updateShiftSchedule(id, shiftScheduleDetails);
            return ResponseEntity.ok(updatedShiftSchedule);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShiftSchedule(@PathVariable int id) {
        try {
            shiftScheduleService.deleteShiftSchedule(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
