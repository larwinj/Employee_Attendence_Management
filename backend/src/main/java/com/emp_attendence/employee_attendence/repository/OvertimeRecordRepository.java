package com.emp_attendence.employee_attendence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.emp_attendence.employee_attendence.model.OvertimeRecord;

@Repository
public interface OvertimeRecordRepository extends JpaRepository<OvertimeRecord, Integer> {
    
    List<OvertimeRecord> findByEmployee_EmployeeID(int employeeID);

    @Query("SELECT e.employeeID, CONCAT(e.firstName, ' ', e.lastName) AS empName, " +
           "o.overtimeDate, o.overtimeHours ,o.reason " +
           "FROM OvertimeRecord o " +
           "JOIN o.employee e")
    List<Object[]> findOvertimeDetails();

}
