package com.emp_attendence.employee_attendence.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.emp_attendence.employee_attendence.model.UserRole;
import com.emp_attendence.employee_attendence.service.UserRoleService;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/userroles")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @PostMapping
    public UserRole createUserRole(@RequestBody UserRole userRole) {
        return userRoleService.saveUserRole(userRole);
    }

    @PostMapping("/sequence")
    public String createUserRoleseq(@RequestBody List<UserRole> userRole) {
        for(UserRole i:userRole){
            userRoleService.saveUserRole(i);
        }
        return "Data stored successfully";
    }

    @GetMapping
    public List<UserRole> getAllUserRoles() {
        return userRoleService.getAllUserRoles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRole> getUserRoleById(@PathVariable int id) {
        Optional<UserRole> userRole = userRoleService.getUserRoleById(id);

        return userRole.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserRole> updateUserRole(
            @PathVariable int id, @RequestBody UserRole userRoleDetails) {

        try {
            UserRole updatedUserRole = userRoleService.updateUserRole(id, userRoleDetails);
            return ResponseEntity.ok(updatedUserRole);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserRole(@PathVariable int id) {
        try {
            userRoleService.deleteUserRole(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
