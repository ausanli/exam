package services;

import domain.Employee;

public interface EmployeeService {
    void hireEmployee(Employee employee);

    void assignDepartmentByIds(String employeeID, String departmentID);

    void promoteEmployee(String employeeID, float promotionPercentage);

    void showEmployeeById(String employeeID);
}
