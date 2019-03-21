package nyelvtanulas_kr_szakdolgozat;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 *
 * @author Kremmer Róbert
 */
public class StatisztikaController implements Initializable {

    static HashMap<String, String> nyelvekKodja = new HashMap<>();
    @FXML
    private ComboBox<String> cbxNyelvek;
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
    private Label lblOsszes;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Legördülő lista nyelveinek beállítása
        FoablakController.nyelvekBeallitasa(cbxNyelvek, nyelvekKodja);
        
        // A legördülő listában kiválasztott nyelv tábláiból statisztikát készít
        cbxNyelvek.getSelectionModel().selectedItemProperty().addListener(
            (v, regi, uj) -> {
                String nyelvKodja = nyelvekKodja.get(uj);

                int ismert = DB.statisztikatLekerdez(nyelvKodja + "_szavak","ismert");
                int ignoralt = DB.statisztikatLekerdez(nyelvKodja + "_szavak","ignoralt");
                int importalt = DB.statisztikatTanulandobolLekerdez(nyelvKodja + "_tanulando",1);
                int nemImportalt = DB.statisztikatTanulandobolLekerdez(nyelvKodja + "_tanulando",0);
                
                lblOsszes.setText((ismert + ignoralt + importalt + nemImportalt) + "");
                lblIsmertekSzama.setText(ismert + "");
                lblIgnoraltakSzama.setText(ignoralt + "");
                lblTanulandoOsszes.setText((importalt + nemImportalt) + "");
                lblImportaltTanulando.setText(importalt + "");
                lblNemImportaltTanulando.setText(nemImportalt + "");
            });
    }    
}
