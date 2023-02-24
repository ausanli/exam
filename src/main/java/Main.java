import domain.Department;
import domain.Employee;
import repositories.DepartmentRepository;
import repositories.EmployeeRepository;
import services.*;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;

    public Main(DepartmentService departmentService, EmployeeService employeeService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    public static void main(String[] args) throws IOException {
        DepartmentRepository departmentRepository = new DepartmentRepository();
        EmployeeRepository employeeRepository = new EmployeeRepository();
        String timestamp = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Europe/Sofia"))
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        String fileName = String.format("EmployeeSystem_OUT_%s.txt", timestamp);
        LoggerServiceImpl loggerService = new LoggerServiceImpl(fileName);
        Main main = new Main(new DepartmentServiceImpl(departmentRepository, loggerService), new EmployeeServiceImpl(departmentRepository, employeeRepository, loggerService));
        main.processScannerCommand(new Scanner(System.in));
        loggerService.close();
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
                departmentService.createDepartment(department);
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
                departmentService.showDepartmentById(departmentID);
            } else if ("UpdateDepartment".equals(command)) {
                String departmentID = tokens[1];
                String name = tokens[2];
                float budget = Float.parseFloat(tokens[3]);
                departmentService.updateDepartmentById(departmentID, name, budget);
            } else if ("End".equals(command)) {
                break;
            } else {
                throw new IllegalArgumentException("Unsupported command: " + command);
            }
        }
    }
}