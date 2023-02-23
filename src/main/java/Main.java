import domain.Department;
import domain.Employee;
import repositories.DepartmentRepository;
import repositories.EmployeeRepository;
import services.DepartmentService;
import services.DepartmentServiceImpl;
import services.EmployeeService;
import services.EmployeeServiceImpl;

import java.util.Scanner;

public class Main {
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;

    public Main(DepartmentService departmentService, EmployeeService employeeService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    public static void main(String[] args) {
        DepartmentRepository departmentRepository = new DepartmentRepository();
        EmployeeRepository employeeRepository = new EmployeeRepository();
        Main main = new Main(new DepartmentServiceImpl(departmentRepository), new EmployeeServiceImpl(departmentRepository, employeeRepository));
        main.processScannerCommand(new Scanner(System.in));
    }

    protected void processScannerCommand(Scanner scanner) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] tokens = line.split(" ");
            String command = tokens[0];
            if ("CreateDepartment".equals(command)) {
                String departmentID = tokens[1];
                String departmentName = tokens[2];
                float yearlyBudget = Float.parseFloat(tokens[3]);
                Department department = new Department(departmentID, departmentName, yearlyBudget);
                departmentService.createDepartament(department);
            } else if ("HireEmployee".equals(command)) {
                String employeeID = tokens[1];
                String firstName = tokens[2];
                String lastName = tokens[3];
                float yearlySalary = Float.parseFloat(tokens[4]);
                Employee employee = new Employee(employeeID, firstName, lastName, yearlySalary);
                employeeService.hireEmployee(employee);
            } else if ("AssignDepartment".equals(command)) {
                String employeeID = tokens[1];
                String departmentID = tokens[2];
                employeeService.assignDepartmentByIds(employeeID, departmentID);
            } else if ("PromoteEmployee".equals(command)) {
                String employeeID = tokens[1];
                float promotionPercentage = Float.parseFloat(tokens[2]);
                employeeService.promoteEmployee(employeeID, promotionPercentage);
            } else if ("ShowEmployee".equals(command)) {
                String employeeID = tokens[1];
                employeeService.showEmployeeById(employeeID);
            } else if ("ShowDepartment".equals(command)) {
                String departmentID = tokens[1];
                departmentService.showDepartamentById(departmentID);
            } else if ("End".equals(command)) {
                break;
            } else {
                throw new IllegalArgumentException("Unsupported command: " + command);
            }
        }
    }
}