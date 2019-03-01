package nyelvtanulas_kr_szakdolgozat;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.ObservableList;

public class DB {

    // A FoablakController induláskor meghívja a setDbUrl() metódust és a DB osztályban is beállítja az adatbázis helyét
    static String dbUrl = "";
    public static void setDbUrl(String dbUrl) {
        DB.dbUrl = dbUrl;
    }
    
    /**
     * A kapott lista szavait beírja a görgetett szavak táblába. Az autoCommit letiltásával és a Batch használatával jelentősen gyorsabban végezhetők
     * az adatbázis-műveletek.
     * @param szavak   Adatbázisba írandó szólista.
     * @param tabla
     */
    public static void dbIr(ArrayList<String> szavak, String tabla) {
        String into = "INSERT INTO " + tabla + " VALUES (?,?)";
        try (Connection kapcs = DriverManager.getConnection(dbUrl);
                PreparedStatement ps = kapcs.prepareStatement(into)) {
                kapcs.setAutoCommit(false);
                int count = 0;
                for (String szo: szavak) {
                    ps.setString(1, szo);
                    ps.setString(2, "gorgetett");
                    ps.addBatch();
                    count++;
                    if (count == 1000) {
                        ps.executeBatch();
                        System.out.println("Adabázisba írás sikeres!");
                        count = 0;
                    }
                }
                if (count != 0) {
                    ps.executeBatch();
                    System.out.println("Adabázisba írás sikeres!");
                }
                kapcs.commit();
        } catch (SQLException e) {
            System.out.println("Nem sikerült a görgetett-táblába írás!");
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * A kapott lista szavait kitörli a kapott táblából. AutoCommit letiltása és Batch használata.
     * @param szavak    Adatbázisból törledő szólista
     * @param tabla     A szavak törlése ebből a táblából történjen
     */
    public static void dbTorol(ArrayList<String> szavak, String tabla) {
        String delete = "DELETE FROM " + tabla + " WHERE szavak= ?;";
        try (Connection kapcs = DriverManager.getConnection(dbUrl);
                PreparedStatement ps = kapcs.prepareStatement(delete)) {
                kapcs.setAutoCommit(false);
            
                int count = 0;
                for (String szo: szavak) {
                    ps.setString(1, szo);
                    ps.addBatch();
                    count++;
                    if (count == 1000) {
                        ps.executeBatch();
                        System.out.println("Adatbáziból törlés sikeres!");
                        count = 0;
                    }
                }
                if (count != 0) {
                    ps.executeBatch();
                    System.out.println("Adatbázisból törlés sikeres!");
                }
                kapcs.commit();
        } catch (SQLException e) {
            System.out.println("Nem sikerült a" + tabla + "-ból törlés!");
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Az adott szóhoz tartozó adatbázis-rekordban az ANKI mező értékét átírja 1-re, jelezve, hogy készült belőle ANKI kártya.
     * @param tabla     Megadja melyik táblában kell módosítani
     * @param szavak    A módosítandó rekordot azonosító szó
     */
    public static void dbModosit(String tabla, ArrayList<String> szavak) {
        String update = "UPDATE " + tabla + " SET ANKI = ? WHERE szavak = ?";
        try (Connection kapcs = DriverManager.getConnection(dbUrl);
                PreparedStatement ps = kapcs.prepareStatement(update)) {
                kapcs.setAutoCommit(false);
                
                int count = 0;
                for (String szo: szavak) {
                    ps.setInt(1, 1);
                    ps.setString(2, szo);
                    ps.addBatch();
                    count++;
                    if (count == 1000) {
                        ps.executeBatch();
                        System.out.println("Adatbázis módosítás sikeres!");
                        count = 0;
                    }
                }
                if (count != 0) {
                    ps.executeBatch();
                    System.out.println("Adatbázis módosítás sikeres!");
                }
                kapcs.commit();
        } catch (SQLException e) {
            System.out.println("Nem sikerült a " + tabla + " módosítása!");
            System.out.println(e.getMessage());
        }
    }
    
    // A kapott táblához hozzáadja a kapott szót és állapotát
    public static void dbBeIr(String tabla, String szo, String allapot) {
        String into = "INSERT INTO " + tabla + " VALUES (?,?)";
        try (Connection kapcs = DriverManager.getConnection(dbUrl);
                PreparedStatement ps = kapcs.prepareStatement(into)) {
                ps.setString(1, szo);
                ps.setString(2, allapot);
                int sorok = ps.executeUpdate();
                System.out.println(sorok + " sor hozzáadva.");
        } catch (SQLException e) {
            System.out.println("Nem sikerült a " + tabla + "-táblába" + " írás!");
            System.out.println(e.getMessage());
        }
    }
    
    // A kapott táblához hozzáadja a kapott szót, mondatot, fordítást és az ANKI oszlop értékét (0 vagy 1)
    public static void dbBeIr(String tabla, String szo, String mondat, String forditas, int anki) {
        String into = "INSERT INTO " + tabla + " (szavak, mondatok, forditas, ANKI) VALUES (?,?,?,?)";
        try (Connection kapcs = DriverManager.getConnection(dbUrl);
                PreparedStatement ps = kapcs.prepareStatement(into)) {
                ps.setString(1, szo);
                ps.setString(2, mondat);
                ps.setString(3, forditas);
                ps.setInt(4, anki);
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
    
    /**
     * A kapott tábla szavait lekérdezi az adatbázisból és ha létezik a listában, akkor a lista szavát átnevezi torlendo-re, 
       jelezve, hogy a táblázat megjelenítése előtt törölni kell a listából. Ha a görgetettszavak táblán megy végig és a szó 
       létezik a listában, akkor 1-gyel növeli a gyakoriságát a listában (így biztos, hogy legalább kétszer előfordul globálisan) és 
       törli a táblából a szót.
     * @param tabla         A kapott tábla neve
     * @param data          A kapott lista a feldolgozott szavakkal,mondatokkal
     * @param szavak_indexe A kapott HashMap, ebben tároljuk azt, hogy az adott szó a listában hányadik indexen van
     * @param miket         Az összevetni kívánt szó állapota (ismert,tanulando,ignoralt,gorgetett)
     */
    public static void dbOsszevet(String tabla, ObservableList<Sor> data, HashMap<String, Integer> szavak_indexe,
                                  String miket) {
        String feltetel = "";
        if (miket.equals("ismertignoralt")) {
            feltetel = " WHERE allapot='ismert' OR allapot='ignoralt'";
        } else if (miket.equals("gorgetett")) {
            feltetel = " WHERE allapot='gorgetett'";
        }
        
        String query = "SELECT szavak FROM " + tabla + feltetel;
        ArrayList<String> szavak = new ArrayList();
        try (Connection kapcs = DriverManager.getConnection(dbUrl);
            PreparedStatement ps = kapcs.prepareStatement(query)) {
            ResultSet eredmeny = ps.executeQuery();
            
            while (eredmeny.next()) {
                String szo = eredmeny.getString("szavak");
                if (szavak_indexe.get(szo) != null) {
                    // ha nem a gorgetettszavak táblán megyünk végig 
                    if (!miket.equals("gorgetett")) {
                        data.get(szavak_indexe.get(szo)).setSzo("torlendo");
                    } else {
                        int gyak = data.get(szavak_indexe.get(szo)).getGyak();
                        data.get(szavak_indexe.get(szo)).setGyak(++gyak);
                        szavak.add(szo);
                    }
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Nem sikerült az adatbázis-lekérdezés!");
            System.out.println(e.getMessage());
        }
        
        // Görgetett szó előfordult a szövegben, ezért töröljük az adatbázisból
        if (!szavak.isEmpty()) {
            dbTorol(szavak, tabla);
        }
    }
    
    public static void dbTablatKeszit(String TablaNevEleje) {
        String createTable = "CREATE TABLE IF NOT EXISTS " + TablaNevEleje + "szavak" + " (szavak VARCHAR(100) PRIMARY KEY,"
                                                                  + "allapot VARCHAR(15));";
        try (Connection kapcs = DriverManager.getConnection(dbUrl);
                PreparedStatement ps = kapcs.prepareStatement(createTable)) {
                ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        String createIndex = "CREATE INDEX IF NOT EXISTS allapot ON " + TablaNevEleje + "szavak" + "(allapot);";
        try (Connection kapcs = DriverManager.getConnection(dbUrl);
                PreparedStatement ps2 = kapcs.prepareStatement(createIndex)) {
                ps2.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        
        createTable = "CREATE TABLE IF NOT EXISTS " + TablaNevEleje + "tanulando" + " (szavak VARCHAR(100) NOT NULL PRIMARY KEY,"
                       + "mondatok TEXT NOT NULL, kikerdezes_ideje DATE, forditas VARCHAR(100), ANKI INT NOT NULL);";
        try (Connection kapcs = DriverManager.getConnection(dbUrl);
                PreparedStatement ps = kapcs.prepareStatement(createTable)) {
                ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        createIndex = "CREATE INDEX IF NOT EXISTS allapot ON " + TablaNevEleje + "tanulando" + "(ANKI);";
        try (Connection kapcs = DriverManager.getConnection(dbUrl);
                PreparedStatement ps2 = kapcs.prepareStatement(createIndex)) {
                ps2.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Megadja az adatbázisban lévő tanulando szavakat tartalmazó táblák nevének első két karakterét.
     * @return  Visszaadja a tanulando tábla első két karakterét
     */
    public static ArrayList<String> dbTablakNevenekLekerdezese() {
        try (Connection kapcs = DriverManager.getConnection(dbUrl)) {
            DatabaseMetaData databaseMetaData = kapcs.getMetaData();
            ResultSet rs = databaseMetaData.getTables(null, null, "%tanulando%", null);
            ArrayList<String> tablakNevei = new ArrayList<>();
            while(rs.next()) {
                String tablaNev = rs.getString("TABLE_NAME");
                tablaNev = tablaNev.substring(0,2);
                tablakNevei.add(tablaNev);
            }
            return tablakNevei;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}