package services;

import domain.Department;
import domain.Employee;
import org.junit.jupiter.api.Test;
import repositories.DepartmentRepository;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DepartmentServiceTest {
    @Test
    void testCreateDepartmentWhenExits() {
        Department department = new Department("1001", "Technology", 1000000);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        DepartmentRepository departmentRepository = mock(DepartmentRepository.class);
        when(departmentRepository.findById(anyString())).thenReturn(department);
        LoggerService loggerService = System.out::print;
        DepartmentServiceImpl departmentService = new DepartmentServiceImpl(departmentRepository, loggerService);
        departmentService.createDepartment(department);

        assertEquals("Department with 1001 already exists!" + System.lineSeparator(), outContent.toString());
    }

    @Test
    void testCreateDepartmentWhenDoesntExit() {
        Department department = new Department("1001", "Technology", 1000000);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        DepartmentRepository departmentRepository = mock(DepartmentRepository.class);
        when(departmentRepository.findById(anyString())).thenReturn(null);
        LoggerService loggerService = System.out::print;
        DepartmentServiceImpl departmentService = new DepartmentServiceImpl(departmentRepository, loggerService);
        departmentService.createDepartment(department);

        verify(departmentRepository).save(eq(department));
    }

    @Test
    void testShowDepartmentByIdWhenDoesntExit() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        DepartmentRepository departmentRepository = mock(DepartmentRepository.class);
        when(departmentRepository.findById(anyString())).thenReturn(null);
        LoggerService loggerService = System.out::print;
        DepartmentServiceImpl departmentService = new DepartmentServiceImpl(departmentRepository, loggerService);
        departmentService.showDepartmentById("101");

        assertEquals("Department with ID 101 does not exist!" + System.lineSeparator(), outContent.toString());
    }

    @Test
    void testShowDepartmentByIdWhenExits() {
        Department department = new Department("1001", "Technology", 1000000);
        Employee employee1 = new Employee("10001", "John", "Doe", 120000);
        Employee employee2 = new Employee("10002", "Pesho", "Ivanov", 120000);
        department.assignEmployee(employee1);
        department.assignEmployee(employee2);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        DepartmentRepository departmentRepository = mock(DepartmentRepository.class);
        when(departmentRepository.findById(anyString())).thenReturn(department);
        LoggerService loggerService = System.out::print;
        DepartmentServiceImpl departmentService = new DepartmentServiceImpl(departmentRepository, loggerService);
        departmentService.showDepartmentById("1001");

        assertEquals("Department: Technology" + System.lineSeparator() +
                "ID: 1001" + System.lineSeparator() +
                "Budget: 1000000.00$/year." + System.lineSeparator() +
                "Not allocated: 760000.00" + System.lineSeparator() +
                "Employees: 10001, 10002" + System.lineSeparator(), outContent.toString());
    }

    @Test
    void testUpdateDepartmentWhenFound() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        DepartmentRepository departmentRepository = mock(DepartmentRepository.class);
        Department department = new Department("1001", "Technology", 1000000);
        when(departmentRepository.findById(eq("1001"))).thenReturn(department);
        LoggerService loggerService = System.out::print;
        DepartmentServiceImpl departmentService = new DepartmentServiceImpl(departmentRepository, loggerService);
        departmentService.updateDepartmentById("1001", "Technology", 1000000);

        verify(departmentRepository).deleteById(eq("1001"));
        verify(departmentRepository).save(eq(new Department("1001", "Technology", 1000000)));
    }

    @Test
    void testUpdateDepartmentWhenNotFound() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        DepartmentRepository departmentRepository = mock(DepartmentRepository.class);
        when(departmentRepository.findById(anyString())).thenReturn(null);
        LoggerService loggerService = System.out::print;
        DepartmentServiceImpl departmentService = new DepartmentServiceImpl(departmentRepository, loggerService);
        departmentService.updateDepartmentById("1001", "Technology", 1000000);

        assertEquals("No department with ID 1001 exists!" + System.lineSeparator(), outContent.toString());
    }
}
