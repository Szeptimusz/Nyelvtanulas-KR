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

    private String szo;
    public void setSzo(String szo) {
        this.szo = szo;
        lblSzo.setText(szo);
        // A fordítás ablak megnyitásakor a kurzor a szövegbeviteli mezőn lesz
        Platform.runLater(() -> {
            txtForditas.requestFocus();
        });
    }

    private String mondat;
    public void setMondat(String mondat) {
        this.mondat = mondat;
        txaMondat.setText(mondat);
    }
    
    // A Google Translate kereséshez beállítja a forrásnyelvet
    private String forrasNyelvKod;
    public void setForrasNyelvKod(String forrasNyelvKod) {
        this.forrasNyelvKod = forrasNyelvKod;
        // Ha nem angol a forrásnyelv, akkor a Cambridge gombot letiltja
        if (!forrasNyelvKod.equals("en")) {
            btnCambridge.setDisable(true); 
        }
    }

    // Visszaadja, hogy hozzá lett-e adva a fordítás
    private static boolean tanulandoElmentve = false;
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
        } else {
            // A mondatot a szövegterületről szedi ki, így lehetőség van a hozzáadás előtt szerkeszteni a példamondatot
            mondat = txaMondat.getText();
            DB.tanulandotBeirAdatbazisba(forrasNyelvKod + "_" + "tanulando",szo,mondat,forditas,0);
            /* Megpróbálja kitörölni az adott szót a szavak táblából, mert ha az előbb kiírt tanulandó szó már görgetettként szerepelt benne, akkor
               így mindkét táblában egyszerre bent van. */
            DB.szotTorolAdatbazisbol(forrasNyelvKod + "_" + "szavak", szo);
            tanulandoElmentve = true;
            Window ablak = lblSzo.getScene().getWindow();
            ablak.hide();
        }
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