package nyelvtanulas_kr_szakdolgozat;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import static nyelvtanulas_kr_szakdolgozat.FoablakController.uzenetek;
import static panel.Panel.figyelmeztet;

/**
 * A kikérdezés ablakot kezelő osztály. Az adott nyelv tanulandó szavai közül kikérdezi
 * azokat, amiknek a tanulása éppen esedékes.
 * @author Kremmer Róbert
 */
public class KikerdezesController implements Initializable, Feliratok {

    ArrayList<Sor> rekordok = new ArrayList<>();
    int            index;
    String         forrasNyelvKod;
    
    @FXML
    private ComboBox<String> cbxNyelvek;
    @FXML
    private Label lblKeremValasszaKi;
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
            figyelmeztet(uzenetek.get("figyelmeztet"),uzenetek.get("melyiknyelv"));
        } else {
            forrasNyelvKod = FoablakController.nyelvekKodja.get(cbxNyelvek.getValue());
            rekordok = DB.tanulandotLekerdez(forrasNyelvKod + "_tanulando");
            if (rekordok.isEmpty()) {
                figyelmeztet(uzenetek.get("figyelmeztet"),uzenetek.get("nincsentanulando"));
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
     * Az Újra -gombra kattintva hozzáadja a kártyát a kikérdezési sor (a lista)
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
            String nevelo = rekordok.get(index).getNevelo();
            if (nevelo.length() > 0) nevelo += " ";
            
            lblSzo.setText(nevelo + rekordok.get(index).getSzo());
            lblMondat.setText(rekordok.get(index).getMondat());
        } else {
            figyelmeztet(uzenetek.get("figyelmeztet"),uzenetek.get("kikerdezesvege"));
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
     * A legördülő lista nyelveinek beállítása. Ablak feliratok beállítása a
     * megfelelő nyelven. Alapértelmezetten az értékelő és válasz gombok letiltása.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        cbxNyelvek.getItems().clear();
        cbxNyelvek.getItems().addAll(FoablakController.nyelvek);
        
        lblKeremValasszaKi.setText(FoablakController.kikerdezesFelirat[0]);
        btnKikerdezestElindit.setText(FoablakController.kikerdezesFelirat[1]);
        btnValasz.setText(FoablakController.kikerdezesFelirat[2]);
        btnUjra.setText(FoablakController.kikerdezesFelirat[3]);
        btnNehez.setText(FoablakController.kikerdezesFelirat[4]);
        btnJo.setText(FoablakController.kikerdezesFelirat[5]);
        btnKonnyu.setText(FoablakController.kikerdezesFelirat[6]);
        
        gombokatTilt(true);
        btnValasz.setDisable(true);
    }
    
    
    
}
