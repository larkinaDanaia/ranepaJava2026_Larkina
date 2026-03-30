package ru.ranepa.service;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // добавление сотрудника
    public String addEmployee(String name, String position, double salary, java.time.LocalDate hireDate) {
        Employee employee = new Employee(name, position, salary, hireDate);
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        List<Employee> result = new ArrayList<>();
        for (Employee emp : employeeRepository.findAll()) {
            result.add(emp);
        }
        return result;
    }


    public String removeEmployee(Long id) {
        return employeeRepository.delete(id);
    }

    // удаление сотрудника
    public Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    // расчет средней зп
    public BigDecimal calculateAverageSalary() {
        List<Employee> allEmployees = getAllEmployees();

        if (allEmployees.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal sumSalary = BigDecimal.ZERO;
        for (Employee employee : allEmployees) {
            sumSalary = sumSalary.add(employee.getSalary());
        }

        int count = allEmployees.size();
        return sumSalary.divide(BigDecimal.valueOf(count), 2, java.math.RoundingMode.HALF_UP);

    }

    // поиск самого высокооплачиваемого
    public Employee findTopEarner() {
        List<Employee> allEmployees = getAllEmployees();


        if (allEmployees.isEmpty()) {
            return null;
        }

        Employee topEarner = allEmployees.getFirst();

        for (Employee employee : allEmployees) {
            if (employee.getSalary().compareTo(topEarner.getSalary()) > 0) {
                topEarner = employee;
            }
        }

        return topEarner;
    }
}