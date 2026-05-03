package ru.ranepa.dto;

import java.math.BigDecimal;

public class EmployeeStatsDto {
    private BigDecimal averageSalary;
    private EmployeeResponseDto topEarner;
    private long totalEmployees;

    public EmployeeStatsDto() {
    }

    public EmployeeStatsDto(BigDecimal averageSalary, EmployeeResponseDto topEarner, long totalEmployees) {
        this.averageSalary = averageSalary;
        this.topEarner = topEarner;
        this.totalEmployees = totalEmployees;
    }

    // Getters and Setters
    public BigDecimal getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(BigDecimal averageSalary) {
        this.averageSalary = averageSalary;
    }

    public EmployeeResponseDto getTopEarner() {
        return topEarner;
    }

    public void setTopEarner(EmployeeResponseDto topEarner) {
        this.topEarner = topEarner;
    }

    public long getTotalEmployees() {
        return totalEmployees;
    }

    public void setTotalEmployees(long totalEmployees) {
        this.totalEmployees = totalEmployees;
    }
}