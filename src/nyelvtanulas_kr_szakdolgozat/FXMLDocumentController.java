package nyelvtanulas_kr_szakdolgozat;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    
    DB db = new DB();
    final String dbUrl = "jdbc:mysql://localhost:3306/";
    static String fileNeve;
    static String minden = "";
    static HashMap<String, Integer> szavak_indexe = new HashMap<>();
    public final ObservableList<Sor> data = FXCollections.observableArrayList();
    
    
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
        eloFeldolgozas();
        feldolgozas();
        azonosakTorlese();
        dbOsszevet("ismertszavak");
        dbOsszevet("ignoraltszavak");
        dbOsszevet("tanulandoszavak");
        // Ha be lett pipálva a checkbox
        if (cxbEgyszer.isSelected()) {
            dbOsszevet("gorgetettszavak");
        }
        listaTorlesek();
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
                System.out.println(e.getMessage());
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

    /*
    A feldolgozás() metódus előtt szükséges azokat az egymás után többször előforduló karaktereket törölni, amik alapján majd a 
    splittelés történik ("." "?" "!").
    Végigmegy a teljes szövegen karakterenként, ha a karakter 'A'-nál kisebb kódú (.,!? stb.), akkor ha a következő karakter is
    ugyanolyan, addig törli a következőket amíg nem talál egy más karaktert.
    */
    public void eloFeldolgozas() {
        StringBuilder sb = new StringBuilder(minden);
        
        for (int i = 0; i < sb.length()-1; i++) {
            char c = sb.charAt(i);
            if (c < 'A') {
                while (c == sb.charAt(i + 1)) {
                    sb.deleteCharAt(i + 1);
                    // Ha az index kimenne a szövegből akkor megállítjuk
                    if (i + 1 == sb.length()) {
                        break;
                    }
                }
            }
        }
        minden = sb.toString();
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
            int db = 1;
            for (int j = i +1; j < data.size(); j++){
                if(szo.equals(data.get(j).getSzo())){
                    data.remove(j);
                    j--;
                    db++;
                }
            }
            data.get(i).setGyak(db);
        }
        /* A lista utolsó szavánál is beállítja az indexes hashmap-et (az lista azonos szavainak törlésénél csak
           az utolsó előtti elemig mentünk el) */
        szavak_indexe.put(data.get(data.size()-1).getSzo(), data.size()-1);
    }

    
    /* A kapott tábla szavait lekérdezi az adatbázisból és ha létezik a listában, akkor a lista szavát átnevezi torlendo-re, 
       jelezve, hogy a táblázat megjelenítése előtt törölni kell a listából, VAGY ha a szó görgetett szó és létezik a listában,
       akkor 1-gyel a gyakoriságát a listában (így biztos, hogy legalább kétszer előfordul globálisan) és törli a táblából
       a szót.
    */

    public void dbOsszevet(String tabla) {
        String query = "SELECT szavak FROM nyelvtanulas." + tabla;
        try (Connection kapcs = DriverManager.getConnection(dbUrl,"root","");
            PreparedStatement ps = kapcs.prepareStatement(query)) {
            ResultSet eredmeny = ps.executeQuery();
            while (eredmeny.next()) {
                String szo = eredmeny.getString("szavak");
                if (szavak_indexe.get(szo) != null) {
                    // ha nem a gorgetettszavak táblán megyünk végig 
                    if (!tabla.equals("gorgetettszavak")) {
                        data.get(szavak_indexe.get(szo)).setSzo("torlendo");
                    } else {
                        int gyak = data.get(szavak_indexe.get(szo)).getGyak();
                        data.get(szavak_indexe.get(szo)).setGyak(++gyak);
                        db.dbSzotTorol(tabla, szo);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Nem sikerült az adatbázis-lekérdezés!");
            System.out.println(e.getMessage());
        }
    }
    
    /*
     Végigmegy a listán és ha a szó "torlendo", akkor törli onnan. Különben ha be volt jelölve az egyszeres
       előfordulás megjelenésének tiltása (és így a szavak görgetése) és a szó, csak egyszer fordul elő globálisan,
       akkor törli a listából és hozzáadja a görgetett szavakhoz az adatbázisban
    */
    
    public void listaTorlesek() {
        for (int i = 0; i < data.size(); i++) {
            String szo = data.get(i).getSzo();
            if (szo.equals("torlendo")) {
                data.remove(i);
                i--;
            } else if (cxbEgyszer.isSelected() && data.get(i).getGyak() == 1) {
                data.remove(i);
                i--;
                db.dbBeIr("gorgetettszavak", szo);
            }
        }
    }

    // A táblázatban kijelölt sor szavát a gomb megnyomása után elmenti az adatbázis ignoraltszavak táblájába
    @FXML
    void ignoral(ActionEvent event) {
        String szo = tblTablazat.getSelectionModel().getSelectedItem().getSzo();
        db.dbBeIr("ignoraltszavak",szo);
        // Miután hozzáadtuk az ismert szavakhoz, ne lehessen véletlenül többször hozzáadni
        btnIgnore.setDisable(true);
        atnevezLeptet("IGNORÁLT SZÓ");
    }

    // A táblázatban kijelölt sor szavát a gomb megnyomása után elmenti az adatbázis ismertszavak táblájába
    @FXML
    void ismertMent(ActionEvent event) {
        String szo = tblTablazat.getSelectionModel().getSelectedItem().getSzo();
        db.dbBeIr("ismertszavak",szo);
        // Miután hozzáadtuk az ismert szavakhoz, ne lehessen véletlenül többször hozzáadni
        btnIsmert.setDisable(true);
        atnevezLeptet("ISMERT SZÓ");
    }

    // A táblázatban kijelölt sor szavát a gomb megnyomása után elmenti az adatbázis tanulandoszavak táblájába
    @FXML
    void tanulandoMent(ActionEvent event) {
        String szo = tblTablazat.getSelectionModel().getSelectedItem().getSzo();
        String mondat = tblTablazat.getSelectionModel().getSelectedItem().getMondat();
        db.dbBeIr("tanulandoszavak",szo,mondat);
        // Miután hozzáadtuk az ismert szavakhoz, ne lehessen véletlenül többször hozzáadni
        btnTanulando.setDisable(true);
        atnevezLeptet("TANULANDÓ");
    }
    
    public void atnevezLeptet(String szoveg) {
        // A listener figyeli, hogy van-e szó, ami változott, ezért nevezzük át (gomb letiltáshoz kell)
        tblTablazat.getSelectionModel().getSelectedItem().setSzo(szoveg);
        // A hozzáadás után a lista következő elemét jelölje ki
        int i = tblTablazat.getSelectionModel().getSelectedIndex();
        tblTablazat.getSelectionModel().select(i+1);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Szövegbeviteli mezőnél a sorok tördelése
        txaBevitel.setWrapText(true);
        
        // Adatbázis és táblák létrehozása
        DB db = new DB();
        db.adatbazisEsTablakLetrehozasa();

        // Táblázat oszlopainak létrehozása és beállítása
        TableColumn colSzo = new TableColumn("Szavak");
        colSzo.setMinWidth(100);
        colSzo.setCellFactory(TextFieldTableCell.forTableColumn());
        colSzo.setCellValueFactory(new PropertyValueFactory<Sor, String>("szo"));
        
        
        TableColumn colMondat = new TableColumn("Mondatok");
        colMondat.setMinWidth(550);
        colMondat.setCellFactory(TextFieldTableCell.forTableColumn());
        colMondat.setCellValueFactory(new PropertyValueFactory<Sor, String>("mondat"));
        
        // Gyakoriság oszlop létrehozása: nem kell rendezés metódus a listához, a felhasználó tudja rendezni
        TableColumn<Sor, Number> colGyak = new TableColumn<>("Gyakoriság");
        colGyak.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
        colGyak.setMinWidth(40);
        
        // Oszlopok hozzáadása a táblázathoz
        tblTablazat.getColumns().addAll(colSzo, colMondat, colGyak);
        
        // Ha egy szónál már használtuk az egyik gombot, akkor ne lehessen már egyik másikat sem használni
        // Kéne egy módosítási lehetőség, ha véletlenül rosszra nyomtunk!
        tblTablazat.getSelectionModel().selectedItemProperty().addListener(
        (v, regi, uj) -> {
            String ujSzo = uj.getSzo();
            if (ujSzo.equals("ISMERT SZÓ") || ujSzo.equals("IGNORÁLT SZÓ") || ujSzo.equals("TANULANDÓ")) {
                btnIsmert.setDisable(true);
                btnIgnore.setDisable(true);
                btnTanulando.setDisable(true);
            } else {
                btnIsmert.setDisable(false);
                btnIgnore.setDisable(false);
                btnTanulando.setDisable(false);
            }
        });
        
    }    
}