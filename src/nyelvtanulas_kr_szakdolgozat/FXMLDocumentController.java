package nyelvtanulas_kr_szakdolgozat;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    static private Statement st2;
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
        dbOsszevet("ismertszavak");
        dbOsszevet("ignoraltszavak");
        dbOsszevet("tanulandoszavak");
        // Ha be lett pipálva a checkbox
        if (cxbEgyszer.isSelected()) {
            dbGorgetettOsszevet();
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
    
    // A táblázatban kijelölt sor szavát a gomb megnyomása után elmenti az adatbázis ignoraltszavak táblájába
    @FXML
    void ignoral(ActionEvent event) {
        String szo = tblTablazat.getSelectionModel().getSelectedItem().getSzo();
        String into = "INSERT INTO nyelvtanulas.ignoraltszavak (szavak) " 
                + "VALUES ('"+ szo +"')";
        dbBeMent("IGNORÁLT SZÓ", into, btnIgnore);
    }

    // A táblázatban kijelölt sor szavát a gomb megnyomása után elmenti az adatbázis ismertszavak táblájába
    @FXML
    void ismertMent(ActionEvent event) {
        String szo = tblTablazat.getSelectionModel().getSelectedItem().getSzo();
        String into = "INSERT INTO nyelvtanulas.ismertszavak (szavak) " 
                + "VALUES ('"+ szo +"')";
        dbBeMent("ISMERT SZÓ", into, btnIsmert);
    }

    // A táblázatban kijelölt sor szavát a gomb megnyomása után elmenti az adatbázis tanulandoszavak táblájába
    @FXML
    void tanulandoMent(ActionEvent event) {
        String szo = tblTablazat.getSelectionModel().getSelectedItem().getSzo();
        String mondat = tblTablazat.getSelectionModel().getSelectedItem().getMondat();
        String into = "INSERT INTO nyelvtanulas.tanulandoszavak (szavak, mondatok) " 
                + "VALUES ('"+ szo + "','" + mondat +"')";
        dbBeMent("TANULANDÓ", into, btnTanulando);
    }
    
    public void dbBeMent(String szoveg, String into, Button btn) {
        try {
            st.executeUpdate(into);
            // Miután hozzáadtuk az ismert szavakhoz, ne lehessen véletlenül többször hozzáadni
            btn.setDisable(true);
            // A listener figyeli, hogy van-e szó, ami ismert-re változott, ezért nevezzük át
            tblTablazat.getSelectionModel().getSelectedItem().setSzo(szoveg);
            // A hozzáadás után a lista következő elemét jelölje ki
            int i = tblTablazat.getSelectionModel().getSelectedIndex();
            tblTablazat.getSelectionModel().select(i+1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
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
        /* A lista utolsó szavánál is beállítja az indexes hashmap-et (az lista azonos szavainak törlésénél csak
           az utolsó előtti elemig mentünk el) */
        szavak_indexe.put(data.get(data.size()-1).getSzo(), data.size()-1);
    }

    /* A kapott tábla szavait lekérdezi az adatbázisból és ha létezik a listában, akkor a lista szavát átnevezi torlendo-re, 
       jelezve, hogy a táblázat megjelenítése előtt törölni kell a listából */
    public void dbOsszevet(String tabla) {
        try {
            String query = "SELECT szavak FROM nyelvtanulas." + tabla;
            rs = st.executeQuery(query);
            while (rs.next()) {
                String szo = rs.getString("szavak");
                if (szavak_indexe.get(szo) != null) {
                    data.get(szavak_indexe.get(szo)).setSzo("torlendo");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    /*
    Végigmegy a görgetett táblán lekérdezi a szavakat, ha a szó létezik a listában, akkor
    megnöveli 1-gyel a gyakoriságát (így biztos, hogy legalább kétszer előfordul globálisan) és törli a táblából
    a szót.
    */
    
    public void dbGorgetettOsszevet() {
        try {
            String query = "SELECT szavak FROM nyelvtanulas.gorgetett";
            st2 = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                String szo = rs.getString("szavak");
                if (szavak_indexe.get(szo) != null) {
                    int gyak = data.get(szavak_indexe.get(szo)).getGyak();
                    data.get(szavak_indexe.get(szo)).setGyak(++gyak);
                    String delete = "DELETE FROM nyelvtanulas.gorgetett WHERE szavak='" + szo + "'";
                    st2.executeUpdate(delete);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    /* Végigmegy a listán és ha a szó "torlendo", akkor törli onnan. Különben ha be volt jelölve az egyszeres
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
                String into = "INSERT INTO nyelvtanulas.gorgetett (szavak) " 
                + "VALUES ('"+ szo +"')";
                try {
                    st.executeUpdate(into);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Szövegbeviteli mezőnél a sorok tördelése
        txaBevitel.setWrapText(true);
        try {
            // Adatbázis létrehozása ha még nem létezik ilyen néven
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/","root","");
            st = con.createStatement();
            
            String sql = "CREATE DATABASE IF NOT EXISTS nyelvtanulas " + 
                    "DEFAULT CHARACTER SET utf8mb4 " +
                    "COLLATE utf8mb4_hungarian_ci";
            st.executeUpdate(sql);
            System.out.println("Adatbázis sikeresen létrehozva!");
            
            // Táblák létrehozása ha még nem léteznek ilyen néven
            System.out.println("Táblák létrehozása .......");
            String ismert = "CREATE TABLE IF NOT EXISTS nyelvtanulas.ismertszavak " +
                "(szavak VARCHAR(100) PRIMARY KEY)";
            // A mondatok mező TEXT, azért, hogy a nagyon hosszú mondatokat is tudja tárolni
            String tanulando = "CREATE TABLE IF NOT EXISTS nyelvtanulas.tanulandoszavak " +
                    "(szavak VARCHAR(100) NOT NULL PRIMARY KEY ," +
                    "mondatok TEXT NOT NULL ," + 
                    "kikerdezes_ideje DATE NULL)";
            String ignoralt = "CREATE TABLE IF NOT EXISTS nyelvtanulas.ignoraltszavak " +
                    "(szavak VARCHAR(100) PRIMARY KEY)";
            String gorgetett = "CREATE TABLE IF NOT EXISTS nyelvtanulas.gorgetett " +
                    "(szavak VARCHAR(100) PRIMARY KEY )";
            st.executeUpdate(ismert);
            st.executeUpdate(tanulando);
            st.executeUpdate(ignoralt);
            st.executeUpdate(gorgetett);
            System.out.println("Táblák sikeresen létrehozva!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        
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