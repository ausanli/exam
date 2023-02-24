package repositories;

import domain.Department;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentRepository implements Repository<String, Department> {
    private final Map<String, Department> storage = new HashMap<>();

    @Override
    public Department findById(String id) {
        return storage.get(id);
    }

    @Override
    public List<Department> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteById(String id) {
        storage.remove(id);
    }

    @Override
    public void save(Department department) {
        String departmentID = department.getDepartmentID();
        storage.put(departmentID, department);
    }
}
