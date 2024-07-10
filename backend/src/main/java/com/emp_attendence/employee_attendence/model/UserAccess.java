package com.emp_attendence.employee_attendence.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "UserAccess")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accessID;

    @ManyToOne
    @JoinColumn(name = "EmployeeID", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "RoleID", nullable = false)
    private UserRole role;

    @Column(name = "AccessGranted", nullable = false)
    private LocalDate accessGranted;
}

