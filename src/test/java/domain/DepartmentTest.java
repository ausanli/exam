package domain;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DepartmentTest {
    @Test
    void testProps() {
        Department department = new Department("1001", "Technology", 1000000);
        assertEquals("1001", department.getDepartmentID());
        assertEquals("Technology", department.getDepartmentName());
        assertEquals(1000000, department.getYearlyBudget());
        assertEquals(Collections.emptyList(), department.getEmployees());
        department.setDepartmentName("BioTechnology");
        department.assignEmployee(new Employee("10001", "John", "Doe", 120000));
        assertEquals(1, department.getEmployees().size());
    }

    @Test
    void testHasCode() {
        Department department1 = new Department("1001", "Technology", 1000000);
        Department department2 = new Department("1001", "Technology", 1000000);
        assertEquals(department1.hashCode(), department2.hashCode());
    }

    @Test
    void testEquals() {
        Department department1 = new Department("1001", "Technology", 1000000);
        Department department2 = new Department("1001", "Technology", 1000000);
        assertEquals(department1, department2);
    }
}
