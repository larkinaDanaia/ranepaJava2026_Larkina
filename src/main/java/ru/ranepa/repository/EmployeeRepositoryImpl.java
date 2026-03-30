package ru.ranepa.repository;

import ru.ranepa.model.Employee;

import java.util.*;

public class EmployeeRepositoryImpl implements EmployeeRepository {
    // HashMap - это как таблица
    private Map<Long, Employee> employees = new HashMap<>();
    private Long nextId = 1L;

    // сохраняет сотрудника и присваивает ему id
    @Override
    public String save(Employee employee) {
        employee.setId(nextId);
        employees.put(nextId, employee);
        String message = "Employee " + nextId + " was saved successful";
        nextId++;
        return message;
    }

    // поиск сотрудника по айди
    @Override
    public Optional<Employee> findById(Long id) {
        return Optional.ofNullable(employees.get(id));
    }

    @Override
    public Iterable<Employee> findAll() {
        // Возвращаем список всех значений из карты
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