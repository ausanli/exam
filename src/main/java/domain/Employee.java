package domain;

import java.util.Objects;

public class Employee {
    private final String employeeID;
    private final String firstName;
    private final String lastName;
    private float yearlySalary;

    public Employee(String employeeID, String firstName, String lastName, float yearlySalary) {
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearlySalary = yearlySalary;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setYearlySalary(float yearlySalary) {
        this.yearlySalary = yearlySalary;
    }
    public float getYearlySalary() {
        return yearlySalary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Float.compare(employee.yearlySalary, yearlySalary) == 0 && Objects.equals(employeeID, employee.employeeID) && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeID, firstName, lastName, yearlySalary);
    }
}
