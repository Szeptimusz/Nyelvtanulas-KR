package nyelvtanulas_kr_szakdolgozat;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.ObservableList;
import static nyelvtanulas_kr_szakdolgozat.FoablakController.uzenetek;
import static panel.Panel.hiba;

/**
 * Az adatbázis kezelést végző osztály. A benne található statikus metódusok bármely más 
 * osztályból meghívhatók. 
 * @author Kremmer Róbert
 */
public class DB {
   
    static String adatbazisUtvonal;
    
    /**
     * Megpróbál csatlakozni a projekt mappájában a kapott nevű adatbázishoz és beállítja az elérési útvonalat
     * az osztályváltozóba. Ha még nem létezik ilyen nevű adatbázis, akkor létrehozza.
     * @param adatbazisNeve   Az adatbázis neve 
     */
    public static void adatbazistKeszit(String adatbazisNeve) {
        String utvonal = new File("").getAbsolutePath();
        adatbazisUtvonal = "jdbc:sqlite:" + utvonal + adatbazisNeve;
 
        try (Connection conn = DriverManager.getConnection(adatbazisUtvonal)) {
            // Ha nem létezik ilyen adatbázis, akkor a kapcsolódási kísérlet 
            // automatikusan létrehozza
            
        } catch (SQLException e) {
            hiba(uzenetek.get("hiba"),e.getMessage());
        }
    }
    
