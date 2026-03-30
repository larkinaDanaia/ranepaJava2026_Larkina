package ru.ranepa;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepositoryImpl;
import ru.ranepa.service.EmployeeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class HrmApplication {
    public static void main(String[] args) {

        EmployeeRepositoryImpl repository = new EmployeeRepositoryImpl();
        EmployeeService service = new EmployeeService(repository);

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== HRM System Menu ===");

        boolean running = true;


        while (running) {
            System.out.println("\n1. Show all employees");
            System.out.println("2. Add employee");
            System.out.println("3. Delete employee by ID");
            System.out.println("4. Find employee by ID");
            System.out.println("5. Show statistics (average salary, top earner)");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        showAllEmployees(service);
                        break;
                    case "2":
                        addEmployee(service, scanner);
                        break;
                    case "3":
                        deleteEmployee(service, scanner);
                        break;
                    case "4":
                        findEmployee(service, scanner);
                        break;
                    case "5":
                        showStatistics(service);
                        break;
                    case "6":
                        System.out.println("Goodbye!");
                        running = false;  // Выходим из цикла
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please try again.");
            }
        }

        scanner.close();
    }

    private static void showAllEmployees(EmployeeService service) {
        List<Employee> employees = service.getAllEmployees();

        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            System.out.println("\n=== Employee List ===");
            for (Employee emp : employees) {
                System.out.println(emp);
            }
        }
    }

    private static void addEmployee(EmployeeService service, Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter position: ");
        String position = scanner.nextLine();


        double salary = 0;
        boolean validSalary = false;
        while (!validSalary) {
            try {
                System.out.print("Enter salary: ");
                String salaryInput = scanner.nextLine();
                salary = Double.parseDouble(salaryInput);
                validSalary = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter salary again.");
            }
        }


        LocalDate hireDate = null;
        boolean validDate = false;
        while (!validDate) {
            try {
                System.out.print("Enter hire date (YYYY-MM-DD): ");
                String dateInput = scanner.nextLine();
                hireDate = LocalDate.parse(dateInput);
                validDate = true;
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }


        String result = service.addEmployee(name, position, salary, hireDate);
        System.out.println(result);
    }

    private static void deleteEmployee(EmployeeService service, Scanner scanner) {
        System.out.print("Enter employee ID to delete: ");
        Long id = Long.parseLong(scanner.nextLine());
        String result = service.removeEmployee(id);
        System.out.println(result);
    }


    private static void findEmployee(EmployeeService service, Scanner scanner) {
        System.out.print("Enter employee ID to find: ");
        Long id = Long.parseLong(scanner.nextLine());
        Employee employee = service.findEmployeeById(id);

        if (employee != null) {
            System.out.println("Employee found: " + employee);
        } else {
            System.out.println("Employee with ID " + id + " not found.");
        }
    }

    private static void showStatistics(EmployeeService service) {
        System.out.println("\n=== Statistics ===");


        BigDecimal averageSalary = service.calculateAverageSalary();
        System.out.println("Average salary: " + averageSalary);

        Employee topEarner = service.findTopEarner();
        if (topEarner != null) {
            System.out.println("Top earner: " + topEarner.getName() +
                    " (Position: " + topEarner.getPosition() +
                    ", Salary: " + topEarner.getSalary() + ")");
        } else {
            System.out.println("No employees to analyze.");
        }
    }
}