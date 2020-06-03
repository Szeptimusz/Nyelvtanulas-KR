package nyelvtanulas_kr_szakdolgozat;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.stage.Window;
import static nyelvtanulas_kr_szakdolgozat.DB.adatbazisUtvonal;
import static panel.Panel.figyelmeztet;
import static panel.Panel.hiba;
import static panel.Panel.igennem;
import static panel.Panel.tajekoztat;

/**
 * Az Anki ablakot kezelő osztály. Itt történik meg a kiválasztott nyelv
 * tanulandó szavainak kiexportálása olyan fájlként, amit az Anki szókártya program be tud
 * importálni.
 * @author Kremmer Róbert
 */
public class AnkiController implements Initializable {

    @FXML
    private ComboBox<String> cbxNyelvek;
    static HashMap<String, String> nyelvekKodja = new HashMap<>();
    
    /**
     * Ha van kiválasztott nyelv, akkor az ahhoz tartozó tanulandó táblában megkeresi azokat a rekordokat, amikből még nem volt
     * Anki-import készítve. Elkészíti az Anki-import fájlt és átírja az érintett szavak ANKI állapotát 1-re a tanulandó táblában.
     * Ezzel jelzi azt, hogy az adott rekordból készült már Anki-import.
     */
    @FXML
    public void kartyatKeszit() {
        if (igennem("ANKI kártya készítés","Valóban szeretne minden új tanulandó szóból ANKI-importot készíteni?")) {
            ArrayList<String> szavak = new ArrayList<>();
            String forrasNyelvKod = nyelvekKodja.get(cbxNyelvek.getValue());
            if (forrasNyelvKod != null) {
                String query = "SELECT nevelo, szavak, mondatok, forditas FROM " + forrasNyelvKod + "_tanulando WHERE ANKI == 0";
                try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
                    PreparedStatement ps = kapcs.prepareStatement(query)) {
                    ResultSet eredmeny = ps.executeQuery();
                    while (eredmeny.next()) {
                        String nevelo = eredmeny.getString("nevelo");
                        if (nevelo.length() > 0) nevelo += " ";
                        
                        String szo = eredmeny.getString("szavak");
                        String mondat = eredmeny.getString("mondatok");
                        String forditas = eredmeny.getString("forditas");
                        if (keszit(nevelo, szo, mondat, forditas, forrasNyelvKod))
                            szavak.add(szo);
                        else 
                            hiba("Hiba!","Hiba történt a kártya készítése során!");
                    }
                } catch (SQLException e) {
                    hiba("Hiba!",e.getMessage());
                }
                // Ha sikeres volt az ANKI kártya készítés, akkor a táblában átírja az ANKI mezőt 0-ról 1-re.
                if (!szavak.isEmpty()) {
                    DB.ankitModositAdatbazisban(forrasNyelvKod + "_tanulando",szavak);
                    tajekoztat("Kártya készítés eredmény", 
                        "A kártyák sikeresen elkészítve a(z):  " + forrasNyelvKod + " _ankiimport fájlba!");
                } else {
                    figyelmeztet("Figyelem!", "Nincsen tanulandó szó amiből szókártya készíthető!");
                }
            } else {
                figyelmeztet("Figyelem!", "Kérem adja meg a nyelvet!");
            }
        }
    }

    /**
     * A kapott szóból, mondatból és fordításból olyan .txt fájlt készít, amit az ANKI szótanuló program be tud importálni 
     * és szókártyákat tud belőle készíteni. A fájlba írás FileOutputStream-el, UTF-8 kódolással történik (az Anki program
     * csak UTF-8-at fogad el importálásnál). A kiírásnál a szókártya két oldalát a \t - tabulátor jelzi, a szókártyákat \n-
     * új sor választja el.
     * @param nevelo           A szó névelője
     * @param szo              A tanulandó szó.
     * @param mondat           A szóhoz tartozó példamondat.
     * @param forditas         Az általunk korábban megadott fordítása a szónak
     * @param forrasNyelvKod   A legördülő listából kiválasztott nyelv rövidített változata
     * @return                 Ha sikerült a fájlba írás igazad ad vissza, ha nem akkor false-t.
     */
    public boolean keszit(String nevelo, String szo, String mondat, String forditas, String forrasNyelvKod) {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(forrasNyelvKod + "_ankiimport.txt",true), StandardCharsets.UTF_8)) {
            
                writer.write(nevelo + szo + "<br><br>" + mondat + "\t" 
                           + forditas + "<br><br>" + (mondat + " ").replaceAll("[^\\w]" + szo + "[^\\w]", 
                                                                               " " + new String(new char[szo.length()]).replace("\0", ".") + " ") + "\n");
                return true;
                
        } catch(IOException e) {
            hiba("Hiba!",e.getMessage());
            return false;
        }
    }
    
    /** MEGTARTJA AZ EREDETI BETŰMÉRETEKET
     * A mondatban a szó előfordulásainak megkeresése, pontokkal helyettesítése és így lyukas szöveg gyártása.
     * A kapott mondaton végigmegy és ha talál keresett szót, akkor annyi ponttal helyettesíti, amennyi a szó hossza.
     * A mondaton való végighaladás során egy új String-ben szavanként felépíti a lyukasmondatot.
     * @param szo    A pontokkal helyettesítendő szó
     * @param mondat A helyettesítendő szót tartalmazó mondat
     * @return       Visszaadja a készített lyukasmondatot
     */
    /*
    public String lyukasMondatotKeszit(String szo, String mondat) {
        String lyukasMondat = "";
        String [] szavak = mondat.toLowerCase().split(" |\\, |\\,|\\; |\\;|\\—");
        String [] szavak2 = mondat.split(" |\\, |\\,|\\; |\\;|\\—");
        for (int i = 0; i < szavak.length; i++) {
            if (szavak[i].equals(szo.toLowerCase())) {
                String lyuk = "";
                for (int j = 0; j < szo.length(); j++) {
                    lyuk = lyuk + ".";
                }
                szavak[i] = lyuk;
                lyukasMondat += szavak[i] + " ";
            } else {
                lyukasMondat += szavak2[i] + " ";
            }
            
        }
        // Lyukas mondat első betűjének nagybetűssé alakítása
        lyukasMondat = lyukasMondat.substring(0, 1).toUpperCase() + lyukasMondat.substring(1);
        return lyukasMondat;
    }
    */
    
    /**
     * A nyelv kiválasztásához beállítja a legördülő lista nyelveit és tárolja azok kódját.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Legördülő lista nyelveinek beállítása
        FoablakController.nyelvekBeallitasa(cbxNyelvek, nyelvekKodja);
    }
    
    /**
     * Mégse-gombra kattintva az ablak bezárása
     */
    @FXML
    public void megse() {
        Window ablak = cbxNyelvek.getScene().getWindow();
        ablak.hide();
    }
    
}
