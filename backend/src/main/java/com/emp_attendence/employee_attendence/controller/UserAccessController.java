package com.emp_attendence.employee_attendence.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.emp_attendence.employee_attendence.model.UserAccess;
import com.emp_attendence.employee_attendence.service.UserAccessService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/useraccess")
public class UserAccessController {

    @Autowired
    private UserAccessService userAccessService;


    @PostMapping
    public UserAccess createUserAccess(@RequestBody UserAccess userAccess) {
        return userAccessService.saveUserAccess(userAccess);
    }

    @PostMapping("/sequence")
    public String createUserAccessseq(@RequestBody List<UserAccess> userAccess) {
        for(UserAccess i:userAccess){
            userAccessService.saveUserAccess(i);
        }
        return "Data Stored Successfully";
    }

    @GetMapping
    public List<UserAccess> getAllUserAccessRecords() {
        return userAccessService.getAllUserAccessRecords();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAccess> getUserAccessById(@PathVariable int id) {
        Optional<UserAccess> userAccess = userAccessService.getUserAccessById(id);

        return userAccess.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/employee/{employeeId}")
    public List<UserAccess> getUserAccessRecordsByEmployeeId(@PathVariable int employeeId) {
        return userAccessService.getUserAccessRecordsByEmployeeId(employeeId);
    }

    @GetMapping("/role/{roleId}")
    public List<UserAccess> getUserAccessRecordsByRoleId(@PathVariable int roleId) {
        return userAccessService.getUserAccessRecordsByRoleId(roleId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserAccess> updateUserAccess(
            @PathVariable int id, @RequestBody UserAccess userAccessDetails) {

        try {
            UserAccess updatedUserAccess = userAccessService.updateUserAccess(id, userAccessDetails);
            return ResponseEntity.ok(updatedUserAccess);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserAccess(@PathVariable int id) {
        try {
            userAccessService.deleteUserAccess(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
