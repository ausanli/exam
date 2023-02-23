package services;

import domain.Department;

public interface DepartmentService {
    void createDepartment(Department department);

    void showDepartmentById(String departmentID);
}
