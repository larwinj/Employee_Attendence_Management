package com.emp_attendence.employee_attendence.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "AttendanceRecords")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int attendanceID;

    @ManyToOne
    @JoinColumn(name = "EmployeeID", nullable = false)
    private Employee employee;

    @Column(name = "AttendanceDate", nullable = false)
    private LocalDate attendanceDate;

    @Column(name = "CheckInTime", nullable = false)
    private LocalTime checkInTime;

    @Column(name = "CheckOutTime")
    private LocalTime checkOutTime = LocalTime.of(17, 0);
    // private LocalTime checkOutTime;

    @Column(name = "AttendanceType", nullable = false, length = 10)
    private String attendanceType;
}

