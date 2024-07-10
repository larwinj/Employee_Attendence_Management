package com.emp_attendence.employee_attendence.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp_attendence.employee_attendence.model.ShiftSchedule;
import com.emp_attendence.employee_attendence.repository.ShiftScheduleRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ShiftScheduleService {

    @Autowired
    private ShiftScheduleRepository shiftScheduleRepository;

    public ShiftSchedule saveShiftSchedule(ShiftSchedule shiftSchedule) {
        return shiftScheduleRepository.save(shiftSchedule);
    }

    public List<ShiftSchedule> getAllShiftSchedules() {
        return shiftScheduleRepository.findAll();
    }

    public Optional<ShiftSchedule> getShiftScheduleById(int scheduleID) {
        return shiftScheduleRepository.findById(scheduleID);
    }

    public List<ShiftSchedule> getShiftSchedulesByEmployeeId(int employeeID) {
        return shiftScheduleRepository.findByEmployee_EmployeeID(employeeID);
    }

    //getting shift schedules by date
    public List<ShiftSchedule> getShiftSchedulesByDate(LocalDate shiftDate) {
        return shiftScheduleRepository.findByShiftDate(shiftDate);
    }

    //getting shift schedules by emp id and date
    public List<ShiftSchedule> getShiftSchedulesByEmployeeIdAndDate(int employeeID, LocalDate shiftDate) {
        return shiftScheduleRepository.findByEmployee_EmployeeIDAndShiftDate(employeeID, shiftDate);
    }

    public ShiftSchedule updateShiftSchedule(int scheduleID, ShiftSchedule shiftScheduleDetails) {
        Optional<ShiftSchedule> shiftScheduleOptional = shiftScheduleRepository.findById(scheduleID);

        if (shiftScheduleOptional.isPresent()) {
            ShiftSchedule shiftSchedule = shiftScheduleOptional.get();
            shiftSchedule.setShiftDate(shiftScheduleDetails.getShiftDate());
            shiftSchedule.setStartTime(shiftScheduleDetails.getStartTime());
            shiftSchedule.setEndTime(shiftScheduleDetails.getEndTime());
            shiftSchedule.setShiftType(shiftScheduleDetails.getShiftType());
            shiftSchedule.setEmployee(shiftScheduleDetails.getEmployee());
            return shiftScheduleRepository.save(shiftSchedule);
        } else {
            throw new RuntimeException("Shift schedule not found with ID: " + scheduleID);
        }
    }

    public void deleteShiftSchedule(int scheduleID) {
        if (shiftScheduleRepository.existsById(scheduleID)) {
            shiftScheduleRepository.deleteById(scheduleID);
        } else {
            throw new RuntimeException("Shift schedule not found with ID: " + scheduleID);
        }
    }
}
