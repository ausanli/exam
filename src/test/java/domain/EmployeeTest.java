package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeTest {
    @Test
    void testProps() {
        Employee employee = new Employee("10001", "John", "Doe", 120000);
        assertEquals("10001", employee.getEmployeeID());
        assertEquals("John", employee.getFirstName());
        assertEquals("Doe", employee.getLastName());
        assertEquals(120000, employee.getYearlySalary());
        employee.setYearlySalary(130000);
        assertEquals(130000, employee.getYearlySalary());
    }

    @Test
    void testHasCode() {
        Employee employee1 = new Employee("10001", "John", "Doe", 120000);
        Employee employee2 = new Employee("10001", "John", "Doe", 120000);
        assertEquals(employee1.hashCode(), employee2.hashCode());
    }

    @Test
    void testEquals() {
        Employee employee1 = new Employee("10001", "John", "Doe", 120000);
        Employee employee2 = new Employee("10001", "John", "Doe", 120000);
        assertEquals(employee1, employee2);
    }
}
