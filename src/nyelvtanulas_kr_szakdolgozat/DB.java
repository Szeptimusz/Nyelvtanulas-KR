package nyelvtanulas_kr_szakdolgozat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
     * A kapott lista szavait beírja mint görgetett szó. Az autoCommit letiltásával és a Batch használatával jelentősen gyorsabban végezhetők
     * az adatbázis-műveletek.
     * @param szavak   Adatbázisba írandó szólista.
     * @param tabla    A kapott tábla neve.
     */
    public static void gorgetettSzavakatBeirAdatbazisba(ArrayList<String> szavak, String tabla) {
        String into = "INSERT INTO " + tabla + " VALUES (?,?)";
        try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
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
            System.out.println("Nem sikerült a görgetett szó adatbázisba írása!");
            hiba("Hiba!",e.getMessage());
        }
    }
    
    /**
     * A kapott lista szavait kitörli a kapott táblából. AutoCommit letiltása és Batch használata.
     * @param szavak    Adatbázisból törledő szólista
     * @param tabla     A szavak törlése ebből a táblából történjen
     */
    public static void szavakatTorolAdatbazisbol(ArrayList<String> szavak, String tabla) {
        String delete = "DELETE FROM " + tabla + " WHERE szavak= ?;";
        try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
                PreparedStatement ps = kapcs.prepareStatement(delete)) {
                kapcs.setAutoCommit(false);
            
                int count = 0;
                for (String szo: szavak) {
                    ps.setString(1, szo);
                    ps.addBatch();
                    count++;
                    if (count == 1000) {
                        ps.executeBatch();
                        System.out.println("Adatbázisból törlés sikeres!");
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
            hiba("Hiba!",e.getMessage());
        }
    }
    
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
            /* Ha egy szót elmentett görgetettnek, majd utána lefuttatjuk úgy, hogy ne vegye figyelembe a görgetetteket és a szó megjelenik
               a táblázatban, akkor az ismert és ignorált gombokkal elmentve primary key hibát dob (19-es a hibakódja),
               mert már benne van a _szavak táblában mint görgetett. Ilyenkor az adott szót töröljük és újra hozzáadjuk a megfelelő állapot-tal.
            */
            if (e.getErrorCode() == 19) {
                szotTorolAdatbazisbol(tabla, szo);
                szotBeirAdatbazisba(tabla, szo, allapot);
                System.out.println("Szó felülbírálva!");
            } else {
                System.out.println("Nem sikerült a " + tabla + "-táblába" + " írás!");
                hiba("Hiba!",e.getMessage());
            }
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
        String into = "INSERT INTO " + tabla + " (szavak, mondatok, forditas, ANKI) VALUES (?,?,?,?)";
        try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
                PreparedStatement ps = kapcs.prepareStatement(into)) {
                ps.setString(1, szo);
                ps.setString(2, mondat);
                ps.setString(3, forditas);
                ps.setInt(4, anki);
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
       jelezve, hogy a táblázat megjelenítése előtt törölni kell a listából. Ha a görgetettszavakon megy végig és a szó 
       létezik a listában, akkor 1-gyel növeli a gyakoriságát a listában (így biztos, hogy legalább kétszer előfordul globálisan) és 
       törli a táblából a szót.
     * @param tabla         A kapott tábla neve
     * @param data          A kapott lista a feldolgozott szavakkal,mondatokkal
     * @param szavak_indexe A kapott HashMap, ebben tároljuk azt, hogy az adott szó a listában hányadik indexen van
     * @param miket         Az összevetni kívánt szó állapota (ismert,tanulando,ignoralt,gorgetett)
     */
    public static void adatbazistListavalOsszevet(String tabla, ObservableList<Sor> data, HashMap<String, Integer> szavak_indexe,
                                  String miket) {
        String feltetel = "";
        if (miket.equals("ismertignoralt")) {
            feltetel = " WHERE allapot='ismert' OR allapot='ignoralt'";
        } else if (miket.equals("gorgetett")) {
            feltetel = " WHERE allapot='gorgetett'";
        }
        
        String query = "SELECT szavak FROM " + tabla + feltetel;
        ArrayList<String> szavak = new ArrayList();
        try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
            PreparedStatement ps = kapcs.prepareStatement(query)) {
            ResultSet eredmeny = ps.executeQuery();
            
            while (eredmeny.next()) {
                String szo = eredmeny.getString("szavak");
                if (szavak_indexe.get(szo) != null) {
                    // ha nem görgetett a szó
                    if (!miket.equals("gorgetett")) {
                        data.get(szavak_indexe.get(szo)).setSzo("torlendo");
                        szavak_indexe.put(szo, null);
                    // ha görgetett a szó
                    } else {
                        int gyak = data.get(szavak_indexe.get(szo)).getGyak();
                        data.get(szavak_indexe.get(szo)).setGyak(++gyak);
                        szavak.add(szo);
                    }
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Nem sikerült az adatbázis-lekérdezés!");
            hiba("Hiba!",e.getMessage());
        }
        
        // Görgetett szó előfordult a szövegben, ezért töröljük az adatbázisból
        if (!szavak.isEmpty()) {
            szavakatTorolAdatbazisbol(szavak, tabla);
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
                       + "mondatok TEXT NOT NULL, kikerdezes_ideje DATE, forditas VARCHAR(100), ANKI INT NOT NULL);";
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
     * @param allapot:  A kapott állapot (ismert, ignoralt, gorgetett)
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
}