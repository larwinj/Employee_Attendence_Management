package com.emp_attendence.employee_attendence.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.emp_attendence.employee_attendence.model.UserPermission;
import com.emp_attendence.employee_attendence.service.UserPermissionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/userpermissions")
public class UserPermissionController {

    @Autowired
    private UserPermissionService userPermissionService;

    @PostMapping
    public UserPermission createUserPermission(@RequestBody UserPermission userPermission) {
        return userPermissionService.saveUserPermission(userPermission);
    
    }
    @PostMapping("/sequence")
    public String createUserPermissionseq(@RequestBody List<UserPermission> userPermission) {
        for(UserPermission i:userPermission){
            userPermissionService.saveUserPermission(i);
        }
        return "Data Stored Successfully";
    }

    @GetMapping
    public List<UserPermission> getAllUserPermissions() {
        return userPermissionService.getAllUserPermissions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserPermission> getUserPermissionById(@PathVariable int id) {
        Optional<UserPermission> userPermission = userPermissionService.getUserPermissionById(id);

        return userPermission.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/role/{roleId}")
    public List<UserPermission> getUserPermissionsByRoleId(@PathVariable int roleId) {
        return userPermissionService.getUserPermissionsByRoleId(roleId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserPermission> updateUserPermission(
            @PathVariable int id, @RequestBody UserPermission userPermissionDetails) {

        try {
            UserPermission updatedUserPermission = userPermissionService.updateUserPermission(id, userPermissionDetails);
            return ResponseEntity.ok(updatedUserPermission);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserPermission(@PathVariable int id) {
        try {
            userPermissionService.deleteUserPermission(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
