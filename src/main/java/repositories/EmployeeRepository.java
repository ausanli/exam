package repositories;

import domain.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeRepository implements Repository<String, Employee> {
    private final Map<String, Employee> storage = new HashMap<>();

    @Override
    public Employee findById(String id) {
        return storage.get(id);
    }

    @Override
    public List<Employee> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteById(String id) {
        storage.remove(id);
    }

    @Override
    public void save(Employee employee) {
        String employeeID = employee.getEmployeeID();
        storage.put(employeeID, employee);
    }
}
