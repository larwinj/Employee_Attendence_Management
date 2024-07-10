package com.emp_attendence.employee_attendence.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.emp_attendence.employee_attendence.model.LeaveRequest;
import com.emp_attendence.employee_attendence.service.LeaveRequestService;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/leaverequests")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @PostMapping
    public LeaveRequest createLeaveRequest(@RequestBody LeaveRequest leaveRequest) {
        return leaveRequestService.saveLeaveRequest(leaveRequest);
    }

    @GetMapping
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestService.getAllLeaveRequests();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaveRequest> getLeaveRequestById(@PathVariable int id) {
        Optional<LeaveRequest> leaveRequest = leaveRequestService.getLeaveRequestById(id);

        return leaveRequest.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/employee/{employeeId}")
    public List<LeaveRequest> getLeaveRequestsByEmployeeId(@PathVariable int employeeId) {
        return leaveRequestService.getLeaveRequestsByEmployeeId(employeeId);
    }

    // Get leave requests by status
    @GetMapping("/status/{leaveStatus}")
    public List<LeaveRequest> getLeaveRequestsByStatus(@PathVariable String leaveStatus) {
        return leaveRequestService.getLeaveRequestsByStatus(leaveStatus);
    }

    // Update a leave request
    @PutMapping("/{id}")
    public ResponseEntity<LeaveRequest> updateLeaveRequest(
            @PathVariable int id, @RequestBody LeaveRequest leaveRequestDetails) {

        try {
            LeaveRequest updatedLeaveRequest = leaveRequestService.updateLeaveRequest(id, leaveRequestDetails);
            return ResponseEntity.ok(updatedLeaveRequest);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/details")         //NEED.................
    public ResponseEntity<List<Map<String, Object>>> getLeaveRequestDetails() {
        List<Map<String, Object>> leaveRequestDetails = leaveRequestService.getLeaveRequestDetails();
        return ResponseEntity.ok(leaveRequestDetails);
    }

    @DeleteMapping("/{id}")     //NEED.................
    public ResponseEntity<Void> deleteLeaveRequest(@PathVariable int id) {
        try {
            leaveRequestService.deleteLeaveRequest(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
