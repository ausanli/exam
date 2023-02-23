package services;

import domain.Department;

public interface DepartmentService {
    void createDepartament(Department department);

    void showDepartamentById(String departmentID);
}
