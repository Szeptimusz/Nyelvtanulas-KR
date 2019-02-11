package nyelvtanulas_kr_szakdolgozat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class DB {
    
    final static String dbUrl = "jdbc:sqlite:C:/Szoftverfejlesztés/OKJ programozás/Szakdolgozat/"
                            + "Githubos verzió/nyelvtanulas_kr_szakdolgozat/nyelvtanulas.db";

    // A kapott lista szavait beírja a görgetett szavak táblába (Batch: csak egyszer kell meghívni a dbIr() metódust)
    public static void dbIr(ArrayList<String> szavak) {
        String into = "INSERT INTO gorgetettszavak (szavak) VALUES (?)";
        try (Connection kapcs = DriverManager.getConnection(dbUrl);
                PreparedStatement ps = kapcs.prepareStatement(into)) {
                
                for (String szo: szavak) {
                    ps.setString(1, szo);
                    ps.addBatch();
                    System.out.println("Szó hozzáadva!");
                }
                
                ps.executeBatch();
        } catch (SQLException e) {
            System.out.println("Nem sikerült a görgetett-táblába írás!");
            System.out.println(e.getMessage());
        }
    }
    
    // A kapott lista szavait kitörli a kapott táblából
    public static void dbTorol(ArrayList<String> szavak, String tabla) {
        String delete = "DELETE FROM " + tabla + " WHERE szavak= ?;";
        try (Connection kapcs = DriverManager.getConnection(dbUrl);
                PreparedStatement ps = kapcs.prepareStatement(delete)) {
                
                for (String szo: szavak) {
                    ps.setString(1, szo);
                    ps.addBatch();
                    System.out.println("Szó hozzáadva!");
                }
                
                ps.executeBatch();
        } catch (SQLException e) {
            System.out.println("Nem sikerült a" + tabla + "-ból törlés!");
            System.out.println(e.getMessage());
        }
    }
    
    // A kapott táblához hozzáadja a kapott szót
    public static void dbBeIr(String tabla, String szo) {
        String into = "INSERT INTO " + tabla + " (szavak) VALUES (?)";
        try (Connection kapcs = DriverManager.getConnection(dbUrl);
                PreparedStatement ps = kapcs.prepareStatement(into)) {
                ps.setString(1, szo);
                int sorok = ps.executeUpdate();
                System.out.println(sorok + " sor hozzáadva.");
        } catch (SQLException e) {
            System.out.println("Nem sikerült a " + tabla + "-táblába" + " írás!");
            System.out.println(e.getMessage());
        }
    }
    
    // A kapott táblához hozzáadja a kapott szót- és mondatot
    public static void dbBeIr(String tabla, String szo, String mondat) {
        String into = "INSERT INTO " + tabla + " (szavak, mondatok) VALUES (?,?)";
        try (Connection kapcs = DriverManager.getConnection(dbUrl);
                PreparedStatement ps = kapcs.prepareStatement(into)) {
                ps.setString(1, szo);
                ps.setString(2, mondat);
                int sorok = ps.executeUpdate();
                System.out.println(sorok + " sor hozzáadva.");
        } catch (SQLException e) {
            System.out.println("Nem sikerült a " + tabla + "-táblába" + " írás!");
            System.out.println(e.getMessage());
        }
    }
    
    // A kapott táblából törli a kapott szót
    public static void dbSzotTorol(String tabla, String szo) {
        String delete = "DELETE FROM " + tabla + " WHERE szavak= ?;";
        try (Connection kapcs = DriverManager.getConnection(dbUrl);
                PreparedStatement ps = kapcs.prepareStatement(delete)) {
                ps.setString(1, szo);
                int sorok = ps.executeUpdate();
                System.out.println(sorok + " sor törölve.");
        } catch (SQLException e) {
            System.out.println("Nem sikerült a: " + szo + " törlése a: " + tabla + " táblából!");
            System.out.println(e.getMessage());
        }
    }
}
 