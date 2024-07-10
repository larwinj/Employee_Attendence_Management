package com.emp_attendence.employee_attendence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp_attendence.employee_attendence.model.UserAccess;
import com.emp_attendence.employee_attendence.repository.UserAccessRepository;
import java.util.List;
import java.util.Optional;

@Service
public class UserAccessService {

    @Autowired
    private UserAccessRepository userAccessRepository;

    public UserAccess saveUserAccess(UserAccess userAccess) {
        return userAccessRepository.save(userAccess);
    }

    public List<UserAccess> getAllUserAccessRecords() {
        return userAccessRepository.findAll();
    }

    public Optional<UserAccess> getUserAccessById(int accessID) {
        return userAccessRepository.findById(accessID);
    }

    //getting user access records by emp id
    public List<UserAccess> getUserAccessRecordsByEmployeeId(int employeeID) {
        return userAccessRepository.findByEmployee_EmployeeID(employeeID);
    }

    //getting user access records by role ID
    public List<UserAccess> getUserAccessRecordsByRoleId(int roleID) {
        return userAccessRepository.findByRole_RoleID(roleID);
    }

    public UserAccess updateUserAccess(int accessID, UserAccess userAccessDetails) {
        Optional<UserAccess> userAccessOptional = userAccessRepository.findById(accessID);

        if (userAccessOptional.isPresent()) {
            UserAccess userAccess = userAccessOptional.get();
            userAccess.setEmployee(userAccessDetails.getEmployee());
            userAccess.setRole(userAccessDetails.getRole());
            userAccess.setAccessGranted(userAccessDetails.getAccessGranted());
            return userAccessRepository.save(userAccess);
        } else {
            throw new RuntimeException("User access record not found with ID: " + accessID);
        }
    }

    public void deleteUserAccess(int accessID) {
        if (userAccessRepository.existsById(accessID)) {
            userAccessRepository.deleteById(accessID);
        } else {
            throw new RuntimeException("User access record not found with ID: " + accessID);
        }
    }
}
