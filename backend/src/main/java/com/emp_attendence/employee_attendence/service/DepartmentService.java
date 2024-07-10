package com.emp_attendence.employee_attendence.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp_attendence.employee_attendence.model.Department;
import com.emp_attendence.employee_attendence.repository.DepartmentRepository;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    //save ...................
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Optional<Department> getDepartmentById(int departmentID) {
        return departmentRepository.findById(departmentID);
    }

    public List<Department> getDepartmentsByName(String departmentName) {
        return departmentRepository.findByDepartmentName(departmentName);
    }

    public Department updateDepartment(int departmentID, Department departmentDetails) {
        Optional<Department> departmentOptional = departmentRepository.findById(departmentID);

        if (departmentOptional.isPresent()) {
            Department department = departmentOptional.get();
            department.setDepartmentName(departmentDetails.getDepartmentName());
            return departmentRepository.save(department);
        } else {
            throw new RuntimeException("Department not found with ID: " + departmentID);
        }
    }
    
    public void deleteDepartment(int departmentID) {
        if (departmentRepository.existsById(departmentID)) {
            departmentRepository.deleteById(departmentID);
        } else {
            throw new RuntimeException("Department not found with ID: " + departmentID);
        }
    }
}

