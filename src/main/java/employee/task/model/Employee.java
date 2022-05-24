package employee.task.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Employee {

    private String firstName;
    private String lastName;
    private Position position;
    private double salary;
    private boolean isEmployed;

    public static List<Employee> employeeExtension = new ArrayList<>();

    public Employee(String firstName, String lastName, Position position, double salary, boolean isEmployed) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.salary = salary;
        this.isEmployed = isEmployed;
        employeeExtension.add(this);
    }
}
