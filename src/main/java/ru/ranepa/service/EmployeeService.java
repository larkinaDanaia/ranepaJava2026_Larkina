package ru.ranepa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ranepa.dto.EmployeeResponseDto;
import ru.ranepa.dto.EmployeeStatsDto;
import ru.ranepa.exception.EmployeeNotFoundException;
import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeResponseDto createEmployee(Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);
        return convertToDto(savedEmployee);
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponseDto> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EmployeeResponseDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        return convertToDto(employee);
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }
        employeeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponseDto> getEmployeesByPosition(String position) {
        return employeeRepository.findByPosition(position)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponseDto> getEmployeesByMinSalary(BigDecimal minSalary) {
        return employeeRepository.findBySalaryGreaterThanEqual(minSalary)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EmployeeStatsDto getStatistics() {
        long totalEmployees = employeeRepository.count();

        BigDecimal averageSalary = BigDecimal.ZERO;
        if (totalEmployees > 0) {
            Double avg = employeeRepository.getAverageSalary();
            if (avg != null) {
                averageSalary = BigDecimal.valueOf(avg).setScale(2, RoundingMode.HALF_UP);
            }
        }

        EmployeeResponseDto topEarner = null;
        List<Employee> topEmployees = employeeRepository.findAllOrderBySalaryDesc();
        if (!topEmployees.isEmpty()) {
            topEarner = convertToDto(topEmployees.get(0));
        }

        return new EmployeeStatsDto(averageSalary, topEarner, totalEmployees);
    }

    private EmployeeResponseDto convertToDto(Employee employee) {
        return new EmployeeResponseDto(
                employee.getId(),
                employee.getName(),
                employee.getPosition(),
                employee.getSalary(),
                employee.getHireDate(),
                employee.getCreatedAt()
        );
    }
}