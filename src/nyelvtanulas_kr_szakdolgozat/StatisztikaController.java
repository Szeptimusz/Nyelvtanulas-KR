package nyelvtanulas_kr_szakdolgozat;

import java.net.URL;
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

    @FXML
    private ComboBox<String>  cbxNyelvek;
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
     * Beállítja a legördülő lista nyelveit.
     * A legördülő listához rendelt listener figyeli a kiválasztott nyelvet és mindig az aktuálisan
     * kiválasztott nyelv tábláinak adatait jeleníti meg az ablak címkéiben. A címkékben megjelenített adatok:
     * az összes szó mennyisége, az ismert szavak száma, a figyelmen kívül hagyott szavak száma, az összes tanulandó
     * szó mennyisége, az importált tanulandó szavak száma és a nem importált tanulandó szavak száma.
     * Beállítja az ablak feliratait a megfelelő nyelven.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // A legördülő listában kiválasztott nyelv tábláiból statisztikát készít
        cbxNyelvek.getSelectionModel().selectedItemProperty().addListener(
            (v, regi, uj) -> {
                String nyelvKodja = FoablakController.nyelvekKodja.get(uj);

                int ismert = DB.statisztikatLekerdez(nyelvKodja + "_szavak");
                int exportalt = DB.statisztikatTanulandobolLekerdez(nyelvKodja + "_tanulando",1);
                int nemExportalt = DB.statisztikatTanulandobolLekerdez(nyelvKodja + "_tanulando",0);
                
                lblOsszes.setText((ismert + exportalt + nemExportalt) + "");
                lblIsmertekSzama.setText(ismert + "");
                lblTanulandoOsszes.setText((exportalt + nemExportalt) + "");
                lblImportaltTanulando.setText(exportalt + "");
                lblNemImportaltTanulando.setText(nemExportalt + "");
            });
        
        cbxNyelvek.getItems().clear();
        cbxNyelvek.getItems().addAll(FoablakController.nyelvek);
        
        lblStatisztika.setText(FoablakController.statisztikaFelirat[0]);
        lblKeremValasszonKi.setText(FoablakController.statisztikaFelirat[1]);
        lblOsszesSzo.setText(FoablakController.statisztikaFelirat[2]);
        lblIsmertSzavak.setText(FoablakController.statisztikaFelirat[3]);
        lblTanulandoSzavak.setText(FoablakController.statisztikaFelirat[5]);
        lblExportaltSzavak.setText(FoablakController.statisztikaFelirat[6]);
        lblNemExportaltSzavak.setText(FoablakController.statisztikaFelirat[7]);
    }
    
}
