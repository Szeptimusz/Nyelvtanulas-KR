package nyelvtanulas_kr_szakdolgozat;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Window;
import static panel.Panel.figyelmeztet;

/**
 * A Fordítás ablakot kezelő osztály. A Főablak táblázatában kiválasztott szó
 * tanulandóként való elmentéséért felel.
 * @author Kremmer Róbert
 */
public class ForditasController {
    
    @FXML
    private Button btnCambridge;
    @FXML
    private Button btnDuden;
    @FXML
    private Button btnElozo;
    @FXML
    private Button btnKovetkezo;
    @FXML
    private Label lblSzo;
    @FXML
    private TextArea txaMondat;
    @FXML
    private TextField txtForditas;
    @FXML
    private TextField txtNevelo;
    @FXML
    private CheckBox cbxNagybetu;

    private String szo;
    private List<String> mondatok;
    private int mondatIndex = 0;
    private String eredetiMondat;
    private String forrasNyelvKod;
    private static boolean tanulandoElmentve = false;
    
    /**
     * A fordítás ablak megnyitásakor beállítja az adott szót és
     * kiírja az ablak megfelelő címkéjébe.
     * @param szo A FoablakController-ből átadott szó
     */
    public void setSzo(String szo) {
        this.szo = szo;
        lblSzo.setText(szo);
        // A fordítás ablak megnyitásakor a kurzor a szövegbeviteli mezőn lesz
        Platform.runLater(() -> {
            btnCambridge.getScene().setOnKeyPressed((final KeyEvent keyEvent) -> {
                if (keyEvent.getCode() == KeyCode.DIGIT8) {
                    try {
                        if (btnElozo.isDisabled()) {
                            keyEvent.consume();
                        } else {
                            elozoMondat();   
                        }
                    } catch (Exception ex) { Logger.getLogger(FoablakController.class.getName()).log(Level.SEVERE, null, ex); }
                    keyEvent.consume();
                }
                
                if (keyEvent.getCode() == KeyCode.DIGIT9) {
                    try {
                        if (btnKovetkezo.isDisabled()) {
                            keyEvent.consume();
                        } else {
                            kovetkezoMondat();   
                        }
                    } catch (Exception ex) { Logger.getLogger(FoablakController.class.getName()).log(Level.SEVERE, null, ex); }
                    keyEvent.consume();
                }
                
                if (keyEvent.getCode() == KeyCode.ESCAPE) {
                    try {
                        btnCambridge.getScene().getWindow().hide();
                    } catch (Exception ex) { Logger.getLogger(FoablakController.class.getName()).log(Level.SEVERE, null, ex); }
                    keyEvent.consume();
                }
                
                if (keyEvent.getCode() == KeyCode.DIGIT0) {
                    try {
                        txtForditas.requestFocus();
                    } catch (Exception ex) { Logger.getLogger(FoablakController.class.getName()).log(Level.SEVERE, null, ex); }
                    keyEvent.consume();
                }
            });
            
        });
    }

    public void setMondatok(List<String> mondatok) {
        this.mondatok = mondatok;
        if (!mondatok.isEmpty()) {
            txaMondat.setText(mondatok.get(0));
            txaMondat.selectRange(this.mondatok.get(0).toLowerCase().indexOf(szo.toLowerCase()), 
                                  this.mondatok.get(0).toLowerCase().indexOf(szo.toLowerCase()) + szo.length());
        }
        btnElozo.setDisable(true);
        if (mondatok.size() < 2) btnKovetkezo.setDisable(true);
    }
    
    /**
     * A Google Translate kereséshez beállítja a forrásnyelvet. Ha az adott nyelv nem
     * angol, akkor a Cambridge gombot letiltja.
     * @param forrasNyelvKod A FoablakController-ből átadott forrásnyelv kód
     */
    public void setForrasNyelvKod(String forrasNyelvKod) {
        this.forrasNyelvKod = forrasNyelvKod;
        // Ha nem angol a forrásnyelv, akkor a Cambridge gombot letiltja
        if (!forrasNyelvKod.equals("en")) {
            btnCambridge.setDisable(true); 
        }
        if (!forrasNyelvKod.equals("de")) {
            btnDuden.setDisable(true);
        }
    }

    /**
     * Lekérdezi, hogy az adatok hozzá lettek-e adva a tanulando táblához.
     * @return Visszaadja, hogy megtörtént-e a hozzáadás az adatbázishoz
     */
    public static boolean isTanulandoElmentve() {
        return tanulandoElmentve;
    }

