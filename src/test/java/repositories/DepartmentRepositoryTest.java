package repositories;

import domain.Department;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepartmentRepositoryTest {
    @Test
    void testCrudOperations() {
        DepartmentRepository departmentRepository = new DepartmentRepository();
        departmentRepository.save(new Department("1001", "Technology", 1000000));
        departmentRepository.save(new Department("101", "Accounting", 1000000));
        assertEquals(2, departmentRepository.findAll().size());
        assertEquals(new Department("101", "Accounting", 1000000), departmentRepository.findById("101"));
        departmentRepository.deleteById("1001");
        assertEquals(1, departmentRepository.findAll().size());
    }
}
