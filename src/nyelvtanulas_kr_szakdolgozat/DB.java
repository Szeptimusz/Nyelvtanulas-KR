package nyelvtanulas_kr_szakdolgozat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DB {
    
    final String dbUrl = "jdbc:mysql://localhost:3306/";
    
    // A kapott táblához hozzáadja a kapott szót
    public void dbBeIr(String tabla, String szo) {
        String into = "INSERT INTO nyelvtanulas." + tabla + " (szavak) VALUES (?)";
        try (Connection kapcs = DriverManager.getConnection(dbUrl,"root","");
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
    public void dbBeIr(String tabla, String szo, String mondat) {
        String into = "INSERT INTO nyelvtanulas." + tabla + " (szavak, mondatok) VALUES (?,?)";
        try (Connection kapcs = DriverManager.getConnection(dbUrl,"root","");
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
    public void dbSzotTorol(String tabla, String szo) {
        String delete = "DELETE FROM nyelvtanulas." + tabla + " WHERE szavak= ?;";
        try (Connection kapcs = DriverManager.getConnection(dbUrl,"root","");
                PreparedStatement ps = kapcs.prepareStatement(delete)) {
                ps.setString(1, szo);
                int sorok = ps.executeUpdate();
                System.out.println(sorok + " sor törölve.");
        } catch (SQLException e) {
            System.out.println("Nem sikerült a: " + szo + " törlése a: " + tabla + " táblából!");
            System.out.println(e.getMessage());
        }
    }
    
    // Adatbázis és táblák létrehozása
    public void adatbazisEsTablakLetrehozasa() {
        String sql = "CREATE DATABASE IF NOT EXISTS nyelvtanulas " + 
                    "DEFAULT CHARACTER SET utf8mb4 " +
                    "COLLATE utf8mb4_hungarian_ci";
        try (Connection kapcs = DriverManager.getConnection(dbUrl,"root","");
                PreparedStatement ps = kapcs.prepareStatement(sql)) {
            // Adatbázis létrehozása ha még nem létezik ilyen néven
            ps.executeUpdate();
            System.out.println("Adatbázis sikeresen létrehozva!");
            
            // Táblák létrehozása ha még nem léteznek ilyen néven
            System.out.println("Táblák létrehozása .......");
            String ismert = "CREATE TABLE IF NOT EXISTS nyelvtanulas.ismertszavak " +
                "(szavak VARCHAR(100) PRIMARY KEY, INDEX szo (szavak))";
            // A mondatok mező TEXT, azért, hogy a nagyon hosszú mondatokat is tudja tárolni
            String tanulando = "CREATE TABLE IF NOT EXISTS nyelvtanulas.tanulandoszavak " +
                    "(szavak VARCHAR(100) NOT NULL PRIMARY KEY ," +
                    "mondatok TEXT NOT NULL ," + 
                    "kikerdezes_ideje DATE NULL, INDEX szo (szavak))";
            String ignoralt = "CREATE TABLE IF NOT EXISTS nyelvtanulas.ignoraltszavak " +
                    "(szavak VARCHAR(100) PRIMARY KEY, INDEX szo (szavak))";
            String gorgetett = "CREATE TABLE IF NOT EXISTS nyelvtanulas.gorgetettszavak " +
                    "(szavak VARCHAR(100) PRIMARY KEY, INDEX szo (szavak))";
            ps.executeUpdate(ismert);
            ps.executeUpdate(tanulando);
            ps.executeUpdate(ignoralt);
            ps.executeUpdate(gorgetett);
            System.out.println("Táblák sikeresen létrehozva!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
 