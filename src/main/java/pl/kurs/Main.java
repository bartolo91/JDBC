package pl.kurs;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        // jdbc:mysql://localhost:3306/ksiegarnia
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ksiegarnia?useSSL=false&serverTimezone=UTC"
                , "root"
                , "root"
        );

        Statement stm = conn.createStatement();

        String selectQuery = "select * from klienci";
        ResultSet resultSet = stm.executeQuery(selectQuery);

        while (resultSet.next()) {
            int idKlienta = resultSet.getInt(1);
            String name = resultSet.getString("imie");
            System.out.println(idKlienta + " " + name);
        }

//        String insertQuery = "insert into klienci values(null, 'Tomek', 'Jankowski');";
//        int insertedRows = stm.executeUpdate(insertQuery);
//        System.out.println(insertedRows);

//        String deleteQuery = "delete from klienci where idklienta = 5";
//        int deletedRows = stm.executeUpdate(deleteQuery);
//        System.out.println(deletedRows);

//        String updateQuery = "update klienci set nazwisko ='kowalski' where idklienta = 6";
//        int uptadtedRows = stm.executeUpdate(updateQuery);
//        System.out.println(uptadtedRows);


        // Stwórz klase samochod marka model cena przebieg.
        // Stwórz kilka samochodow i zapisz je do bazy danych,
        // nastepnie usun jedno auto a potem inne zmien marke
        // odczytaj wszystkie auta


        // Stwórz tabele user która ma login haslo i bardzo tajne dane, dodaj kilka rekordow
        // https://www.w3schools.com/sql/sql_injection.asp

        String select = "select * from user where login = 'test1234' or 1=1";

        ResultSet resultSet1 = stm.executeQuery(select);

        while (resultSet1.next()) {
            System.out.println(resultSet1.getString(4));
        }

        // prepared statement
        String selectBooks = "select * from ksiazki where idksiazki > ? and cena > ?";
        PreparedStatement prepStm = conn.prepareStatement(selectBooks);

        prepStm.setInt(1, 1);
        prepStm.setInt(2, 40);

        ResultSet resultSet2 = prepStm.executeQuery();

        while (resultSet2.next()) {
            System.out.println(resultSet2.getString(2));
        }

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/ksiegarnia?useSSL=false&serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        // obsluga wyjatkow
        Connection conn2 = null;
        Statement stm2 = null;
        ResultSet rs2 = null;

        try {
            conn2 = dataSource.getConnection();
            stm2 = conn2.createStatement();
            rs2 = stm2.executeQuery("Select * from klienci");

            while (rs2.next()) {
                System.out.println(rs2.getString("imie"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs2 != null) rs2.close();
                if (stm2 != null) stm2.close();
                if (rs2 != null) rs2.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

}
