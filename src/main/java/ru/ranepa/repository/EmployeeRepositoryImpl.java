package ru.ranepa.repository;

import ru.ranepa.model.Employee;

import java.util.*;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final Map<Long, Employee> employees = new HashMap<>();
    private Long nextId = 1L;


    @Override
    public String save(Employee employee) {
        employee.setId(nextId);
        employees.put(nextId, employee);
        String message = "Employee " + nextId + " was saved successful";
        nextId++;
        return message;
    }


    @Override
    public Optional<Employee> findById(Long id) {
        return Optional.ofNullable(employees.get(id));
    }

    @Override
    public Iterable<Employee> findAll() {
        return new ArrayList<>(employees.values());
    }

    @Override
    public String delete(Long id) {
        Employee removed = employees.remove(id);
        if (removed != null) {
            return "Employee " + id + " was deleted";
        } else {
            return "Employee " + id + " not found";
        }
    }

    public void clear() {
        employees.clear();
        nextId = 1L;
    }
}