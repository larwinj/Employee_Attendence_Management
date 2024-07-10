package com.emp_attendence.employee_attendence.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.emp_attendence.employee_attendence.model.AttendanceRecord;

@Repository
public interface AttendenceRecordRepository extends JpaRepository<AttendanceRecord, Integer> {
    
    List<AttendanceRecord> findByAttendanceDate(LocalDate attendanceDate);

    List<AttendanceRecord> findByEmployee_EmployeeID(int employeeID);
    
    Optional<AttendanceRecord> findByEmployee_EmployeeIDAndAttendanceDate(int employeeID, LocalDate attendanceDate);

    @Query("SELECT e.employeeID, CONCAT(e.firstName, ' ', e.lastName) AS employeeName, " +
           "ar.attendanceDate, d.departmentName, 'present' AS status " +
           "FROM AttendanceRecord ar " +
           "JOIN ar.employee e " +
           "JOIN e.department d " +
           "WHERE ar.attendanceDate = :date")
    List<Object[]> findAttendanceDetailsByDate(LocalDate date);

    @Query("SELECT e.employeeID, CONCAT(e.firstName, ' ', e.lastName) AS employeeName, " +
       "ar.attendanceDate, d.departmentName, 'present' AS status " +
       "FROM AttendanceRecord ar " +
       "JOIN ar.employee e " +
       "JOIN e.department d " +
       "WHERE CONCAT(e.firstName, ' ', e.lastName) = :name")
    List<Object[]> findAttendanceDetailsByEmpName(@Param("name") String name);
}
