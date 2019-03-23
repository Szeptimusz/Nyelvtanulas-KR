package nyelvtanulas_kr_szakdolgozat;

import java.awt.Desktop;
import java.net.URI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import static panel.Panel.figyelmeztet;

/**
 *
 * @author Kremmer Róbert
 */
public class ForditasController {
    
    @FXML
    private Button btnGoogleTrans;
    @FXML
    private Button btnCambridge;
    @FXML
    private Button btnHozzaad;
    @FXML
    private Label lblSzo;
    @FXML
    private TextArea txaMondat;
    @FXML
    private TextField txtForditas;
    @FXML
    private Button btnVisszaallit;

    private String szo;
    public void setSzo(String szo) {
        this.szo = szo;
        lblSzo.setText(szo);
        // A fordítás ablak megnyitásakor a kurzor a szövegbeviteli mezőn lesz
        Platform.runLater(() -> {
            txtForditas.requestFocus();
        });
    }

    private String eredetiMondat;
    public void setMondat(String mondat) {
        eredetiMondat = mondat;
        txaMondat.setText(eredetiMondat);
    }
    
    
    private String forrasNyelvKod;
    
    // A Google Translate kereséshez beállítja a forrásnyelvet
    public void setForrasNyelvKod(String forrasNyelvKod) {
        this.forrasNyelvKod = forrasNyelvKod;
        // Ha nem angol a forrásnyelv, akkor a Cambridge gombot letiltja
        if (!forrasNyelvKod.equals("en")) {
            btnCambridge.setDisable(true); 
        }
    }

    
    private static boolean tanulandoElmentve = false;
    
    // Visszaadja, hogy hozzá lett-e adva a fordítás
    public static boolean isTanulandoElmentve() {
        return tanulandoElmentve;
    }

    public static void setTanulandoElmentve(boolean tanulandoElmentve) {
        ForditasController.tanulandoElmentve = tanulandoElmentve;
    }
    
    /**
     * Ha a fordítás beviteli mező nem üres, akkor hozzáadja a szót,mondatot,fordítást és ANKI állapotot a tanulandó táblához
     */
    @FXML
    void hozzaad() {
        String forditas = txtForditas.getText();
        if (forditas.equals("")) {
            figyelmeztet("Figyelem!", "Kérem írjon be fordítást a szóhoz!");
        } else if (txaMondat.getText().equals("")){
            figyelmeztet("Figyelem!", "Az adott szóhoz nincsen megadva példamondat!");
        } else {
            // A mondatot a szövegterületről szedi ki, így lehetőség van a hozzáadás előtt szerkeszteni a példamondatot
            String mondat = txaMondat.getText();
            DB.tanulandotBeirAdatbazisba(forrasNyelvKod + "_" + "tanulando",szo,mondat,forditas,0);
            tanulandoElmentve = true;
            Window ablak = lblSzo.getScene().getWindow();
            ablak.hide();
        }
    }

    // Visszaállítja a szövegterületre az eredeti példamondatot
    @FXML
    void visszaallit() {
        txaMondat.setText(eredetiMondat);
    }
    
    /**
     * Megnyitja a Google Translate egy adott forrás-nyelvről magyarra fordító oldalát az adott szóval.
     * @throws Exception 
     */
    @FXML
    void megnyitGoogleTranslate() throws Exception {
        Desktop.getDesktop().browse(new URI("https://translate.google.com/"
                    + "?hl=hu#view=home&op=translate&sl=" + forrasNyelvKod
                    + "&tl=hu&text=" + szo));
    }
    
    /**
     * Megnyitja a dictionary.cambridge.org weblapot az adott szóval: példamondatok és angol nyelvű körülírása a szónak.
     * @throws Exception 
     */
    @FXML
    void megnyitCambridge() throws Exception{
            Desktop.getDesktop().browse(new URI("https://dictionary.cambridge.org/dictionary/english/" + szo));
    }
}