    /**
     * Beállítja, hogy hozzá lettek-e adva az adatok az adatbázishoz vagy nem.
     * @param tanulandoElmentve Boolean típussal megadja, hogy el lett-e mentve vagy nem
     */
    public static void setTanulandoElmentve(boolean tanulandoElmentve) {
        ForditasController.tanulandoElmentve = tanulandoElmentve;
    }
    
    @FXML
    void elozoMondat() {
        mondatIndex--;
        eredetiMondat = mondatok.get(mondatIndex);
        txaMondat.setText(eredetiMondat);
        if (mondatIndex == 0) btnElozo.setDisable(true);
        if (mondatIndex < mondatok.size()-1) btnKovetkezo.setDisable(false);
        txaMondat.selectRange(mondatok.get(mondatIndex).toLowerCase().indexOf(szo.toLowerCase()), 
                              mondatok.get(mondatIndex).toLowerCase().indexOf(szo.toLowerCase()) + szo.length());
    }
    
    @FXML
    void kovetkezoMondat() {
        mondatIndex++;
        eredetiMondat = mondatok.get(mondatIndex);
        txaMondat.setText(eredetiMondat);
        if (mondatIndex == mondatok.size()-1) btnKovetkezo.setDisable(true);
        if (mondatIndex > 0) btnElozo.setDisable(false);
        txaMondat.selectRange(mondatok.get(mondatIndex).toLowerCase().indexOf(szo.toLowerCase()), 
                              mondatok.get(mondatIndex).toLowerCase().indexOf(szo.toLowerCase()) + szo.length());
    }
    
    /**
     * Ha a fordítás beviteli mező és a példamondat nem üres, akkor hozzáadja a szót,mondatot,fordítást és 
     * ANKI állapotot a tanulandó táblához; beállítja az elmentettséget és bezárja az ablakot.
     */
    @FXML
    public void hozzaad() {
        if (cbxNagybetu.isSelected()) szo = szo.substring(0, 1).toUpperCase() + szo.substring(1);
        
        String forditas = txtForditas.getText();
        if (forditas.equals("")) {
            figyelmeztet("Figyelem!", "Kérem írjon be fordítást a szóhoz!");
        } else if (txaMondat.getText().equals("")){
            figyelmeztet("Figyelem!", "Az adott szóhoz nincsen megadva példamondat!");
        } else {
            String nevelo = txtNevelo.getText();
            
            // A mondatot a szövegterületről szedi ki, így lehetőség van a hozzáadás előtt szerkeszteni a példamondatot
            String mondat = txaMondat.getText();
            DB.tanulandotBeirAdatbazisba(forrasNyelvKod + "_tanulando",nevelo,szo,mondat,forditas,0);
            tanulandoElmentve = true;
            Window ablak = lblSzo.getScene().getWindow();
            ablak.hide();
        }
    }

    /**
     * Visszaállítja a szövegterületre az eredeti példamondatot
     */
    @FXML
    public void visszaallit() {
        txaMondat.setText(eredetiMondat);
        txaMondat.selectRange(mondatok.get(mondatIndex).indexOf(szo), mondatok.get(mondatIndex).indexOf(szo) + szo.length() + 1);
    }
    
    /**
     * Megnyitja a Google Translate egy adott forrásnyelvről magyarra fordító oldalát az adott szóval.
     * @throws Exception Hiba esetén kivételt dob
     */
    @FXML
    public void megnyitGoogleTranslate() throws Exception {
        Desktop.getDesktop().browse(new URI("https://translate.google.com/"
                    + "?hl=hu#view=home&op=translate&sl=" + forrasNyelvKod
                    + "&tl=hu&text=" + szo));
    }
    
    /**
     * Megnyitja a dictionary.cambridge.org weblapot az adott szóval: példamondatok és angol nyelvű körülírása a szónak.
     * @throws Exception Exception Hiba esetén kivételt dob
     */
    @FXML
    public void megnyitCambridge() throws Exception{
            Desktop.getDesktop().browse(new URI("https://dictionary.cambridge.org/dictionary/english/" + szo));
    }
    
    @FXML
    void megnyitDuden() throws URISyntaxException, IOException {
        if (cbxNagybetu.isSelected()) szo = szo.substring(0, 1).toUpperCase() + szo.substring(1);
        Desktop.getDesktop().browse(new URI("https://www.duden.de/suchen/dudenonline/" + szo));
    }
    
}