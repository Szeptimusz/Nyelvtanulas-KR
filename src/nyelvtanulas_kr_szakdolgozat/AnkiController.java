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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.stage.Window;
import static nyelvtanulas_kr_szakdolgozat.FoablakController.adatbazisUtvonal;

public class AnkiController implements Initializable {

    @FXML
    private ComboBox<String> cbxNyelvek;
    static HashMap<String, String> nyelvekKodja = new HashMap<>();
    
    /**
     * Ha van kiválasztott nyelv, akkor az ahhoz tartozó tanulandó táblában megkeresi azokat a rekordokat, amikből még nem volt
     * txt import készítve. Megcsinálja a fájlírást és módosítja a tanulandó táblában az ANKI állapotot 1-re.
     */
    @FXML
    void kartyatKeszit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ANKI kártya készítés");
        alert.setHeaderText(null);
        alert.setContentText("Valóban szeretne minden új tanulandó szóból ANKI szókártyát készíteni?");

        ArrayList<String> szavak = new ArrayList<>();
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            String forrasNyelvKod = nyelvekKodja.get(cbxNyelvek.getValue());
            if (forrasNyelvKod != null) {
                String query = "SELECT szavak, mondatok, forditas FROM " + forrasNyelvKod + "_tanulando WHERE ANKI == 0";
                try (Connection kapcs = DriverManager.getConnection(adatbazisUtvonal);
                    PreparedStatement ps = kapcs.prepareStatement(query)) {
                    ResultSet eredmeny = ps.executeQuery();
                    while (eredmeny.next()) {
                        String szo = eredmeny.getString("szavak");
                        String mondat = eredmeny.getString("mondatok");
                        String forditas = eredmeny.getString("forditas");
                        if (keszit(szo, mondat, forditas, forrasNyelvKod)) {
                            szavak.add(szo);
                        } else {
                            System.out.println("Hiba történt a kártya készítése során!");
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("Nem sikerült az adatbázis-lekérdezés!");
                    System.out.println(e.getMessage());
                }
                // Ha sikeres volt az ANKI kártya készítés, akkor a táblában átírja az ANKI mezőt 0-ról 1-re.
                if (!szavak.isEmpty()) {
                    DB.ankitModositAdatbazisban(forrasNyelvKod + "_tanulando",szavak);
                    FoablakController.tajekoztat("Kártya készítés eredmény", 
                        "A kártyák sikeresen elkészítve a: " + forrasNyelvKod + " _ankiimport fájlba!");
                    System.out.println("ANKI kártya készítés sikeres!");
                } else {
                    FoablakController.figyelmeztet("Figyelem!", "Nincsen tanulandó szó amiből szókártya készíthető!");
                }
            } else {
                FoablakController.figyelmeztet("Figyelem!", "Kérem adja meg a nyelvet!");
            }
        } else {
            alert.hide();
        }
    }

    /**
     * A kapott szóból, mondatból és fordításból olyan .txt fájlt készít, amit az ANKI szótanuló program be tud importálni 
     * és szókártyákat tud belőle készíteni.
     * @param szo:              A tanulandó szó.
     * @param mondat:           A szóhoz tartozó példamondat.
     * @param forditas:         Az általunk korábban megadott fordítása a szónak
     * @param forrasNyelvKod    A legördülő listából kiválasztott nyelv rövidített változata
     * @return :                Ha sikerült a fájlba írás igazad ad vissza, ha nem akkor false-t.
     */
    public boolean keszit(String szo, String mondat, String forditas, String forrasNyelvKod) {
        // Kiírás FileOutputStream-mel, mert így megadható az utf-8 kódolás (az ANKI program csak ezt tudja beimportálni)
        try (OutputStreamWriter writer =
             new OutputStreamWriter(new FileOutputStream(forrasNyelvKod + "_ankiimport.txt",true), StandardCharsets.UTF_8)) {
            
            // A mondatban a szó előfordulásainak megkeresése, pontokkal helyessítése és így lyukas szöveg gyártása.
            String lyukasMondat = "";
            String [] szavak = mondat.toLowerCase().split(" ");
            for (int i = 0; i < szavak.length; i++) {
                if (szavak[i].equals(szo)) {
                    int szoHossza = szavak[i].length();
                    String lyuk = "";
                    for (int j = 0; j < szoHossza; j++) {
                        lyuk = lyuk + ".";
                    }
                    szavak[i] = lyuk;
                }
                lyukasMondat += szavak[i] + " ";
            }
            // Lyukas mondat első betűjének nagybetűssé alakítása
            lyukasMondat = lyukasMondat.substring(0, 1).toUpperCase() + lyukasMondat.substring(1);
            // A szó,mondat,lyukasmondat fájlba írása - az ANKI importálási szabályainak megfelelően
            writer.write(szo + "<br><br>" + mondat + "\t" + forditas + "<br><br>" + lyukasMondat + "\n");
            return true;
        } catch(IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Legördülő lista nyelveinek beállítása
        FoablakController.nyelvekBeallitasa(cbxNyelvek, nyelvekKodja);
    }
    
    /**
     * Mégse-gombra kattintva az ablak bezárása
     */
    @FXML
    void megse() {
        Window ablak = cbxNyelvek.getScene().getWindow();
        ablak.hide();
    }
    
}
