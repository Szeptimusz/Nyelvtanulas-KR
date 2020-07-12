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
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Window;
import static nyelvtanulas_kr_szakdolgozat.DB.adatbazisUtvonal;
import static nyelvtanulas_kr_szakdolgozat.FoablakController.uzenetek;
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
public class AnkiController implements Initializable, Feliratok {

    @FXML
    private Label             lblKeremValasszaKi;
    @FXML
    private Button            btnKartyakElkeszitese;
    @FXML
    private Button            btnMegse;
    @FXML
    private ComboBox<String>  cbxNyelvek;
    
    /**
     * Ha van kiválasztott nyelv, akkor az ahhoz tartozó tanulandó táblában megkeresi azokat a rekordokat, amikből még nem volt
     * Anki-import készítve. Elkészíti az Anki-import fájlt és átírja az érintett szavak ANKI állapotát 1-re a tanulandó táblában.
     * Ezzel jelzi azt, hogy az adott rekordból készült már Anki-import.
     */
    @FXML
    public void kartyatKeszit() {
        if (igennem(uzenetek.get("ankiimportkeszites"),uzenetek.get("akarankiimportotkesziteni"))) {
            ArrayList<String> szavak = new ArrayList<>();
            String forrasNyelvKod = FoablakController.nyelvekKodja.get(cbxNyelvek.getValue());
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
                            hiba(uzenetek.get("hiba"),uzenetek.get("hibaskartyakeszites"));
                    }
                } catch (SQLException e) {
                    hiba(uzenetek.get("hiba"),e.getMessage());
                }
                // Ha sikeres volt az ANKI kártya készítés, akkor a táblában átírja az ANKI mezőt 0-ról 1-re.
                if (!szavak.isEmpty()) {
                    DB.ankitModositAdatbazisban(forrasNyelvKod + "_tanulando",szavak);
                    tajekoztat(uzenetek.get("kártyakesziteseredmeny"), 
                        uzenetek.get("kartyakelkeszitve") + forrasNyelvKod + uzenetek.get("fajlba"));
                    cbxNyelvek.getScene().getWindow().hide();
                } else {
                    figyelmeztet(uzenetek.get("figyelmeztet"), uzenetek.get("nincstanulando"));
                }
            } else {
                figyelmeztet(uzenetek.get("figyelmeztet"), uzenetek.get("adjameganyelvet"));
            }
        }
    }

    /**
     * A kapott szóból, mondatból és fordításból olyan .txt fájlt készít, amit az ANKI szótanuló program be tud importálni 
     * és szókártyákat tud belőle készíteni. A fájlba írás FileOutputStream-el, UTF-8 kódolással történik (az Anki program
     * csak UTF-8-at fogad el importálásnál). A kiírásnál a szókártya két oldalát a \t - tabulátor jelzi, a szókártyákat \n-
     * új sor választja el. A példamondatnak elkészíti egy olyan verzióját, ahol az idegen szó helye ki van pontozva.
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
                           + forditas + "<br><br>" + (mondat + " ").replaceAll("[^\\w](?i)" + szo + "[^\\w]", 
                                                                               " " + new String(new char[szo.length()]).replace("\0", ".") + " ") + "\n");
                return true;
                
        } catch(IOException e) {
            hiba(uzenetek.get("hiba"),e.getMessage());
            return false;
        }
    }
    
    /**
     * A nyelv kiválasztásához beállítja a legördülő lista nyelveit, beállítja
     * az ablak feliratait a megfelelő nyelven.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        cbxNyelvek.getItems().clear();
        cbxNyelvek.getItems().addAll(FoablakController.nyelvek);
        
        lblKeremValasszaKi.setText(FoablakController.ankiFelirat[0]);
        btnKartyakElkeszitese.setText(FoablakController.ankiFelirat[1]);
        btnMegse.setText(FoablakController.ankiFelirat[2]);
        
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
