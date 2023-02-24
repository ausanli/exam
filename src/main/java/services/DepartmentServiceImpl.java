package services;

import domain.Department;
import domain.Employee;
import repositories.DepartmentRepository;

import java.util.stream.Collectors;

public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public void createDepartment(Department department) {
        String departmentID = department.getDepartmentID();
        if (departmentRepository.findById(departmentID) != null) {
            System.out.printf("Department with %s already exists!%n", departmentID);
            return;
        }
        departmentRepository.save(department);
    }

    @Override
    public void showDepartmentById(String departmentID) {
        Department department = departmentRepository.findById(departmentID);
        if (department == null) {
            System.out.printf("Department with ID %s does not exist!%n", departmentID);
            return;
        }

        double sumOfAllSalariesInDepartment = department.getEmployees().stream().mapToDouble(Employee::getYearlySalary).sum();
        double leftOverBudget = department.getYearlyBudget() - sumOfAllSalariesInDepartment;
        String employesString = department.getEmployees().stream().map(Employee::getEmployeeID).collect(Collectors.joining(", "));

        System.out.printf("Department: %s%n" +
                        "ID: %s%n" +
                        "Budget: %.2f$/year.%n" +
                        "Not allocated: %.2f%n" +
                        "Employees: %s%n", department.getDepartmentName(),
                department.getDepartmentID(), department.getYearlyBudget(), leftOverBudget, employesString);
    }

    @Override
    public void updateDepartmentById(String departmentID, String name, float budget) {
        Department department = departmentRepository.findById(departmentID);
        if (department == null) {
            System.out.printf("No department with ID %s exists!%n", departmentID);
            return;
        }
        departmentRepository.deleteById(departmentID);
        Department newDepartment = new Department(departmentID, name, budget);
        departmentRepository.save(newDepartment);
    }
}