package repositories;

import domain.Department;
import domain.Employee;
import services.FileService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentRepository implements Repository<String, Department> {
    //private final Map<String, Department> storage = new HashMap<>();
    private FileService fileService;
    private File dataFile;

    public DepartmentRepository(FileService fileService) {
        this.fileService = fileService;
        this.dataFile = new File("departments.txt");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Department findById(String id) {
        String[] row = fileService.getRowById(dataFile, id);
        Department department = new Department(row[0], row[1], Float.parseFloat(row[2]));
        if (row.length > 3) {
            //TODO fill employees to Department
        }
        return department;
    }

    @Override
    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();
        List<String[]> rows = fileService.getRows(dataFile);
        for (String[] row : rows) {
            Department department = new Department(row[0], row[1], Float.parseFloat(row[2]));
            if (row.length > 3) {
                //TODO fill employees to Department
            }
            departments.add(department);
        }
        return departments;
    }

    @Override
    public void deleteById(String id) {
        fileService.deleteRowById(dataFile, id);
    }

    @Override
    public void save(Department department) {
        String departmentID = department.getDepartmentID();
        String[] row = fileService.getRowById(dataFile, departmentID);
        if (row != null && row.length != 0) {
            List<String> cols = new ArrayList<>();
            cols.add(departmentID);
            cols.add(department.getDepartmentName());
            cols.add(department.getYearlyBudget() + "");

            for (Employee employee : department.getEmployees()) {
                //TODO add employee info
                cols.add(employee.getEmployeeID());
            }


            fileService.replaceRowById(dataFile, departmentID, cols.toArray(cols.toArray(new String[0])));
        } else {
            List<String> cols = new ArrayList<>();
            cols.add(departmentID);
            cols.add(department.getDepartmentName());
            cols.add(department.getYearlyBudget() + "");

            for (Employee employee : department.getEmployees()) {
                //TODO add employee info
                cols.add(employee.getEmployeeID());
            }

            fileService.saveRow(dataFile, cols.toArray(cols.toArray(new String[0])));
        }


    }
}
