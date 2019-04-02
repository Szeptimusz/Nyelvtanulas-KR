package nyelvtanulas_kr_szakdolgozat;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import static panel.Panel.hiba;

/**
 *
 * @author Kremmer Róbert
 */
public class NevjegyController implements Initializable {

    @FXML
    private Label lblKeszito;

    @FXML
    private Label lblVerzio;

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
            hiba("Hiba",e.getMessage());
        }
    }
    
    /**
     * Beállítja az ablak címkéibe a készítőt és a verzió számot.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblKeszito.setText("Készítette: Kremmer Róbert");
        lblVerzio.setText("Verzió: 1.0");
    }    
    
}
