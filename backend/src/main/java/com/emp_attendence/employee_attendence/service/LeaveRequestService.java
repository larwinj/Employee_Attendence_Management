package com.emp_attendence.employee_attendence.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp_attendence.employee_attendence.model.LeaveRequest;
import com.emp_attendence.employee_attendence.repository.LeaveRequestRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    //savingg a new leave request
    public LeaveRequest saveLeaveRequest(LeaveRequest leaveRequest) {
        return leaveRequestRepository.save(leaveRequest);
    }

    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }

    public Optional<LeaveRequest> getLeaveRequestById(int leaveID) {
        return leaveRequestRepository.findById(leaveID);
    }

    //getting leave requests by emp id
    public List<LeaveRequest> getLeaveRequestsByEmployeeId(int employeeID) {
        return leaveRequestRepository.findByEmployee_EmployeeID(employeeID);
    }

    //getting leave requests by status
    public List<LeaveRequest> getLeaveRequestsByStatus(String status) {
        return leaveRequestRepository.findByLeaveStatus(status);
    }

    //updating leave request's details
    public LeaveRequest updateLeaveRequest(int leaveID, LeaveRequest leaveRequestDetails) {
        Optional<LeaveRequest> leaveRequestOptional = leaveRequestRepository.findById(leaveID);

        if (leaveRequestOptional.isPresent()) {
            LeaveRequest leaveRequest = leaveRequestOptional.get();
            leaveRequest.setLeaveType(leaveRequestDetails.getLeaveType());
            leaveRequest.setStartDate(leaveRequestDetails.getStartDate());
            leaveRequest.setEndDate(leaveRequestDetails.getEndDate());
            leaveRequest.setLeaveStatus(leaveRequestDetails.getLeaveStatus());
            leaveRequest.setReason(leaveRequestDetails.getReason());
            leaveRequest.setEmployee(leaveRequestDetails.getEmployee());
            return leaveRequestRepository.save(leaveRequest);
        } else {
            throw new RuntimeException("Leave request not found with ID: " + leaveID);
        }
    }

    public List<Map<String, Object>> getLeaveRequestDetails() {
        List<Object[]> results = leaveRequestRepository.findLeaveRequestDetails();
        return results.stream().map(result -> {
            Map<String, Object> details = new HashMap<>();
            details.put("leaveID", result[0]);
            details.put("employeeID", result[1]);
            details.put("employeeName", result[2]);
            details.put("department", result[3]);
            details.put("startDate", result[4]);
            details.put("endDate", result[5]);
            return details;
        }).toList();
    }

    public void deleteLeaveRequest(int leaveID) {
        if (leaveRequestRepository.existsById(leaveID)) {
            leaveRequestRepository.deleteById(leaveID);
        } else {
            throw new RuntimeException("Leave request not found with ID: " + leaveID);
        }
    }
}
