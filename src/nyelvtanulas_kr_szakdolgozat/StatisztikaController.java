package nyelvtanulas_kr_szakdolgozat;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class StatisztikaController implements Initializable {

    static HashMap<String, String> nyelvekKodja = new HashMap<>();
    @FXML
    private ComboBox<String> cbxNyelvek;
    @FXML
    private Label lblIsmertekSzama;
    @FXML
    private Label lblIgnoraltakSzama;
    @FXML
    private Label lblGorgetettekSzama;
    @FXML
    private Label lblTanulandoOsszes;
    @FXML
    private Label lblImportaltTanulando;
    @FXML
    private Label lblNemImportaltTanulando;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Legördülő lista nyelveinek beállítása
        FoablakController.nyelvekBeallitasa(cbxNyelvek, nyelvekKodja);
        
        // A legördülő listában kiválasztott nyelv tábláiból statisztikát készít
        cbxNyelvek.getSelectionModel().selectedItemProperty().addListener(
            (v, regi, uj) -> {
                String nyelvKodja = nyelvekKodja.get(uj);
                lblIsmertekSzama.setText("" + DB.statisztikatLekerdez(nyelvKodja + "_szavak","ismert"));
                lblIgnoraltakSzama.setText("" + DB.statisztikatLekerdez(nyelvKodja + "_szavak","ignoralt"));
                lblGorgetettekSzama.setText("" + DB.statisztikatLekerdez(nyelvKodja + "_szavak","gorgetett"));
                
                int importalt = DB.statisztikatTanulandobolLekerdez(nyelvKodja + "_tanulando",1);
                int nemImportalt = DB.statisztikatTanulandobolLekerdez(nyelvKodja + "_tanulando",0);
                
                lblTanulandoOsszes.setText((importalt + nemImportalt) + "");
                lblImportaltTanulando.setText(importalt + "");
                lblNemImportaltTanulando.setText(nemImportalt + "");
            });
    }    
}
