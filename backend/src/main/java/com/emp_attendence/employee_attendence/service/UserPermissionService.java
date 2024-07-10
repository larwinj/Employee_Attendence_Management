package com.emp_attendence.employee_attendence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp_attendence.employee_attendence.model.UserPermission;
import com.emp_attendence.employee_attendence.repository.UserPermissionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserPermissionService {

    @Autowired
    private UserPermissionRepository userPermissionRepository;

    public UserPermission saveUserPermission(UserPermission userPermission) {
        return userPermissionRepository.save(userPermission);
    }

    public List<UserPermission> getAllUserPermissions() {
        return userPermissionRepository.findAll();
    }

    public Optional<UserPermission> getUserPermissionById(int permissionID) {
        return userPermissionRepository.findById(permissionID);
    }

    public List<UserPermission> getUserPermissionsByRoleId(int roleID) {
        return userPermissionRepository.findByRole_RoleID(roleID);
    }

    public UserPermission updateUserPermission(int permissionID, UserPermission userPermissionDetails) {
        Optional<UserPermission> userPermissionOptional = userPermissionRepository.findById(permissionID);

        if (userPermissionOptional.isPresent()) {
            UserPermission userPermission = userPermissionOptional.get();
            userPermission.setPermissionName(userPermissionDetails.getPermissionName());
            userPermission.setRole(userPermissionDetails.getRole());
            return userPermissionRepository.save(userPermission);
        } else {
            throw new RuntimeException("User permission not found with ID: " + permissionID);
        }
    }

    public void deleteUserPermission(int permissionID) {
        if (userPermissionRepository.existsById(permissionID)) {
            userPermissionRepository.deleteById(permissionID);
        } else {
            throw new RuntimeException("User permission not found with ID: " + permissionID);
        }
    }
}
