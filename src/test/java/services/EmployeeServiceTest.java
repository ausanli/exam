package services;

import domain.Department;
import domain.Employee;
import org.junit.jupiter.api.Test;
import repositories.DepartmentRepository;
import repositories.EmployeeRepository;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {
    @Test
    void testHireEmployeeWhenExists() {
        DepartmentRepository departmentRepository = mock(DepartmentRepository.class);
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        LoggerService loggerService = System.out::print;
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl(departmentRepository, employeeRepository, loggerService);
        Employee employee = new Employee("10001", "John", "Doe", 120000);

        when(employeeRepository.findById(anyString())).thenReturn(employee);
        employeeService.hireEmployee(employee);
        assertEquals("Employee with 10001 already exists!"+System.lineSeparator(), outContent.toString());
    }

    @Test
    void testHireEmployeeWhenDoesntExists() {
        DepartmentRepository departmentRepository = mock(DepartmentRepository.class);
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        LoggerService loggerService = System.out::print;
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl(departmentRepository, employeeRepository, loggerService);
        Employee employee = new Employee("10001", "John", "Doe", 120000);

        when(employeeRepository.findById(anyString())).thenReturn(null);
        employeeService.hireEmployee(employee);
        verify(employeeRepository).save(eq(employee));
    }

    @Test
    void testAssignDepartmentByIdsWhenNoEmployee() {
        DepartmentRepository departmentRepository = mock(DepartmentRepository.class);
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        LoggerService loggerService = System.out::print;
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl(departmentRepository, employeeRepository, loggerService);
        employeeService.assignDepartmentByIds("101", "1001");
        when(employeeRepository.findById(anyString())).thenReturn(null);
        assertEquals("Employee with ID 101 doesn't exist!"+System.lineSeparator(), outContent.toString());
    }

    @Test
    void testAssignDepartmentByIdsWhenNoDepartment() {
        DepartmentRepository departmentRepository = mock(DepartmentRepository.class);
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        LoggerService loggerService = System.out::print;
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl(departmentRepository, employeeRepository, loggerService);
        Employee employee = new Employee("101", "John", "Doe", 120000);
        when(employeeRepository.findById(anyString())).thenReturn(employee);
        when(departmentRepository.findById(anyString())).thenReturn(null);
        employeeService.assignDepartmentByIds("101", "1001");
        assertEquals("Department with ID 1001 does not exist!" + System.lineSeparator(), outContent.toString());
    }

    @Test
    void testAssignDepartmentByIds() {
        DepartmentRepository departmentRepository = mock(DepartmentRepository.class);
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        LoggerService loggerService = System.out::print;
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl(departmentRepository, employeeRepository, loggerService);
        Employee employee = new Employee("101", "John", "Doe", 120000);
        when(employeeRepository.findById(anyString())).thenReturn(employee);

        Department department = new Department("1001", "Technology", 1000000);
        when(departmentRepository.findById(anyString())).thenReturn(department);
        employeeService.assignDepartmentByIds("101", "1001");

        assertTrue(department.getEmployees().contains(employee));
        assertEquals("", outContent.toString());
    }

    @Test
    void testAssignDepartmentByIdsNoEnoughBudget() {
        DepartmentRepository departmentRepository = mock(DepartmentRepository.class);
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        LoggerService loggerService = System.out::print;
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl(departmentRepository, employeeRepository, loggerService);
        Employee employee = new Employee("101", "John", "Doe", 100);
        when(employeeRepository.findById(anyString())).thenReturn(employee);

        Department department = new Department("1001", "Technology", 50);
        when(departmentRepository.findById(anyString())).thenReturn(department);
        employeeService.assignDepartmentByIds("101", "1001");

        assertFalse(department.getEmployees().contains(employee));
        assertEquals("Unable to add employee 101 to department 1001 as there is not enough budget!" + System.lineSeparator(), outContent.toString());
    }


    @Test
    void testPromoteEmployeeWhenIsNegative() {
        DepartmentRepository departmentRepository = mock(DepartmentRepository.class);
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        LoggerService loggerService = System.out::print;
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl(departmentRepository, employeeRepository, loggerService);
        employeeService.promoteEmployee("101", -5);

        assertEquals("The promotion percentage needs to be a positive floating number!" + System.lineSeparator(), outContent.toString());
    }

    @Test
    void testPromoteEmployeeWhenIsPositiveAndWrongEmployee() {
        DepartmentRepository departmentRepository = mock(DepartmentRepository.class);
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        when(employeeRepository.findById(anyString())).thenReturn(null);
        LoggerService loggerService = System.out::print;
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl(departmentRepository, employeeRepository, loggerService);
        employeeService.promoteEmployee("101", 20);

        assertEquals("Employee with ID 101 doesn't exist!" + System.lineSeparator(), outContent.toString());
    }

    @Test
    void testPromoteEmployeeWhenIsPositiveAndNoDepartments() {
        DepartmentRepository departmentRepository = mock(DepartmentRepository.class);
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);

        Employee employee = new Employee("101", "John", "Doe", 100);
        when(employeeRepository.findById(anyString())).thenReturn(employee);
        LoggerService loggerService = System.out::print;
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl(departmentRepository, employeeRepository, loggerService);
        employeeService.promoteEmployee("101", 50);

        assertEquals(150, employee.getYearlySalary());
    }

    @Test
    void testPromoteEmployeeWhenIsPositiveAndNoDepartmentDontHaveBudget() {
        DepartmentRepository departmentRepository = mock(DepartmentRepository.class);
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Employee employee = new Employee("101", "John", "Doe", 100);
        when(employeeRepository.findById(anyString())).thenReturn(employee);

        Department department = new Department("1001", "Technology", 99);
        department.assignEmployee(employee);

        when(departmentRepository.findAll()).thenReturn(List.of(department));
        LoggerService loggerService = System.out::print;
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl(departmentRepository, employeeRepository, loggerService);
        employeeService.promoteEmployee("101", 50);

        assertEquals(100, employee.getYearlySalary());
        assertEquals("Department 1001's budget does not allow for such a high promotion!" + System.lineSeparator(), outContent.toString());

    }

    @Test
    void testShowEmployeeByIdWhenNoEmployee() {
        DepartmentRepository departmentRepository = mock(DepartmentRepository.class);
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        LoggerService loggerService = System.out::print;
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl(departmentRepository, employeeRepository, loggerService);
        employeeService.showEmployeeById("101");
        assertEquals("Employee with ID 101 doesn't exist!" + System.lineSeparator(), outContent.toString());
    }

    @Test
    void testShowEmployeeById() {
        DepartmentRepository departmentRepository = mock(DepartmentRepository.class);
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Employee employee = new Employee("101", "John", "Doe", 100);
        when(employeeRepository.findById(anyString())).thenReturn(employee);
        LoggerService loggerService = System.out::print;
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl(departmentRepository, employeeRepository, loggerService);
        employeeService.showEmployeeById("101");
        assertEquals("Employee ID: 101, name: John Doe, Department: N/A, Salary: 100.00" + System.lineSeparator(), outContent.toString());
    }
}
