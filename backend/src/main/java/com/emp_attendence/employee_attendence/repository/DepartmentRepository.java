package com.emp_attendence.employee_attendence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.emp_attendence.employee_attendence.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    
    List<Department> findByDepartmentName(String departmentName);
}