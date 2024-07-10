package com.emp_attendence.employee_attendence.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.emp_attendence.employee_attendence.model.Employee;
import com.emp_attendence.employee_attendence.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Save
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(int employeeID) {
        return employeeRepository.findById(employeeID);
    }

    public List<Employee> getEmployeesByFirstName(String firstName) {
        return employeeRepository.findByFirstName(firstName);
    }

    public List<Employee> getEmployeesByLastName(String lastName) {
        return employeeRepository.findByLastName(lastName);
    }

    //UPDATING......................
    public Employee updateEmployee(int employeeID, Employee employeeDetails) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeID);

        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            employee.setFirstName(employeeDetails.getFirstName());
            employee.setLastName(employeeDetails.getLastName());
            employee.setDepartment(employeeDetails.getDepartment());
            employee.setPosition(employeeDetails.getPosition());
            employee.setHireDate(employeeDetails.getHireDate());
            employee.setEmail(employeeDetails.getEmail());
            employee.setPhone(employeeDetails.getPhone());
            employee.setActive(employeeDetails.isActive());
            return employeeRepository.save(employee);
        } else {
            throw new RuntimeException("Employee not found with ID: " + employeeID);
        }
    }

    public List<Map<String, Object>> getEmployeeAttendanceByDepartmentName(String departmentName) {  //NEED
        List<Object[]> results = employeeRepository.findEmployeeAttendanceByDepartmentName(departmentName);
        List<Map<String, Object>> formattedResults = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("id", result[0]);
            resultMap.put("name", result[1]);
            resultMap.put("department", result[2]);
            resultMap.put("attendanceDate", result[3]);
            resultMap.put("status", result[4]);
            formattedResults.add(resultMap);
        }

        return formattedResults;
    }

    public void deleteEmployee(int employeeID) {
        if (employeeRepository.existsById(employeeID)) {
            employeeRepository.deleteById(employeeID);
        } else {
            throw new RuntimeException("Employee not found with ID: " + employeeID);
        }
    }
}
