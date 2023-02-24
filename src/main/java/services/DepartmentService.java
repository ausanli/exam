package services;

import domain.Department;

public interface DepartmentService {
    void createDepartment(Department department);

    void showDepartmentById(String departmentID);

    void updateDepartmentById(String departmentID, String name, float budget);
}
