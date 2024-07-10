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
@Table(name = "OvertimeRecords")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OvertimeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int overtimeID;

    @ManyToOne
    @JoinColumn(name = "EmployeeID", nullable = false)
    private Employee employee;

    @Column(name = "OvertimeDate", nullable = false)
    private LocalDate overtimeDate;

    @Column(name = "OvertimeHours", nullable = false)
    private double overtimeHours;

    @Column(name = "Reason", columnDefinition = "TEXT")
    private String reason;
}

