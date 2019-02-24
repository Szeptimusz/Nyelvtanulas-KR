package nyelvtanulas_kr_szakdolgozat;

import java.awt.Desktop;
import java.net.URI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Window;

public class ForditasController {
    
    @FXML
    private Button btnGoogleTrans;

    @FXML
    private Button btnHozzaad;

    @FXML
    private Label lblSzo;

    @FXML
    private Label lblMondat;

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
        lblMondat.setText(mondat);
    }
    
    private static boolean tanulandoElmentve = false;
    public static boolean isTanulandoElmentve() {
        return tanulandoElmentve;
    }
    
    /**
     * Ha a fordítás beviteli mező nem üres, akkor hozzáadja a szót,mondatot,fordítást és ANKI állapotot a tanulandó táblához
     */
    @FXML
    void hozzaad() {
        String forditas = txtForditas.getText();
        if (forditas.equals("")) {
            FoablakController.figyelmeztet("Figyelem!", "Kérem írjon be fordítást a szóhoz!");
        } else {
            DB.dbBeIr("tanulandoszavak",szo,mondat,forditas,0);
            tanulandoElmentve = true;
            Window ablak = lblSzo.getScene().getWindow();
            ablak.hide();
        }
    }

    /**
     * Megnyitja a Google Translate angolról magyarra fordító oldalát az adott szóval
     * @throws Exception 
     */
    @FXML
    void keres() throws Exception {
        Desktop.getDesktop().browse(new URI("https://translate.google.com/"
                    + "?hl=hu#view=home&op=translate&sl=en&tl=hu&text=" + szo));
    }    
}