package com.emp_attendence.employee_attendence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "UserPermissions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int permissionID;

    @ManyToOne
    @JoinColumn(name = "RoleID", nullable = false)
    private UserRole role;

    @Column(name = "PermissionName", nullable = false, length = 50)
    private String permissionName;
}
