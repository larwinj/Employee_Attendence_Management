package com.emp_attendence.employee_attendence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.emp_attendence.employee_attendence.model.UserAccess;

@Repository
public interface UserAccessRepository extends JpaRepository<UserAccess, Integer> {
    
    List<UserAccess> findByEmployee_EmployeeID(int employeeID);

    List<UserAccess> findByRole_RoleID(int roleID);
}