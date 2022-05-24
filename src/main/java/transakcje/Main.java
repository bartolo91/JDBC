package transakcje;

//  Zasady ACID (anagram) to zbiór zasad które opisują transakcję:
//•	Atomowość – wykonanie wszystkich kroków wchodzących w skład transakcji decyduje o jej sukcesie;
//•	Spójność – stan bazy danych zawsze przedstawia stan przed lub po transakcji;
//•	Izolacja – transakcja jest odizolowana od innych transakcji, działa niezależnie od pozostałych;
//•	Trwałość – w przypadku awarii systemu bazodanowego, transakcja albo wykonana jest w całości albo wcale;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setUrl("jdbc:mysql://localhost:3306/ksiegarnia?useSSL=false&serverTimezone=UTC");
//        dataSource.setUsername("root");
//        dataSource.setPassword("root");
//
//        Connection conn = null;
//
//        try {
//            conn = dataSource.getConnection();
//            conn.setAutoCommit(false);
//            PreparedStatement prepStm1 = null;
//
//            try {
//                prepStm1 = conn.prepareStatement("delete from klienci where nazwisko = ?");
//                prepStm1.setString(1, "Nieznany");
//
//                int deletedRows = prepStm1.executeUpdate();
//                System.out.println(deletedRows);
//            } finally {
//                if (prepStm1 != null) prepStm1.close();
//            }
//
//            PreparedStatement prepStm2 = null;
//
//            try {
//                prepStm2 = conn.prepareStatement("insert into klienci values(null, ?, ?)");
//                prepStm2.setString(1, "jan");
//                // prepStm2.setString(2, "Kiepura");
//
//                int insertedRows = prepStm2.executeUpdate();
//                System.out.println(insertedRows);
//            } finally {
//                if (prepStm2 != null) prepStm2.close();
//            }
//            conn.commit();
//
//        } catch (SQLException e) {
//            System.out.println("ROLLBACK!!!!!!!!!");
//            conn.rollback();
//            e.printStackTrace();
//        } finally {
//            if (conn != null) {
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        // Stwórz tabele Pociag ktora ma nazwe, dlugosc, to czy jest wars oraz laczna liczba miejsc.
        // Za pomoca transakcji dodaj jaksi pociag i usun stary, ale jesli usuniceie sie nie powiedzie
        // to chcemy cofnac inserta, no bo ile mozna miec pociagow w pkp? O_O


        // Przetestuj działanie rollbacka na trzech operacjach np robimy inserta, deleta i updejta, jak sie sypnie updejt
        // to chcemy cofnac wszystkie zmiany, na dowolnej tabeli


        BasicDataSource dataSource1 = new BasicDataSource();
        dataSource1.setUrl("jdbc:mysql://localhost:3306/train?useSSL=false&serverTimezone=UTC");
        dataSource1.setUsername("root");
        dataSource1.setPassword("root");

        Connection trainconn = null;

        try {
            trainconn = dataSource1.getConnection();
            trainconn.setAutoCommit(false);
            PreparedStatement trainPrep = null;

            try {
                trainPrep = trainconn.prepareStatement("insert into train values (null, ?,?, ?, ?)");

                trainPrep.setString(1, "NowyPociąg");
                trainPrep.setInt(2, 100);
                trainPrep.setInt(3, 1);
                trainPrep.setInt(4, 500);

                int insertRows = trainPrep.executeUpdate();
                System.out.println(insertRows);
            } finally {
                if (trainPrep != null) trainPrep.close();
            }
            PreparedStatement trainPrep2 = null;
            try {
                trainPrep2 = trainconn.prepareStatement("delete from train where name =?");
                trainPrep2.setString(1, "Matejko");

                int deleteTrain = trainPrep2.executeUpdate();
                System.out.println(deleteTrain);
            } finally {
                if (trainPrep2 != null) trainPrep2.close();
            }
            trainconn.commit();

        } catch (SQLException e) {
            System.out.println("ROLLBACK!!!!!!!!!");
            trainconn.rollback();
            e.printStackTrace();
        } finally {
            if (trainconn != null) {
                try {
                    trainconn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


        // Przetestuj działanie rollbacka na trzech operacjach np robimy inserta, V
        // deleta i updejta, jak sie sypnie updejt
        // to chcemy cofnac wszystkie zmiany, na dowolnej tabeli

        try {
            trainconn = dataSource1.getConnection();
            trainconn.setAutoCommit(false);
            PreparedStatement trainPrep = null;

            try {
                trainPrep = trainconn.prepareStatement("insert into train values (null, ?,?, ?, ?)");

                trainPrep.setString(1, "szczesliwy pociag Macieja");
                trainPrep.setInt(2, 200);
                trainPrep.setInt(3, 0);
                trainPrep.setInt(4, 300);

                int insertRows = trainPrep.executeUpdate();
                System.out.println(insertRows);
            } finally {
                if (trainPrep != null) trainPrep.close();
            }
            PreparedStatement trainPrep2 = null;
            try {
                trainPrep2 = trainconn.prepareStatement("delete from train where name =?");
                trainPrep2.setString(1, "szczesliwy pociag Macieja");

                int deleteTrain = trainPrep2.executeUpdate();
                System.out.println(deleteTrain);
            } finally {
                if (trainPrep2 != null) trainPrep2.close();
            }
            PreparedStatement trainPrep3 = null;
            try {

                trainPrep3 = trainconn.prepareStatement("update train set totalcapacity = ? where trainid= ?;");
                trainPrep3.setInt(1, 220);
                trainPrep3.setInt(2, 2);
                int updateTrain = trainPrep3.executeUpdate();
                System.out.println(updateTrain);
            } finally {
                if (trainPrep3 != null) trainPrep3.close();
            }


            //tuu sie konczy
            trainconn.commit();

        } catch (SQLException e) {
            System.out.println("ROLLBACK!!!!!!!!!");
            trainconn.rollback();
            e.printStackTrace();
        } finally {
            if (trainconn != null) {
                try {
                    trainconn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}