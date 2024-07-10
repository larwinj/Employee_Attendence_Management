package com.emp_attendence.employee_attendence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.emp_attendence.employee_attendence.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    
    List<Employee> findByFirstName(String firstName);
    List<Employee> findByLastName(String lastName);
    List<Employee> findAll();

    @Query("SELECT e.employeeID, CONCAT(e.firstName, ' ', e.lastName), d.departmentName, ar.attendanceDate, " +
           "CASE WHEN ar.checkInTime IS NOT NULL THEN 'Present' ELSE 'Absent' END " +
           "FROM Employee e " +
           "JOIN e.department d " +
           "LEFT JOIN AttendanceRecord ar ON e.employeeID = ar.employee.employeeID " +
           "WHERE d.departmentName = :departmentName " +
           "ORDER BY ar.attendanceDate")
    List<Object[]> findEmployeeAttendanceByDepartmentName(@Param("departmentName") String departmentName);

}