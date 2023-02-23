package repositories;

import domain.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeRepositoryTest {
    @Test
    void testCrudOperations(){
        EmployeeRepository employeeRepository = new EmployeeRepository();
        employeeRepository.save(new Employee("10001", "John", "Doe", 120000));
        employeeRepository.save(new Employee("10002", "Jessica", "Dozer", 120000));
        assertEquals(2, employeeRepository.findAll().size());
        assertEquals(new Employee("10002", "Jessica", "Dozer", 120000), employeeRepository.findById("10002"));
        employeeRepository.deleteById("10002");
        assertEquals(1, employeeRepository.findAll().size());
    }
}
