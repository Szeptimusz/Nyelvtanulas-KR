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

    /**
     * A nyelv kiválasztása után elindítja az ahhoz a nyelvhez tartozó aktuálisan
       tanulandó szókártyák kikérdezését.
     */
    @FXML
    public void kikerdez() {
        
        if (cbxNyelvek.getValue() == null) {
            figyelmeztet("Figyelem!","Kérem válassza ki, hogy melyik nyelv szókártyáit szeretné használni");
        } else {
            forrasNyelvKod = nyelvekKodja.get(cbxNyelvek.getValue());
            rekordok = DB.tanulandotLekerdez(forrasNyelvKod + "_tanulando");
            if (rekordok.isEmpty()) {
                figyelmeztet("Figyelem","Nincsen aktuálisan tanulandó szó!");
            } else {
                index = 0;
                szotMondatotBeallit(index);
                btnKikerdezestElindit.setDisable(true);
                cbxNyelvek.setDisable(true);
                btnValasz.setDisable(false);
            }
        }
        
    }
    
    /**
     * A Válasz mutatása -gombra kattintva megmutatja a szóhoz tartozó fordítást
     * és kikapcsolja az értékelő gombok tiltását.
     */
    @FXML
    public void valasz() {
        lblForditas.setText(rekordok.get(index).getForditas());
        gombokatTilt(false);
    }
    
    /**
     * Az Újra -gombra kattintva hozzáadja a kártyát a kikérdezi sor (a lista)
     * végéhez. Így addig kérdezi ki újra a kártyát, amíg nem lesz máshogyan értékelve.
     */
    @FXML
    public void ujra() {
        // Ha nem tudjuk a szókártyát, akkor addig ismétli a kikérdezést, amíg
        // nem kíttintunk rá valamelyik másik értékelő gombra
        rekordok.add(rekordok.get(index));
        index++;
        lblForditas.setText("");
        szotMondatotBeallit(index);
        gombokatTilt(true);
    }
    
    /**
     * A Nehéz-gombra kattintva úgy frissíti az adott szókártya kikérdezési idejét,
     * hogy 2 nap múlva legyen esedékes.
     */
    @FXML
    public void nehez() {
        // Ha a szókártya nehéz volt, akkor 2 nap múlva kérdezi ki újra
        DB.frissitKikerdezes(forrasNyelvKod + "_tanulando", rekordok.get(index).getSzo(), 
                (System.currentTimeMillis() + 2*24*3600*1000));
        index++;
        lblForditas.setText("");
        szotMondatotBeallit(index);
        gombokatTilt(true);
    }
    
    /**
     * A Könnyű-gombra kattintva úgy frissíti az adott szókártya kikérdezési idejét,
     * hogy 10 nap múlva legyen esedékes.
     */
    @FXML
    public void konnyu() {
        // Ha a szókártya könnyű volt, akkor 10 nap múlva kérdezi ki újra
        DB.frissitKikerdezes(forrasNyelvKod + "_tanulando", rekordok.get(index).getSzo(), 
                (System.currentTimeMillis() + 10*24*3600*1000));
        index++;
        lblForditas.setText("");
        szotMondatotBeallit(index);
        gombokatTilt(true);
    }

    /**
     * A Jó-gombra kattintva úgy frissíti az adott szókártya kikérdezési idejét,
     * hogy 5 nap múlva legyen esedékes.
     */
    @FXML
    public void jo() {
        // Ha a szókártya jó volt, akkor 5 nap múlva kérdezi ki újra
        DB.frissitKikerdezes(forrasNyelvKod + "_tanulando", rekordok.get(index).getSzo(), 
                (System.currentTimeMillis() + 5*24*3600*1000));
        index++;
        lblForditas.setText("");
        szotMondatotBeallit(index);
        gombokatTilt(true);
    }
    
    /**
     * Az értékelés gombok megnyomása után ha még van listaelem, akkor beállítja
     * a label-be a szót és mondatot, egyébként befejeződik a kikérdezés.
     * @param index  A kikérdezési sorban (listában) adja meg, hogy hányadik helyen vagyunk
     */
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
    
    /**
     * Beállítja, hogy az értékelő gombok le legyenek-e tiltva
     * @param letilt boolean típussal megkapja, hogy tiltsa a gombokat vagy ne
     */
    private void gombokatTilt(boolean letilt) {
        btnUjra.setDisable(letilt);
        btnNehez.setDisable(letilt);
        btnJo.setDisable(letilt);
        btnKonnyu.setDisable(letilt);
    }
    
    /**
     * A legördülő lista nyelveinek beállítása és rövidítéseik tárolása.
     * Alapértelmezetten az értékelő és válasz gombok letiltása.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Legördülő lista nyelveinek beállítása
        FoablakController.nyelvekBeallitasa(cbxNyelvek, nyelvekKodja);
        gombokatTilt(true);
        btnValasz.setDisable(true);
    }
    
    
    
}
