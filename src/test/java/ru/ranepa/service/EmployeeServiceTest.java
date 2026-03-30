package ru.ranepa.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepositoryImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

    private EmployeeService service;
    private EmployeeRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        repository = new EmployeeRepositoryImpl();
        service = new EmployeeService(repository);
    }


    @Test
    void shouldCalculateAverageSalary() {
        service.addEmployee("A", "Dev", 100, LocalDate.now());
        service.addEmployee("B", "QA", 200, LocalDate.now());
        service.addEmployee("C", "Manager", 300, LocalDate.now());

        BigDecimal average = service.calculateAverageSalary();

        // ✅ ИСПРАВЛЕНО: сравниваем как числа с допустимой погрешностью 0.01
        assertEquals(200.0, average.doubleValue(), 0.01);
    }

    @Test
    void shouldFindTopEarner() {
        // Given: добавляем сотрудников
        service.addEmployee("Low", "Intern", 50, LocalDate.now());
        service.addEmployee("High", "Director", 500, LocalDate.now());
        service.addEmployee("Mid", "Dev", 200, LocalDate.now());

        // When: ищем топ
        Employee top = service.findTopEarner();

        // Then: должен вернуться сотрудник с зарплатой 500
        assertNotNull(top);
        assertEquals("High", top.getName());
        // ✅ ИСПРАВЛЕНО: сравниваем как числа
        assertEquals(500.0, top.getSalary().doubleValue());
    }

    @Test
    void shouldReturnZeroWhenNoEmployees() {
        // When
        BigDecimal average = service.calculateAverageSalary();

        // Then
        assertEquals(BigDecimal.ZERO, average);
    }

    @Test
    void shouldFindEmployeeById() {
        // Given
        service.addEmployee("Ivan", "Developer", 150, LocalDate.now());

        // When
        Employee found = service.findEmployeeById(1L);

        // Then
        assertNotNull(found);
        assertEquals("Ivan", found.getName());
    }

    @Test
    void shouldDeleteEmployee() {

        service.addEmployee("ToDelete", "QA", 100, LocalDate.now());

        String result = service.removeEmployee(1L);
        Employee afterDelete = service.findEmployeeById(1L);

        assertTrue(result.contains("deleted"));
        assertNull(afterDelete);
    }
}