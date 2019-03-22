package nyelvtanulas_kr_szakdolgozat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.ObservableList;
import static nyelvtanulas_kr_szakdolgozat.FoablakController.adatbazisUtvonal;
import static panel.Panel.hiba;

/**
 *
 * @author Kremmer Róbert
 */
public class DB {
   
    /**
     * Az adott szóhoz tartozó adatbázis-rekordban az ANKI mező értékét átírja 1-re, jelezve, hogy készült belőle ANKI kártya.
     * @param tabla     Megadja melyik táblában kell módosítani
     * @param szavak    A módosítandó rekordot azonosító szó
     */
    public static void ankitModositAdatbazisban(String tabla, ArrayList<String> szavak) {
        String update = "UPDATE " + tabla + " SET ANKI = ? WHERE szavak = ?";
        try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
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
            hiba("Hiba!",e.getMessage());
        }
    }
    
    /**
     * A kapott táblához hozzáadja a kapott szót és állapotát.
     * @param tabla:   A kapott tábla neve
     * @param szo:     A kapott szó
     * @param allapot: A kiírandó szó állapota (ismert,ignoralt,tanulando)
     */
    public static void szotBeirAdatbazisba(String tabla, String szo, String allapot) {
        String into = "INSERT INTO " + tabla + " VALUES (?,?)";
        try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
                PreparedStatement ps = kapcs.prepareStatement(into)) {
                ps.setString(1, szo);
                ps.setString(2, allapot);
                int sorok = ps.executeUpdate();
                System.out.println(sorok + " sor hozzáadva.");
        } catch (SQLException e) {
                hiba("Hiba!",e.getMessage());
        }
    }

    /**
     * A kapott táblához hozzáadja a kapott szót, mondatot, fordítást és az ANKI oszlop értékét (0 vagy 1)
     * @param tabla:    A kapott tábla neve.
     * @param szo:      A kapott szó
     * @param mondat:   A kapott mondat
     * @param forditas: A szó általunk megadott fordítása
     * @param anki:     A kiírandó anki állapot (0 vagy 1)
     */
    public static void tanulandotBeirAdatbazisba(String tabla, String szo, String mondat, String forditas, int anki) {
        String into = "INSERT INTO " + tabla + " (szavak, mondatok, kikerdezes_ideje, forditas, ANKI) VALUES (?,?,?,?,?)";
        try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
                PreparedStatement ps = kapcs.prepareStatement(into)) {
                ps.setString(1, szo);
                ps.setString(2, mondat);
                ps.setLong(3, System.currentTimeMillis());
                ps.setString(4, forditas);
                ps.setInt(5, anki);
                int sorok = ps.executeUpdate();
                System.out.println(sorok + " sor hozzáadva.");
        } catch (SQLException e) {
            System.out.println("Nem sikerült a " + tabla + "-táblába" + " írás!");
            hiba("Hiba!",e.getMessage());
        }
    }

    /**
     * A kapott táblából törli a kapott szót
     * @param tabla: A kapott tábla neve.
     * @param szo:   A kapott szó.
     */
    public static void szotTorolAdatbazisbol(String tabla, String szo) {
        String delete = "DELETE FROM " + tabla + " WHERE szavak= ?;";
        try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
                PreparedStatement ps = kapcs.prepareStatement(delete)) {
                ps.setString(1, szo);
                int sorok = ps.executeUpdate();
                System.out.println(sorok + " sor törölve.");
        } catch (SQLException e) {
            System.out.println("Nem sikerült a: " + szo + " törlése a: " + tabla + " táblából!");
            hiba("Hiba!",e.getMessage());
        }
    }
    
    /**
     * A kapott tábla szavait lekérdezi az adatbázisból és ha létezik a listában, akkor a lista szavát átnevezi torlendo-re, 
       jelezve, hogy a táblázat megjelenítése előtt törölni kell a listából.
     * @param tabla         A kapott tábla neve
     * @param data          A kapott lista a feldolgozott szavakkal,mondatokkal
     * @param szavak_indexe A kapott HashMap, ebben tároljuk azt, hogy az adott szó a listában hányadik indexen van
     * @param miket         Az összevetni kívánt szó állapota (ismert,tanulando,ignoralt)
     */
    public static void adatbazistListavalOsszevet(String tabla, ObservableList<Sor> data, HashMap<String, Integer> szavak_indexe,
                                  String miket) {
        String feltetel = "";
        if (miket.equals("ismertignoralt")) {
            feltetel = " WHERE allapot='ismert' OR allapot='ignoralt'";
        }
        
        String query = "SELECT szavak FROM " + tabla + feltetel;
        try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
            PreparedStatement ps = kapcs.prepareStatement(query)) {
            ResultSet eredmeny = ps.executeQuery();
            
            while (eredmeny.next()) {
                String szo = eredmeny.getString("szavak");
                if (szavak_indexe.get(szo) != null) {
                    data.get(szavak_indexe.get(szo)).setSzo("torlendo");
                    szavak_indexe.put(szo, null);
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Nem sikerült az adatbázis-lekérdezés!");
            hiba("Hiba!",e.getMessage());
        }
        
    }
    
    /**
     * Attól függően, hogy melyik nyelvet választottuk forrásnyelvnek, létrehoz az adott nyelv számára két egyedi táblát és 
     * elkészíti hozzájuk az indexeket (abban az esetben ha még nem léteznek a táblák és az indexek).
     * @param TablaNevEleje:  A választott forrásnyelv alapján generált String (pl. angolnál: 'en_' németnél: 'de_'),
     *                        az ehhez kapcsolódó 'szavak' vagy 'tanulandó' együtt alkotja a tábla teljes nevét. 
     */
    public static void tablakatKeszit(String TablaNevEleje) {
        String createTable = "CREATE TABLE IF NOT EXISTS " + TablaNevEleje + "szavak" + " (szavak VARCHAR(100) PRIMARY KEY,"
                                                                  + "allapot VARCHAR(15));";
        try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
                PreparedStatement ps = kapcs.prepareStatement(createTable)) {
                ps.executeUpdate();
        } catch (SQLException e) {
            hiba("Hiba!",e.getMessage());
        }
        
        String createIndex = "CREATE INDEX IF NOT EXISTS allapot ON " + TablaNevEleje + "szavak" + "(allapot);";
        try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
                PreparedStatement ps2 = kapcs.prepareStatement(createIndex)) {
                ps2.executeUpdate();
        } catch (SQLException e) {
            hiba("Hiba!",e.getMessage());
        }
        
        
        createTable = "CREATE TABLE IF NOT EXISTS " + TablaNevEleje + "tanulando" + " (szavak VARCHAR(100) NOT NULL PRIMARY KEY,"
                       + "mondatok TEXT NOT NULL, kikerdezes_ideje BIGINT, forditas VARCHAR(100), ANKI INT NOT NULL);";
        try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
                PreparedStatement ps = kapcs.prepareStatement(createTable)) {
                ps.executeUpdate();
        } catch (SQLException e) {
            hiba("Hiba!",e.getMessage());
        }
        
        createIndex = "CREATE INDEX IF NOT EXISTS allapot ON " + TablaNevEleje + "tanulando" + "(ANKI);";
        try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
                PreparedStatement ps2 = kapcs.prepareStatement(createIndex)) {
                ps2.executeUpdate();
        } catch (SQLException e) {
            hiba("Hiba!",e.getMessage());
        }
    }
    
    /**
     * Az kapott táblából lekérdezi a kapott állapotú sorok számát.
     * @param tabla:    A kapott tábla teljes neve
     * @param allapot:  A kapott állapot (ismert, ignoralt)
     * @return :        Visszaadja, hogy hány ilyen állapotú sor van.
     */
    public static int statisztikatLekerdez(String tabla, String allapot) {
        String query = "SELECT COUNT(*) FROM " + tabla + " WHERE allapot='"+ allapot +"'";
        try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
                PreparedStatement ps = kapcs.prepareStatement(query)) {
                ResultSet eredmeny = ps.executeQuery();
                int sorokSzama = eredmeny.getInt(1);
                return sorokSzama;
        } catch (SQLException e) {
            return 0;
        }
    }
    
    /**
     * A kapott táblából lekérdezi a kapott ANKI állapotú sorok számát.
     * @param tabla:        A kapott tábla teljes neve.
     * @param ankiAllapot:  A kapott ANKI állapot (0 vagy 1)
     * @return :            Visszaadja, hogy hány ilyen állapotú sor van.
     */
    public static int statisztikatTanulandobolLekerdez(String tabla, int ankiAllapot) {
        String query = "SELECT COUNT(*) FROM " + tabla + " WHERE ANKI='"+ ankiAllapot +"'";
        try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
                PreparedStatement ps = kapcs.prepareStatement(query)) {
                ResultSet eredmeny = ps.executeQuery();
                int sorokSzama = eredmeny.getInt(1);
                return sorokSzama;
        } catch (SQLException e) {
            return 0;
        }
    }
    
    public static ArrayList<Sor> tanulandotLekerdez(String tabla) {
        ArrayList<Sor> rekordok = new ArrayList<>();
        String query = "SELECT * FROM " + tabla + " WHERE kikerdezes_ideje<=" + System.currentTimeMillis();
        try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
             PreparedStatement ps = kapcs.prepareStatement(query)) {
             ResultSet eredmeny = ps.executeQuery();
             while (eredmeny.next()) {
                 rekordok.add(new Sor(eredmeny.getString("szavak"),
                                      eredmeny.getString("mondatok"),
                                      eredmeny.getString("forditas"),
                                      eredmeny.getLong("kikerdezes_ideje")));
             }
             return rekordok;
        } catch (SQLException e) {
            hiba("Hiba!",e.getMessage());
            return rekordok;
        }
    }
    
    public static void frissitKikerdezes(String tabla, String szo, long kikerdezesIdeje) {
        String update = "UPDATE " + tabla + " SET kikerdezes_ideje= ? WHERE szavak= ?;";
        try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
             PreparedStatement ps = kapcs.prepareStatement(update)) {
                ps.setLong(1, kikerdezesIdeje);
                ps.setString(2, szo);
                ps.executeUpdate();
        } catch (SQLException e) {
            hiba("Hiba!",e.getMessage());
        }
    }
}