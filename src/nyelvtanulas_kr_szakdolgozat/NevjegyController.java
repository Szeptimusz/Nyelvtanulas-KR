package nyelvtanulas_kr_szakdolgozat;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import static nyelvtanulas_kr_szakdolgozat.FoablakController.uzenetek;
import static panel.Panel.hiba;

/**
 * A névjegy ablakot kezelő osztály. Tájékoztatást ad a program készítőjéről, a program
 * verziószámáról, valamint böngészőben megnyitható a fejlesztői dokumentáció osztályokat, 
 * metódusokat és függvényeket leíró részlete.
 * @author Kremmer Róbert
 */
public class NevjegyController implements Initializable, Feliratok {

    @FXML
    private Label  lblKeszito;
    @FXML
    private Label  lblVerzio;
    @FXML
    private Button btnFejlesztoiDok;
    @FXML
    private Button btnGithub;
    
    /**
     * A gombra kattintva ha talál javadoc mappát a projekt mappájában, akkor
     * a fejlesztői dokumentáció html verzióját megnyitja a böngészőben. 
     */
    @FXML
    public void fejlesztoDoc() {
        try {
            String docUtvonal = new File("").getAbsolutePath();
            docUtvonal += "\\javadoc\\index.html";
            File htmlFile = new File(docUtvonal);
            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch (IOException e) {
            hiba(uzenetek.get("hiba"),e.getMessage());
        }
    }
    
    /**
     * Megnyitja a program Github oldalát.
     */
    @FXML
    void github() {
        try {
            Desktop.getDesktop().browse(new URI("https://github.com/Szeptimusz/Nyelvtanulas-KR"));
        } catch (IOException | URISyntaxException e) {
            hiba(uzenetek.get("hiba"), e.getMessage());
        }
    }
    
    /**
     * Beállítja az ablak címkéibe a készítőt és a verzió számot.
     * Beállítja az ablak feliratait a megfelelő nyelven.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        lblKeszito.setText(FoablakController.nevjegyFelirat[0] + " Kremmer Róbert");
        lblVerzio.setText(FoablakController.nevjegyFelirat[1] + " 1.8");
        btnFejlesztoiDok.setText(FoablakController.nevjegyFelirat[2]);
        btnGithub.setText(FoablakController.nevjegyFelirat[3]);
    }    
    
}
