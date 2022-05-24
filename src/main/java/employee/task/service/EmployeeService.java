package employee.task.service;

import employee.task.constant.Constants;
import employee.task.model.Position;
import employee.task.model.Employee;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EmployeeService {

    private static final Connection conn = Constants.getConnection();

    @SneakyThrows
    public static void saveEmployeeIntoDB(Employee employee) {

        String query = "insert into employees values (null,?, ?, ?, ?, ?)";

        PreparedStatement prepStm = conn.prepareStatement(query);

        prepStm.setString(1, employee.getFirstName());
        prepStm.setString(2, employee.getLastName());
        prepStm.setString(3, employee.getPosition().name());
        prepStm.setDouble(4, employee.getSalary());
        prepStm.setInt(5, (employee.isEmployed()) ? 1 : 0);

        prepStm.execute();
    }

    @SneakyThrows
    public static void dismissEmployee(Employee employee) {

        String query = "update employees set isEmployed = 0 where name = ? and surname = ? and position = ?";

        PreparedStatement prepStm = conn.prepareStatement(query);

        prepStm.setString(1, employee.getFirstName());
        prepStm.setString(2, employee.getLastName());
        prepStm.setString(3, employee.getPosition().name());

        prepStm.execute();
    }

    @SneakyThrows
    public static void increaseSalaryOfEmployeeInDB(Employee employee, double rise) {

        String query = "update employees set salary = ? where name = ? and surname = ? and position = ?";

        PreparedStatement prepStm = conn.prepareStatement(query);

        prepStm.setDouble(1, employee.getSalary() + rise);
        prepStm.setString(2, employee.getFirstName());
        prepStm.setString(3, employee.getLastName());
        prepStm.setString(4, employee.getPosition().name());

        prepStm.execute();
    }

    @SneakyThrows
    public static void setPositionOnDataBase(Employee employee, Position position) {

        String query = "update employees set position = ? where name = ? and surname = ? and position = ?";

        PreparedStatement prepStm = conn.prepareStatement(query);

        prepStm.setString(1, position.name());
        prepStm.setString(2, employee.getFirstName());
        prepStm.setString(3, employee.getLastName());
        prepStm.setString(4, employee.getPosition().name());

        prepStm.execute();
    }

    @SneakyThrows
    public static void selectEmployee(String name, String surname) {

        String query = "select * from employees where name = ? and surname = ? or name = ? or surname = ?";

        PreparedStatement prepStm = conn.prepareStatement(query);

        prepStm.setString(1, name);
        prepStm.setString(2, surname);
        prepStm.setString(3, name);
        prepStm.setString(4, surname);

        ResultSet resultSet1 = prepStm.executeQuery();

        while (resultSet1.next()) {
            System.out.println(resultSet1.getString(2) + " " + resultSet1.getString(3) + " " + resultSet1.getString(4));
        }
    }

    public static void fillDBWithEmployees(int numberOfEmployees) {
        for (int i = 0; i < numberOfEmployees; i++) {
            saveEmployeeIntoDB(new Employee("Filled" + i, "Filled surname" + i
                    , Position.JUNIOR_DEV, 2500 + i, true));
        }
    }
}

