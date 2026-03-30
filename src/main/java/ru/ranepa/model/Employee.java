package ru.ranepa.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Employee {
    private Long id;
    private String name;
    private String position;
    private BigDecimal salary;
    private LocalDate hireDate;

    // создание нового сотрудника
    public Employee(String name, String position, double salary, LocalDate hireDate) {
        this.name = name;
        this.position = position;
        this.salary = BigDecimal.valueOf(salary);
        this.hireDate = hireDate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // чтобы красиво выводился сотрудник в консоль
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", hireDate=" + hireDate +
                '}';
    }
}