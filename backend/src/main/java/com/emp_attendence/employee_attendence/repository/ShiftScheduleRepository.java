package com.emp_attendence.employee_attendence.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.emp_attendence.employee_attendence.model.ShiftSchedule;

@Repository
public interface ShiftScheduleRepository extends JpaRepository<ShiftSchedule, Integer> {
    
    List<ShiftSchedule> findByEmployee_EmployeeID(int employeeID);

    List<ShiftSchedule> findByShiftDate(LocalDate shiftDate);

    List<ShiftSchedule> findByEmployee_EmployeeIDAndShiftDate(int employeeID, LocalDate shiftDate);
}