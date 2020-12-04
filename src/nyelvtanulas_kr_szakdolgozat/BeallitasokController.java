package nyelvtanulas_kr_szakdolgozat;

import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import static nyelvtanulas_kr_szakdolgozat.FoablakController.uzenetek;
import static panel.Panel.hiba;

/**
 *
 * @author Kremmer Róbert
 */
public class BeallitasokController implements Initializable {
    
    @FXML
    private Label            lblCelnyelv;
    @FXML
    private Label            lblBeallitasok;
    @FXML
    private Label            lblSorokSzama;
    @FXML
    private Button           btnMentes;
    @FXML
    private Button           btnMegse;
    @FXML
    private TextField        txtSorokSzama;
    @FXML
    private ComboBox<String> cbxNyelvek;
    @FXML
    private Label            lblFeluletNyelve;
    @FXML
    private ComboBox<String> cbxFeluletNyelve;
    
    
    @FXML
    void ment() {
        String feluletNyelve = cbxFeluletNyelve.getValue();
        String celNyelvKod   = FoablakController.nyelvekKodja.get(cbxNyelvek.getValue());
        
        int sorokSzama;
        try {
            sorokSzama = Integer.parseInt(txtSorokSzama.getText());
        } catch (NumberFormatException e) {
            hiba(uzenetek.get("hiba"),uzenetek.get("nemszam"));
            return;
        }
        
        if (feluletNyelve == null || celNyelvKod == null) {
            hiba(uzenetek.get("hiba"),uzenetek.get("adjonmegmindenadatot"));
            return;
        }
        
        FoablakController.celNyelvKod = celNyelvKod;
        FoablakController.beolvasottSorokSzama = sorokSzama;
        
        try (PrintWriter ki = new PrintWriter("settings.txt")) {
            
            ki.println(feluletNyelve);
            ki.println(celNyelvKod);
            ki.println(sorokSzama);
            
        } catch (Exception e) {
            hiba(uzenetek.get("hiba"), e.getMessage());
            return;
        }
        
        FoablakController.feluletNyelve = feluletNyelve;
        
        Window ablak = cbxNyelvek.getScene().getWindow();
        ablak.hide();
    }
    
    @FXML
    void megse() {
        Window ablak = cbxNyelvek.getScene().getWindow();
        ablak.hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        lblBeallitasok.setText(FoablakController.beallitasokFelirat[0]);
        lblFeluletNyelve.setText(FoablakController.beallitasokFelirat[1]);
        lblCelnyelv.setText(FoablakController.beallitasokFelirat[2]);
        lblSorokSzama.setText(FoablakController.beallitasokFelirat[3]);
        btnMentes.setText(FoablakController.beallitasokFelirat[4]);
        btnMegse.setText(FoablakController.beallitasokFelirat[5]);
        
        cbxFeluletNyelve.getItems().addAll(FoablakController.nyelvek);
        cbxNyelvek.getItems().addAll(FoablakController.nyelvek);
        
        
        // KÍSÉRLETI KÓD!!!
        //cbxFeluletNyelve.setValue(FoablakController.feluletNyelve.substring(0,1) + FoablakController.feluletNyelve.substring(1));
        //cbxNyelvek.setValue(FoablakController.celNyelvKod);
        
        
        
        txtSorokSzama.setText(FoablakController.beolvasottSorokSzama + "");
    }
    
}
