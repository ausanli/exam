package services;

import domain.Department;
import domain.Employee;
import repositories.DepartmentRepository;
import repositories.EmployeeRepository;

import java.util.List;
import java.util.Optional;

public class EmployeeServiceImpl implements EmployeeService {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void hireEmployee(Employee employee) {
        String employeeID = employee.getEmployeeID();
        if (employeeRepository.findById(employeeID) != null) {
            System.out.printf("Employee with %s already exists!%n", employeeID);
            return;
        }
        employeeRepository.save(employee);
    }

    @Override
    public void assignDepartmentByIds(String employeeID, String departmentID) {
        Employee employee = employeeRepository.findById(employeeID);
        if (employee == null) {
            System.out.printf("Employee with ID %s doesn't exist!%n", employeeID);
            return;
        }

        Department department = departmentRepository.findById(departmentID);
        if (department == null) {
            System.out.printf("Department with ID %s does not exist!%n", departmentID);
            return;
        }
        if (employee.getYearlySalary() >= department.getYearlyBudget()) {
            System.out.printf("Unable to add employee %s to department %s as there is not enough budget!%n", employeeID, departmentID);
            return;
        }
        department.assignEmployee(employee);
    }

    @Override
    public void promoteEmployee(String employeeID, float promotionPercentage) {
        if (promotionPercentage <= 0) {
            System.out.printf("The promotion percentage needs to be a positive floating number!%n");
            return;
        }

        Employee employee = employeeRepository.findById(employeeID);
        if (employee == null) {
            System.out.printf("Employee with ID %s doesn't exist!%n", employeeID);
            return;
        }
        float newEmployeeSalary = employee.getYearlySalary() * ((100 + promotionPercentage) / 100);
        List<Department> departments = departmentRepository.findAll();

        if (!departments.isEmpty()) {
            Optional<Department> departmentDoesntAllowPromote = departments.stream().filter(d -> d.getEmployees().contains(employee)).filter(d -> d.getYearlyBudget() < newEmployeeSalary).findFirst();
            if (departmentDoesntAllowPromote.isPresent()) {
                System.out.printf("Department %s's budget does not allow for such a high promotion!%n", departmentDoesntAllowPromote.get().getDepartmentID());
                return;
            }
        }
        employee.setYearlySalary(newEmployeeSalary);
    }

    @Override
    public void showEmployeeById(String employeeID) {
        Employee employee = employeeRepository.findById(employeeID);
        if (employee == null) {
            System.out.printf("Employee with ID %s doesn't exist!%n", employeeID);
            return;
        }
        final String employeeDepartmentID = departmentRepository.findAll().stream().filter(department -> department.getEmployees().contains(employee)).map(Department::getDepartmentID).findFirst().orElse("N/A");
        System.out.printf("Employee ID: %s, name: %s %s, Department: %s, Salary: %.2f%n", employee.getEmployeeID(), employee.getFirstName(), employee.getLastName(), employeeDepartmentID, employee.getYearlySalary());
    }
}
