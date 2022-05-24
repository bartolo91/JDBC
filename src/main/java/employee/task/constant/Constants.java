package employee.task.constant;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class Constants {

    @SneakyThrows
    public static Connection getConnection(){
        return  DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/employees?useSSL=false&serverTimezone=UTC"
                , "root"
                , "root"
        );
    }
}
