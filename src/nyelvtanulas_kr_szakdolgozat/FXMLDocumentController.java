package nyelvtanulas_kr_szakdolgozat;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;

/**
 *
 * @author Kremmer Róbert
 */
public class FXMLDocumentController implements Initializable {
    
    static private Connection con;
    static private Statement st;
    static private ResultSet rs;
    static String fileNeve;
    static String minden = "";
    static HashMap<String, Integer> gyakorisag = new HashMap<>();
    static HashMap<String, Integer> szavak_indexe = new HashMap<>();
    private final ObservableList<Sor> data = FXCollections.observableArrayList();
    
    
     @FXML
    private Button btnChooser;

    @FXML
    private TextArea txaBevitel;

    @FXML
    private CheckBox cxbEgyszer;

    @FXML
    private Button btnFuttat;

    @FXML
    private Button btnIsmert;

    @FXML
    private Button btnTanulando;

    @FXML
    private Button btnIgnore;

    @FXML
    private TableView<Sor> tblTablazat;

    @FXML
    void futtat(ActionEvent event) {
        beolvasas();
        feldolgozas();
        azonosakTorlese();
        tblTablazat.setItems(data);
    }

    @FXML
    void talloz(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            fileNeve = selectedFile.getName();
        } else {
            System.out.println("File is not valid");
        }
    }
    
    @FXML
    void ignoral(ActionEvent event) {

    }

    @FXML
    void ismertMent(ActionEvent event) {

    }

    @FXML
    void tanulandoMent(ActionEvent event) {

    }
    
    // Adatok beolvasása fájl tallózással vagy szövegterületből
    public void beolvasas() {
        if (fileNeve != null) {
            File f = new File(fileNeve);
            try (Scanner be = new Scanner(f,"Cp1252")) {
                while (be.hasNextLine()) {
                    minden += be.nextLine();
                }
                be.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        } else {
            minden = txaBevitel.getText();
            if (minden.equals("")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Figyelem!");
                alert.setHeaderText(null);
                alert.setContentText("Üres szövegmező! Kérem adjon meg szöveget, vagy használja "
                        + "a Tallózás gombot!");

                alert.showAndWait();
            }
        }
    }

    // Beolvasott sorok feldolgozása: mondatok és szavak meghatározása
    public void feldolgozas() {
        // A szöveg szétvágása "." "?" "!" szerint, plusz azok az esetek amikor szóköz van utánuk
        String mondatok [] = minden.split("\\. |\\.|\\? |\\?|\\! |\\!");
        for (int i = 0; i < mondatok.length; i++) {
            String[] szok = mondatok[i].split(" ");
            for (int j = 0; j < szok.length; j++) {
                char elsoKarakter = szok[j].charAt(0);
                // Csak a A-Z és a-z kezdőbetűseket engedi feldolgozni
                if (elsoKarakter < 'A' || elsoKarakter > 'z' || (elsoKarakter > 90 && elsoKarakter < 97)) {
                    continue;
                }
                char utolsoKarakter = szok[j].charAt(szok[j].length()-1);
                if (utolsoKarakter < 'a' || utolsoKarakter > 'z') {
                    szok[j] = szok[j].substring(0, szok[j].length()-1);
                }
                szok[j] = szok[j].toLowerCase();
                
                data.add(new Sor(szok[j], mondatok[i], 1));
            }
        }
        // A minden String kiürítése a feldolgozás után, hogy ne foglalja tovább a memóriát
        minden = "";
    }

    // Az azonos szavak törlése a listából és szógyakoriság számolása
    public void azonosakTorlese() {
        for (int i = 0; i < data.size()-1; i++){
            String szo = data.get(i).getSzo();
            //Beállítja az egyes szavak index helyét a listában (listában keresést gyorsítja)
            szavak_indexe.put(szo, i);
            Integer db = gyakorisag.get(szo);
            if (db == null) {
                    db = 1;
            }
            for (int j = i +1; j < data.size(); j++){
                if(szo.equals(data.get(j).getSzo())){
                    data.remove(j);
                    j--;
                    gyakorisag.put(szo, ++db);
                }
            }
            gyakorisag.put(szo, db);
            data.get(i).setGyak(db);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Szövegbeviteli mezőnél a sorok tördelése
        txaBevitel.setWrapText(true);
        
        // Táblázat oszlopainak létrehozása és beállítása
        TableColumn colSzo = new TableColumn("Szavak");
        colSzo.setMinWidth(100);
        colSzo.setCellFactory(TextFieldTableCell.forTableColumn());
        colSzo.setCellValueFactory(new PropertyValueFactory<Sor, String>("szo"));
        
        
        TableColumn colMondat = new TableColumn("Mondatok");
        colMondat.setMinWidth(550);
        colMondat.setCellFactory(TextFieldTableCell.forTableColumn());
        colMondat.setCellValueFactory(new PropertyValueFactory<Sor, String>("mondat"));
        
        // Gyakoriság oszlop létrehozása: nem kell rendezés metódus, a felhasználó tudja rendezni
        TableColumn<Sor, Number> colGyak = new TableColumn<>("Gyakoriság");
        colGyak.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
        colGyak.setMinWidth(40);
        
        // Oszlopok hozzáadása a táblázathoz
        tblTablazat.getColumns().addAll(colSzo, colMondat, colGyak);
    }    
}