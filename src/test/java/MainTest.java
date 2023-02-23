

import domain.Department;
import domain.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.DepartmentServiceImpl;
import services.EmployeeServiceImpl;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MainTest {
    EmployeeServiceImpl employeeService;
    DepartmentServiceImpl departmentService;
    Main main;

    @BeforeEach
    void before() {
        employeeService = mock(EmployeeServiceImpl.class);
        departmentService = mock(DepartmentServiceImpl.class);
        main = new Main(departmentService, employeeService);
    }

    @Test
    void testWhenEndCommandPassed() {
        String input = "End\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        main.processScannerCommand(new Scanner(System.in));
    }

    @Test
    void testWhenCreateDepartmentCommandPassed() {
        String input = "CreateDepartment 1001 Technology 1000000\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        main.processScannerCommand(new Scanner(System.in));
        verify(departmentService).createDepartament(eq(new Department("1001", "Technology", 1000000)));
    }

    @Test
    void testWhenHireEmployeeCommandPassed() {
        String input = "HireEmployee 10001 John Doe 120000\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        main.processScannerCommand(new Scanner(System.in));
        verify(employeeService).hireEmployee(eq(new Employee("10001", "John", "Doe", 120000)));
    }

    @Test
    void testWhenAssignDepartmentCommandPassed() {
        String input = "AssignDepartment 10001 1001\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        main.processScannerCommand(new Scanner(System.in));
        verify(employeeService).assignDepartmentByIds(eq("10001"), eq("1001"));
    }
    @Test
    void testWhenPromoteEmployeeCommandPassed() {
        String input = "PromoteEmployee 10002 20\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        main.processScannerCommand(new Scanner(System.in));
        verify(employeeService).promoteEmployee(eq("10002"), eq(20.0f));
    }
    @Test
    void testWhenShowEmployeeCommandPassed() {
        String input = "ShowEmployee 100\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        main.processScannerCommand(new Scanner(System.in));
        verify(employeeService).showEmployeeById(eq("100"));
    }
    @Test
    void testWhenShowDepartmentCommandPassed() {
        String input = "ShowDepartment 1001\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        main.processScannerCommand(new Scanner(System.in));
        verify(departmentService).showDepartamentById(eq("1001"));
    }

    @Test
    void testWrongCommandPassed() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            String input = "TestWrongCommand\n";
            System.setIn(new ByteArrayInputStream(input.getBytes()));
            main.processScannerCommand(new Scanner(System.in));
        });

    }
}