    /**
     * A kapott lista szavainak ANKI mezőjét az adatbázisban átírja 1-re, jelezve, hogy az adott rekordból már 
     * készült Anki-import. Nagy mennyiségű sor módosítása esetén a műveletet gyorsítja az AutoCommit letiltása 
     * és a Batch használata.
     * @param tabla     A tábla neve
     * @param szavak    A szavakat tartalmazó lista neve
     */
    public static void ankitModositAdatbazisban(String tabla, ArrayList<String> szavak) {
        String update = "UPDATE " + tabla + " SET ANKI = ? WHERE szavak = ?";
        try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
                PreparedStatement ps = kapcs.prepareStatement(update)) {
            
                kapcs.setAutoCommit(false);  
                int count = 0;
                for (String szo : szavak) {
                    ps.setInt(1, 1);
                    ps.setString(2, szo);
                    ps.addBatch();
                    count++;
                    if (count == 1000) {
                        ps.executeBatch();
                        count = 0;
                    }
                }
                if (count != 0) {
                    ps.executeBatch();
                }
                kapcs.commit();
                
        } catch (SQLException e) {
            hiba(uzenetek.get("hiba"),e.getMessage());
        }
    }
    
    /**
     * A kapott táblához hozzáadja a kapott szót és annak állapotát.
     * @param tabla   A tábla neve
     * @param szo     A kiírandó szó
     * @param allapot A kiírandó szó állapota (ismert,ignoralt)
     */
    public static void szotBeirAdatbazisba(String tabla, String szo, String allapot) {
        String into = "INSERT INTO " + tabla + " VALUES (?,?)";
        try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
                PreparedStatement ps = kapcs.prepareStatement(into)) {
            
                ps.setString(1, szo);
                ps.setString(2, allapot);
                ps.executeUpdate();
                
        } catch (SQLException e) {
                hiba(uzenetek.get("hiba"),e.getMessage());
        }
    }

    /**
     * A kapott táblához hozzáadja a kapott szót, mondatot, fordítást, a kikérdezés idejét és az 
     * ANKI oszlop értékét (0 vagy 1). A kikérdezés ideje a függvény hívásakor az 1970-óta eltelt milliszekundumok számával egyenlő.
     * @param tabla    A tábla neve.
     * @param nevelo   A szó névelője (ha meg lett adva)
     * @param szo      A kiírandó szó
     * @param mondat   A kiírandó mondat
     * @param forditas A szó általunk megadott fordítása
     * @param anki     A kiírandó anki állapot (0 vagy 1)
     */
    public static void tanulandotBeirAdatbazisba(String tabla, String nevelo, String szo, String mondat, String forditas, int anki) {
        String into = "INSERT INTO " + tabla + " (nevelo, szavak, mondatok, kikerdezes_ideje, forditas, ANKI) VALUES (?,?,?,?,?,?)";
        try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
                PreparedStatement ps = kapcs.prepareStatement(into)) {
            
                ps.setString(1, nevelo);
                ps.setString(2, szo);
                ps.setString(3, mondat);
                ps.setLong(4, System.currentTimeMillis());
                ps.setString(5, forditas);
                ps.setInt(6, anki);
                ps.executeUpdate();
                
        } catch (SQLException e) {
            hiba(uzenetek.get("hiba"),e.getMessage());
        }
    }

    /**
     * A kapott táblából törli a kapott szót
     * @param tabla A tábla neve.
     * @param szo   A törlendő szó.
     */
    public static void szotTorolAdatbazisbol(String tabla, String szo) {
        String delete = "DELETE FROM " + tabla + " WHERE LOWER(szavak) = ?;";
        try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
                PreparedStatement ps = kapcs.prepareStatement(delete)) {
            
                ps.setString(1, szo);
                ps.executeUpdate();
                
        } catch (SQLException e) {
            hiba(uzenetek.get("hiba"),e.getMessage());
        }
    }
    
    /**
     * A kapott tábla szavait lekérdezi az adatbázisból és ha a szó létezik a listában, akkor a lista szavát átnevezi torlendo-re,
     * jelezve, hogy a táblázat megjelenítése előtt törölni kell a listából. Továbbá az adott szó hashmap-ben tárolt indexét
     * beállítja null-ra.
     * @param tabla         A kapott tábla neve
     * @param data          A szinkronizálandó lista neve
     * @param szavak_indexe A lista szavainak indexét tároló HashMap neve
     */
    public static void adatbazistListavalOsszevet(String tabla, ObservableList<Sor> data, HashMap<String, Integer> szavak_indexe) {
        
        String query = "SELECT szavak FROM " + tabla;
        try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
            PreparedStatement ps = kapcs.prepareStatement(query)) {
            ResultSet eredmeny = ps.executeQuery();
            
            while (eredmeny.next()) {
                String szo = eredmeny.getString("szavak").toLowerCase();
                if (szavak_indexe.get(szo) != null) {
                    data.get(szavak_indexe.get(szo)).setSzo("torlendo");
                    szavak_indexe.put(szo, null);
                }
            }
            
        } catch (SQLException e) {
            hiba(uzenetek.get("hiba"),e.getMessage());
        }
        
    }
    
    /**
     * A korábban választott nyelvtől függően létrehoz az adott nyelv számára két egyedi táblát és 
     * elkészíti hozzájuk az indexeket (abban az esetben ha még nem léteznek a táblák és az indexek).
     * @param TablaNevEleje  A korábban választott forrásnyelv alapján generált String (pl. angolnál: 'en_' németnél: 'de_'),
     *                        az ehhez kapcsolódó 'szavak' vagy 'tanulandó' együtt alkotja a tábla teljes nevét. 
     */
    public static void tablakatKeszit(String TablaNevEleje) {
        String createTable = "CREATE TABLE IF NOT EXISTS " + TablaNevEleje + "szavak" + " (szavak VARCHAR(30) NOT NULL UNIQUE,"
                                                                                        + "allapot VARCHAR(15) NOT NULL);";
        try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
                PreparedStatement ps = kapcs.prepareStatement(createTable)) {
                ps.executeUpdate();
        } catch (SQLException e) {
            hiba(uzenetek.get("hiba"),e.getMessage());
        }
        
        String createIndex = "CREATE INDEX IF NOT EXISTS allapot ON " + TablaNevEleje + "szavak" + "(allapot);";
        try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
                PreparedStatement ps2 = kapcs.prepareStatement(createIndex)) {
                ps2.executeUpdate();
        } catch (SQLException e) {
            hiba(uzenetek.get("hiba"),e.getMessage());
        }
        
        
        createTable = "CREATE TABLE IF NOT EXISTS " + TablaNevEleje + "tanulando" + " (nevelo VARCHAR(10) NOT NULL, "
                                                                                    + "szavak VARCHAR(30) NOT NULL UNIQUE,"
                                                                                    + "mondatok TEXT NOT NULL, "
                                                                                    + "kikerdezes_ideje BIGINT NOT NULL, "
                                                                                    + "forditas VARCHAR(100) NOT NULL, "
                                                                                    + "ANKI INT NOT NULL);";
        try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
                PreparedStatement ps = kapcs.prepareStatement(createTable)) {
                ps.executeUpdate();
        } catch (SQLException e) {
            hiba(uzenetek.get("hiba"),e.getMessage());
        }
        
        createIndex = "CREATE INDEX IF NOT EXISTS anki ON " + TablaNevEleje + "tanulando" + "(ANKI);";
        try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
                PreparedStatement ps2 = kapcs.prepareStatement(createIndex)) {
                ps2.executeUpdate();
        } catch (SQLException e) {
            hiba(uzenetek.get("hiba"),e.getMessage());
        }
    }
    
    /**
     * A kapott _szavak táblából lekérdezi a kapott állapotú szavak számát.
     * @param tabla    A tábla neve
     * @param allapot  A szó állapota (ismert, ignoralt)
     * @return         Visszaadja, hogy hány ilyen állapotú sor van.
     */
    public static int statisztikatLekerdez(String tabla, String allapot) {
        String query = "SELECT COUNT(*) FROM " + tabla + " WHERE allapot=?";
        try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
            PreparedStatement ps = kapcs.prepareStatement(query)) {
            
            ps.setString(1, allapot);
            ResultSet eredmeny = ps.executeQuery();
            int sorokSzama = eredmeny.getInt(1);
            return sorokSzama;
            
        } catch (SQLException e) {
            return 0;
        }
    }
    
    /**
     * A kapott _tanulando táblából lekérdezi a kapott ANKI állapotú szavak számát.
     * @param tabla       A tábla neve.
     * @param ankiAllapot A szó ANKI állapota (0 vagy 1)
     * @return            Visszaadja, hogy hány ilyen állapotú szó van.
     */
    public static int statisztikatTanulandobolLekerdez(String tabla, int ankiAllapot) {
        String query = "SELECT COUNT(*) FROM " + tabla + " WHERE ANKI=?";
        try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
            PreparedStatement ps = kapcs.prepareStatement(query)) {
            
            ps.setInt(1, ankiAllapot);
            ResultSet eredmeny = ps.executeQuery();
            int sorokSzama = eredmeny.getInt(1);
            return sorokSzama;
            
        } catch (SQLException e) {
            return 0;
        }
    }
    
    /**
     * Lekérdezi azokat a tanulandó szavakat (mondattal és fordítással), amiknél
     * a kikérdezés már esedékes (a jelenlegi időpont nagyobbegyenlő, mint a tervezett
     * kikérdezési idő). A rekordok szavak, mondatok, és fordítás mezőinek értékeiből Sor típusú objektumot készít,
     * amit hozzáad a listához.
     * @param tabla   A tanulando tábla neve
     * @return        Visszaadja a lekérdezett rekordokból készített Sor típusú listát
     */
    public static ArrayList<Sor> tanulandotLekerdez(String tabla) {
        ArrayList<Sor> rekordok = new ArrayList<>();
        String query = "SELECT * FROM " + tabla + " WHERE kikerdezes_ideje<=" + System.currentTimeMillis();
        try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
            PreparedStatement ps = kapcs.prepareStatement(query)) {
            
            ResultSet eredmeny = ps.executeQuery();
            while (eredmeny.next()) {
                rekordok.add(new Sor(
                                     eredmeny.getString("nevelo"),
                                     eredmeny.getString("szavak"),
                                     eredmeny.getString("mondatok"),
                                     eredmeny.getString("forditas")));
            }
            return rekordok;
            
        } catch (SQLException e) {
            hiba(uzenetek.get("hiba"),e.getMessage());
            return rekordok;
        }
    }
    
    /**
     * Az adott tanulandó szónak beállítja a következő kikérdezési idejét.
     * @param tabla             A tanulandó tábla neve
     * @param szo               A tanulandó szó ahol a módosítást végre kell hajtani
     * @param kikerdezesIdeje   A következő kikérdezés ideje milliszekundumban
     */
    public static void frissitKikerdezes(String tabla, String szo, long kikerdezesIdeje) {
        String update = "UPDATE " + tabla + " SET kikerdezes_ideje= ? WHERE szavak= ?;";
        try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
             PreparedStatement ps = kapcs.prepareStatement(update)) {
            
                ps.setLong(1, kikerdezesIdeje);
                ps.setString(2, szo);
                ps.executeUpdate();
                
        } catch (SQLException e) {
            hiba(uzenetek.get("hiba"),e.getMessage());
        }
    }
}