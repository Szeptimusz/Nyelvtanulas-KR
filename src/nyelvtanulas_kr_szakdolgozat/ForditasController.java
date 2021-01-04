package nyelvtanulas_kr_szakdolgozat;

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
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Window;
import static nyelvtanulas_kr_szakdolgozat.FoablakController.uzenetek;
import static nyelvtanulas_kr_szakdolgozat.Panel.figyelmeztet;

/**
 * A Fordítás ablakot kezelő osztály. A Főablak táblázatában kiválasztott szó
 * tanulandóként való elmentéséért felel.
 * @author Kremmer Róbert
 */
public class ForditasController implements Feliratok {
    
    @FXML
    private Label       lblMentesElottKeremAdjaMeg;
    @FXML
    private Label       lblSzo1;
    @FXML
    private Label       lblPeldamondat;
    @FXML
    private Button      btnGoogleTranslate;
    @FXML
    private Label       lblSzoForditasa;
    @FXML
    private Label       lblSzo;
    @FXML
    private TextField   txtForditas;
    @FXML
    private Button      btnCambridge;
    @FXML
    private Button      btnHozzaadas;
    @FXML
    private TextArea    txaMondat;
    @FXML
    private Button      btnEredetiPeldamondat;
    @FXML
    private Label       lblNagybetuvelKezdodjon;
    @FXML
    private CheckBox    cbxNagybetu;
    @FXML
    private Label       lblNevelo;
    @FXML
    private TextField   txtNevelo;
    @FXML
    private Button      btnDuden;
    @FXML
    private Button      btnElozo;
    @FXML
    private Button      btnKovetkezo;
    @FXML
    private WebView     wvOldal;
    private WebEngine   engine;

    private String         szo;
    private List<String>   mondatok;
    private int            mondatIndex = 0;
    private String         eredetiMondat;
    private String         forrasNyelvKod;
    private String         celNyelvKod;
    private static boolean tanulandoElmentve = false;
    
    /**
     * Beállítja a fordítás ablakban megjelenő adatokat: szó, példamondatok listája.
     * Forrásnyelv alapján gombokat tilthat le. Hotkey-t rendel hozzá az ablakhoz.
     * Beállítja az ablak felületének a kiírásait a megfelelő nyelven.
     * @param szo            A tanulandó szó
     * @param mondatok       A  példamondatok listája
     * @param forrasNyelvKod A forrásnyelv kódja
     * @param celNyelvKod
     */
    public void setForditasAblakAdatok(String szo, List<String> mondatok, String forrasNyelvKod, String celNyelvKod) {
        engine = wvOldal.getEngine();
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
        
        this.szo = szo;
        lblSzo.setText(szo);
        
        this.celNyelvKod = celNyelvKod;
        
        this.mondatok = mondatok;
        if (!mondatok.isEmpty()) {
            txaMondat.setText(mondatok.get(0));
            txaMondat.selectRange(this.mondatok.get(0).toLowerCase().indexOf(szo.toLowerCase()), 
                                  this.mondatok.get(0).toLowerCase().indexOf(szo.toLowerCase()) + szo.length());
        }
        btnElozo.setDisable(true);
        if (mondatok.size() < 2) btnKovetkezo.setDisable(true);
        
        this.forrasNyelvKod = forrasNyelvKod;
        if (!forrasNyelvKod.equals("en")) btnCambridge.setDisable(true); 
        if (!forrasNyelvKod.equals("de")) btnDuden.setDisable(true);
        
        Platform.runLater(() -> {
            btnCambridge.getScene().setOnKeyPressed((final KeyEvent keyEvent) -> {
                
                if (keyEvent.getCode() == KeyCode.ESCAPE) {
                    try {
                        btnCambridge.getScene().getWindow().hide();
                    } catch (Exception ex) { Logger.getLogger(FoablakController.class.getName()).log(Level.SEVERE, null, ex); }
                    keyEvent.consume();
                }
                
            });
            
            // A fordítás ablak megnyitásakor automatikusan megnyitja a google translate-et a megfelelő szóval
            megnyitGoogleTranslate();
        });
        
        // Az ablak feliratainak beállítása a megfelelő nyelven
        lblMentesElottKeremAdjaMeg.setText(FoablakController.forditasFelirat[0]);
        lblNevelo.setText(FoablakController.forditasFelirat[1]);
        lblSzo1.setText(FoablakController.forditasFelirat[2]);
        lblNagybetuvelKezdodjon.setText(FoablakController.forditasFelirat[3]);
        lblPeldamondat.setText(FoablakController.forditasFelirat[4]);
        btnEredetiPeldamondat.setText(FoablakController.forditasFelirat[5]);
        btnElozo.setText(FoablakController.forditasFelirat[6]);
        btnKovetkezo.setText(FoablakController.forditasFelirat[7]);
        btnGoogleTranslate.setText(FoablakController.forditasFelirat[8]);
        btnCambridge.setText(FoablakController.forditasFelirat[9]);
        btnDuden.setText(FoablakController.forditasFelirat[10]);
        lblSzoForditasa.setText(FoablakController.forditasFelirat[11]);
        btnHozzaadas.setText(FoablakController.forditasFelirat[12]);
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
    
    /**
     * Az adott szóhoz tartozó példamondatok listájából a korábbi mondatot 
     * jeleníti meg (ha legalább a második elemnél van). Kijelöli a szót a mondatban.
     */
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
    
    /**
     * Az adott szóhoz tartozó példamondatok listájából a későbbi mondatot
     * jeleníti meg (kivéve ha már a lista végén van). Kijelöli a szót a mondatban.
     */
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
            figyelmeztet(uzenetek.get("figyelmeztet"), uzenetek.get("irjonbeforditast"));
            txtForditas.requestFocus();
        } else if (txaMondat.getText().equals("")){
            figyelmeztet(uzenetek.get("figyelmeztet"), uzenetek.get("nincspeldamondat"));
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
     * Megnyitja a Google Translate egy adott forrásnyelvről egy cél nyelvre fordító oldalát az adott szóval.
     */
    @FXML
    public void megnyitGoogleTranslate() {
        engine.load("https://translate.google.com/"
                    + "?hl=" + celNyelvKod + "#view=home&op=translate&sl=" + forrasNyelvKod
                    + "&tl=" + celNyelvKod + "&text=" + szo);
    }
    
    /**
     * Megnyitja a dictionary.cambridge.org weblapot az adott szóval: példamondatok és angol nyelvű körülírása a szónak.
     */
    @FXML
    public void megnyitCambridge() {
        engine.load("https://dictionary.cambridge.org/dictionary/english/" + szo);
    }
    
    /**
     * A Duden.de online német szótárat megnyitja az adott német szóval.
     */
    @FXML
    void megnyitDuden() {
        if (cbxNagybetu.isSelected()) szo = szo.substring(0, 1).toUpperCase() + szo.substring(1);
        engine.load("https://www.duden.de/suchen/dudenonline/" + szo);
    }

}