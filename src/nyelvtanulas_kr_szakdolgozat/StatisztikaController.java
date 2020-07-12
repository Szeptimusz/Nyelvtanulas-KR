package nyelvtanulas_kr_szakdolgozat;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 * A statisztika ablakot kezelő osztály. Megjeleníti az adott nyelv tábláiban 
 * tárolt szavak számát különböző szempontok alapján.
 * @author Kremmer Róbert
 */
public class StatisztikaController implements Initializable, Feliratok {

    static HashMap<String, String>  nyelvekKodja = new HashMap<>();
    @FXML
    private ComboBox<String>        cbxNyelvek;
    @FXML
    private Label lblKeremValasszonKi;
    @FXML
    private Label lblIsmertSzavak;
    @FXML
    private Label lblFigyelmenKivulHagyott;
    @FXML
    private Label lblTanulandoSzavak;
    @FXML
    private Label lblExportaltSzavak;
    @FXML
    private Label lblNemExportaltSzavak;
    @FXML
    private Label lblStatisztika;
    @FXML
    private Label lblIsmertekSzama;
    @FXML
    private Label lblIgnoraltakSzama;
    @FXML
    private Label lblTanulandoOsszes;
    @FXML
    private Label lblImportaltTanulando;
    @FXML
    private Label lblNemImportaltTanulando;
    @FXML
    private Label lblOsszesSzo;
    @FXML
    private Label lblOsszes;
   
    /**
     * Beállítja a legördülő lista nyelveit és tárolja azok kódját.
     * A legördülő listához rendelt listener figyeli a kiválasztott nyelvet és mindig az aktuálisan
     * kiválasztott nyelv tábláinak adatait jeleníti meg az ablak címkéiben. A címkékben megjelenített adatok:
     * az összes szó mennyisége, az ismert szavak száma, a figyelmen kívül hagyott szavak száma, az összes tanulandó
     * szó mennyisége, az importált tanulandó szavak száma és a nem importált tanulandó szavak száma.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Legördülő lista nyelveinek beállítása
        FoablakController.nyelvekBeallitasa(cbxNyelvek, nyelvekKodja, Feliratok.NYELVEK_MAGYAR);
        
        // A legördülő listában kiválasztott nyelv tábláiból statisztikát készít
        cbxNyelvek.getSelectionModel().selectedItemProperty().addListener(
            (v, regi, uj) -> {
                String nyelvKodja = nyelvekKodja.get(uj);

                int ismert = DB.statisztikatLekerdez(nyelvKodja + "_szavak","ismert");
                int ignoralt = DB.statisztikatLekerdez(nyelvKodja + "_szavak","ignoralt");
                int exportalt = DB.statisztikatTanulandobolLekerdez(nyelvKodja + "_tanulando",1);
                int nemExportalt = DB.statisztikatTanulandobolLekerdez(nyelvKodja + "_tanulando",0);
                
                lblOsszes.setText((ismert + ignoralt + exportalt + nemExportalt) + "");
                lblIsmertekSzama.setText(ismert + "");
                lblIgnoraltakSzama.setText(ignoralt + "");
                lblTanulandoOsszes.setText((exportalt + nemExportalt) + "");
                lblImportaltTanulando.setText(exportalt + "");
                lblNemImportaltTanulando.setText(nemExportalt + "");
            });
        
        
        
        String [] feliratok;
        
        switch (FoablakController.feluletNyelve) {
            case "magyar" :
                feliratok = STATISZTIKA_MAGYARFELIRATOK;
                FoablakController.nyelvekBeallitasa(cbxNyelvek, nyelvekKodja, Feliratok.NYELVEK_MAGYAR);
                break;
            case "english" :
                feliratok = STATISZTIKA_ANGOLFELIRATOK;
                FoablakController.nyelvekBeallitasa(cbxNyelvek, nyelvekKodja, Feliratok.NYELVEK_ANGOL);
                break;
            default :
                feliratok = STATISZTIKA_MAGYARFELIRATOK;
                FoablakController.nyelvekBeallitasa(cbxNyelvek, nyelvekKodja, Feliratok.NYELVEK_MAGYAR);
                break;
        }
        
        lblStatisztika.setText(feliratok[0]);
        lblKeremValasszonKi.setText(feliratok[1]);
        lblOsszesSzo.setText(feliratok[2]);
        lblIsmertSzavak.setText(feliratok[3]);
        lblFigyelmenKivulHagyott.setText(feliratok[4]);
        lblTanulandoSzavak.setText(feliratok[5]);
        lblExportaltSzavak.setText(feliratok[6]);
        lblNemExportaltSzavak.setText(feliratok[7]);
        
    }
    
    
    
}
