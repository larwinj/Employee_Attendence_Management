package com.emp_attendence.employee_attendence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.emp_attendence.employee_attendence.model.LeaveRequest;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {
    
    List<LeaveRequest> findByEmployee_EmployeeID(int employeeID);

    List<LeaveRequest> findByLeaveStatus(String leaveStatus);

    @Query("SELECT lr.leaveID, e.employeeID, CONCAT(e.firstName, ' ', e.lastName) AS employeeName, d.departmentName, lr.startDate, lr.endDate " +
           "FROM LeaveRequest lr " +
           "JOIN lr.employee e " +
           "JOIN e.department d")
    List<Object[]> findLeaveRequestDetails();

    

}