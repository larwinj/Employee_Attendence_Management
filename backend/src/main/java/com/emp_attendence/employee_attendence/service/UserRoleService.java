package com.emp_attendence.employee_attendence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp_attendence.employee_attendence.model.UserRole;
import com.emp_attendence.employee_attendence.repository.UserRoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    public UserRole saveUserRole(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    public List<UserRole> getAllUserRoles() {
        return userRoleRepository.findAll();
    }

    public Optional<UserRole> getUserRoleById(int roleID) {
        return userRoleRepository.findById(roleID);
    }

    public UserRole updateUserRole(int roleID, UserRole userRoleDetails) {
        Optional<UserRole> userRoleOptional = userRoleRepository.findById(roleID);

        if (userRoleOptional.isPresent()) {
            UserRole userRole = userRoleOptional.get();
            userRole.setRoleName(userRoleDetails.getRoleName());
            return userRoleRepository.save(userRole);
        } else {
            throw new RuntimeException("User role not found with ID: " + roleID);
        }
    }

    public void deleteUserRole(int roleID) {
        if (userRoleRepository.existsById(roleID)) {
            userRoleRepository.deleteById(roleID);
        } else {
            throw new RuntimeException("User role not found with ID: " + roleID);
        }
    }
}
