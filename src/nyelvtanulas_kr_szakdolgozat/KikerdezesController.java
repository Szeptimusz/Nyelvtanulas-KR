package nyelvtanulas_kr_szakdolgozat;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import static panel.Panel.figyelmeztet;
import static panel.Panel.tajekoztat;

/**
 *
 * @author Kremmer Róbert
 */
public class KikerdezesController implements Initializable {

    @FXML
    private ComboBox<String> cbxNyelvek;
    static HashMap<String, String> nyelvekKodja = new HashMap<>();
    ArrayList<Sor> rekordok = new ArrayList<>();
    int index;
    String forrasNyelvKod;
    
    @FXML
    private Button btnKikerdezestElindit;
    @FXML
    private Label lblSzo;
    @FXML
    private Label lblMondat;
    @FXML
    private Label lblForditas;
    @FXML
    private Button btnUjra;
    @FXML
    private Button btnNehez;
    @FXML
    private Button btnJo;
    @FXML
    private Button btnKonnyu;
    @FXML
    private Button btnValasz;

    // A nyelv kiválasztása után elindítja az ahhoz a nyelvhez tartozó aktuálisan
    // tanulandó szókártyák kikérdezését
    @FXML
    void kikerdez() {
        
        if (cbxNyelvek.getValue() == null) {
            figyelmeztet("Figyelem!","Kérem válassza ki, hogy melyik nyelv szókártyáit szeretné használni");
        } else {
            forrasNyelvKod = nyelvekKodja.get(cbxNyelvek.getValue());
            rekordok = DB.tanulandotLekerdez(forrasNyelvKod + "_tanulando");
            if (rekordok.isEmpty()) {
                figyelmeztet("Figyelem","Nincsen aktuálisan tanulandó szó!");
                btnValasz.setDisable(true);
            } else {
                index = 0;
                szotMondatotBeallit(index);
                btnKikerdezestElindit.setDisable(true);
                cbxNyelvek.setDisable(true);
                btnValasz.setDisable(false);
            }
        }
        
    }
    
    @FXML
    void valasz() {
        lblForditas.setText(rekordok.get(index).getForditas());
        gombokatTilt(false);
    }
    
    @FXML
    void ujra() {
        // Ha nem tudjuk a szókártyát, akkor addig ismétli a kikérdezést, amíg
        // nem kíttintunk rá valamelyik másik értékelő gombra
        rekordok.add(rekordok.get(index));
        index++;
        lblForditas.setText("");
        szotMondatotBeallit(index);
        gombokatTilt(true);
    }
    
    @FXML
    void nehez() {
        // Ha a szókártya nehéz volt, akkor 2 nap múlva kérdezi ki újra
        DB.frissitKikerdezes(forrasNyelvKod + "_tanulando", rekordok.get(index).getSzo(), 
                (rekordok.get(index).getKikerdezes_ideje() + 2*24*3600*1000));
        index++;
        lblForditas.setText("");
        szotMondatotBeallit(index);
        gombokatTilt(true);
    }
    
    @FXML
    void konnyu() {
        // Ha a szókártya könnyű volt, akkor 10 nap múlva kérdezi ki újra
        DB.frissitKikerdezes(forrasNyelvKod + "_tanulando", rekordok.get(index).getSzo(), 
                (rekordok.get(index).getKikerdezes_ideje() + 10*24*3600*1000));
        index++;
        lblForditas.setText("");
        szotMondatotBeallit(index);
        gombokatTilt(true);
    }

    @FXML
    void jo() {
        // Ha a szókártya jó volt, akkor 5 nap múlva kérdezi ki újra
        DB.frissitKikerdezes(forrasNyelvKod + "_tanulando", rekordok.get(index).getSzo(), 
                (rekordok.get(index).getKikerdezes_ideje() + 5*24*3600*1000));
        index++;
        lblForditas.setText("");
        szotMondatotBeallit(index);
        gombokatTilt(true);
    }
    
    private void szotMondatotBeallit(int index) {
        if (index < rekordok.size()) {
            lblSzo.setText(rekordok.get(index).getSzo());
            lblMondat.setText(rekordok.get(index).getMondat());
        } else {
            tajekoztat("Figyelem","Véget ért a kikérdezés!");
            btnKikerdezestElindit.setDisable(false);
            cbxNyelvek.setDisable(false);
            rekordok.clear();
            gombokatTilt(true);
            btnValasz.setDisable(true);
            lblSzo.setText("");
            lblMondat.setText("");
        }
    }
    
    private void gombokatTilt(boolean letilt) {
        btnUjra.setDisable(letilt);
        btnNehez.setDisable(letilt);
        btnJo.setDisable(letilt);
        btnKonnyu.setDisable(letilt);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Legördülő lista nyelveinek beállítása
        FoablakController.nyelvekBeallitasa(cbxNyelvek, nyelvekKodja);
        gombokatTilt(true);   
    }
    
    
    
}
