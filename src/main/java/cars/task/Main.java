package cars.task;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {

        // Stwórz klase samochod marka model cena przebieg.
        // Stwórz kilka samochodow i zapisz je do bazy danych,
        // nastepnie usun jedno auto a potem inne zmien marke
        // odczytaj wszystkie auta

        for (int i = 0; i < 9; i++) {
            new Car("Test" + i, "testModel" + i, 12000 + i, 1220 + i);
        }
        System.out.println(Car.carsExtension);

        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/samochody?useSSL=false&serverTimezone=UTC"
                , "root"
                , "root"
        );

        Statement stm = conn.createStatement();

//        String insertCar = "insert into car values(null,'car','test model',233223,22222);";
//        int insertedCar = stm.executeUpdate(insertCar);
//
//        String insertCar1 = "insert into car values(null,'car1','test model1',233223,22222);";
//        int insertedCar1 = stm.executeUpdate(insertCar1);
//
//        String insertCar2 = "insert into car values(null,'car2','test model2',233223,22222);";
//        int insertedCar2 = stm.executeUpdate(insertCar2);
//
//        String insertCar3 = "insert into car values(null,'car3','test model3',233223,22222);";
//        int insertedCar3 = stm.executeUpdate(insertCar3);

//        String deleteCar = "delete from car where carid = 1";
//        int deletedCar = stm.executeUpdate(deleteCar);
//        System.out.println(deletedCar);

        String updateCar = "update car set brand = 'zmieniony brand' where carid = 3 ";
        stm.executeUpdate(updateCar);

        String selectQuery = "select * from car";
        ResultSet resultSet = stm.executeQuery(selectQuery);

        while (resultSet.next()) {
            String brand = resultSet.getString("brand");
            String model = resultSet.getString("model");
            System.out.println(brand + model);
        }
    }
}

