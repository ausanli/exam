package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Department {
    private final String departmentID;
    private String departmentName;
    private final float yearlyBudget;

    private final List<Employee> employees = new ArrayList<>();

    public Department(String departmentID, String departmentName, float yearlyBudget) {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        this.yearlyBudget = yearlyBudget;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public float getYearlyBudget() {
        return yearlyBudget;
    }



    public List<Employee> getEmployees() {
        return employees;
    }

    public void assignEmployee(Employee employee){
        employees.add(employee);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Float.compare(that.yearlyBudget, yearlyBudget) == 0 && Objects.equals(departmentID, that.departmentID) && Objects.equals(departmentName, that.departmentName) && Objects.equals(employees, that.employees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentID, departmentName, yearlyBudget, employees);
    }
}
