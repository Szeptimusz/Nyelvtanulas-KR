package nyelvtanulas_kr_szakdolgozat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Kremmer Róbert
 */
public class FoablakController implements Initializable {

    String dbUrl = "jdbc:sqlite:";
    static String fileNeve;
    static String minden = "";
    static HashMap<String, Integer> szavak_indexe = new HashMap<>();
    private final ObservableList<Sor> data = FXCollections.observableArrayList();
    
    
     @FXML
    private Button btnChooser;

    @FXML
    private TextArea txaBevitel;

    @FXML
    private TextArea txaMondat;
    
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
    private Label lblTallozasEredmeny;

    @FXML
    private TableView<Sor> tblTablazat;

    @FXML
    private TableColumn<Sor, String> oSzo;

    @FXML
    private TableColumn<Sor, String> oMondat;

    @FXML
    private TableColumn<Sor, Integer> oGyak;
    
    private ChangeListener<Sor> listener;
    
    @FXML
    void futtat() {
        // Korábbi listener eltávolítása
        tblTablazat.getSelectionModel().selectedItemProperty().removeListener(listener);
        // Korábbi HashMap beállítások törlése.
        szavak_indexe.clear();
        // Korábbi lista törlése.
        data.clear();
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
        // Listener beállítása az adatok táblázatba betöltése után
        tblTablazat.getSelectionModel().selectedItemProperty().addListener(listener);
        lblTallozasEredmeny.setText("");
    }

    /**
     * A Tallózás-gomb megnyomása után felugró ablakból kiválasztható a beolvasandó fájl.
     */
    @FXML
    void talloz() {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            fileNeve = selectedFile.getName();
            lblTallozasEredmeny.setText("Tallózás sikeres!");
        } else {
            System.out.println("File is not valid");
            lblTallozasEredmeny.setText("Sikertelen tallózás!");
        }
    }
    
    /**
     * Adatok beolvasása a betallózott fájlból vagy a szövegterületből.
     */
    public void beolvasas() {
        if (fileNeve != null) {
            File f = new File(fileNeve);
            try (Scanner be = new Scanner(f,"Cp1250")) {
                while (be.hasNextLine()) {
                    minden += be.nextLine();
                }
                be.close();
                // Ha egyszer lefuttatuk tallózott fájllal, kiszedjük a fájltnevet, hogy újra dönteni lehessen a tallózás-szövegdoboz között
                // , különben a korábban tallózott fájlnév megmarad és így nem lehet használni a szövegmezőt.
                fileNeve = null;
            } catch(IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            minden = txaBevitel.getText();
            if (minden.equals("")) {
                figyelmeztetes("Figyelem!", "Üres szövegmező! Kérem adjon meg szöveget, vagy használja "
                + "a Tallózás gombot!");
            }
        }
    }

    /**
     * A feldolgozás() metódus előtt szükséges azokat az egymás után többször előforduló karaktereket törölni, amik alapján majd a 
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
    
    /**
     * A beolvasott sorokat .?!-szerint mondatokká, azokat szóköz szerint szavakká vágva tárolja tömbben. A karakterkezelések 
     * után Sor típusú objektumként hozzáadja a megfigyelhető listához.
     */
    public void feldolgozas() {
        // A szöveg szétvágása "." "?" "!" szerint, plusz azok az esetek amikor szóköz van utánuk
        String mondatok [] = minden.split("\\. |\\.|\\? |\\?|\\! |\\!");
        for (int i = 0; i < mondatok.length; i++) {
            // Mondat szétvágása szavakká szóköz vagy vessző szerint
            String[] szok = mondatok[i].split(" |\\, |\\,");
            for (int j = 0; j < szok.length; j++) {
                // Mozaikszavaknál, rövidítéseknél sok pont lehet közel egymáshoz, ilyenkor mindegyiket külön mondatnak
                // veszi és 0, 1 karakteres töredékek keletkeznek mint szó. Ilyen esetekben a szót figyelmen kívül hagyjuk
                if (szok[j].length() < 2) {
                    continue;
                }
                char elsoKarakter = szok[j].charAt(0);
                // Csak a A-Z és a-z kezdőbetűseket engedi feldolgozni és azokat amik idézőjellel kezdődnek
                if ((elsoKarakter < 'A' || elsoKarakter > 'z' || (elsoKarakter > 90 && elsoKarakter < 97))
                        && elsoKarakter != '“' && elsoKarakter != '"') {
                    continue;
                }
                // Ha a szó előtt idézőjel van, akkor levágjuk róla
                if (elsoKarakter == '“' || elsoKarakter == '"') {
                    szok[j] = szok[j].substring(1, szok[j].length());
                }
                char utolsoKarakter = szok[j].charAt(szok[j].length()-1);
                // Megengedjük, hogy az utolsó betű nagy lehessen pl.: a mozaikszavak miatt
                if (utolsoKarakter < 'A' || utolsoKarakter > 'z' || (utolsoKarakter > 90 && utolsoKarakter < 97)) {
                    szok[j] = szok[j].substring(0, szok[j].length()-1);
                }
                szok[j] = szok[j].toLowerCase();
                
                data.add(new Sor(szok[j], mondatok[i], 1));
            }
        }
        // A minden String kiürítése a feldolgozás után, hogy ne foglalja tovább a memóriát
        minden = "";
    }

    /**
     * Azonos szavak törlése a listából, szógyakoriság számolása, egyes szavak indexének tárolása.
     */
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

    /**
     * A kapott tábla szavait lekérdezi az adatbázisból és ha létezik a listában, akkor a lista szavát átnevezi torlendo-re, 
       jelezve, hogy a táblázat megjelenítése előtt törölni kell a listából. Ha a görgetettszavak táblán megy végig és a szó 
       létezik a listában, akkor 1-gyel növeli a gyakoriságát a listában (így biztos, hogy legalább kétszer előfordul globálisan) és 
       törli a táblából a szót.
     * @param tabla: Paraméterként kapott tábla neve
     */
    public void dbOsszevet(String tabla) {
        ArrayList<String> szavak = new ArrayList();
        String query = "SELECT szavak FROM " + tabla;
        try (Connection kapcs = DriverManager.getConnection(dbUrl);
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
                        szavak.add(szo);
                    }
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Nem sikerült az adatbázis-lekérdezés!");
            System.out.println(e.getMessage());
        }
        
        // Görgetett szó előfordult a szövegben, ezért töröljük az adatbázisból
        if (!szavak.isEmpty()) {
            DB.dbTorol(szavak, tabla);
        }
    }
    
    /**
     * Végigmegy a listán és ha a szó "torlendo", akkor törli onnan. Különben ha be volt jelölve az egyszer előforduló
       szavak megjelenítésének tiltása (és így a szavak görgetése) és a szó, csak egyszer fordul elő globálisan,
       akkor törli a listából és hozzáadja a görgetett szavakhoz az adatbázisban.
     */
    public void listaTorlesek() {
        ArrayList<String> szavak = new ArrayList();
        for (int i = 0; i < data.size(); i++) {
            String szo = data.get(i).getSzo();
            if (szo.equals("torlendo")) {
                data.remove(i);
                i--;
            } else if (cxbEgyszer.isSelected() && data.get(i).getGyak() == 1) {
                data.remove(i);
                i--;
                szavak.add(szo);
            }
        }
        // Egyszer előforduló szavak görgetettszavak táblába írása
        if (!szavak.isEmpty()) {
            DB.dbIr(szavak);
        }
    }

    /**
     * A táblázatban kijelölt sor szavát a gomb megnyomása után elmenti az adatbázis ignoraltszavak táblájába.
     */
    @FXML
    void ignoralMent() {
        String szo = tblTablazat.getSelectionModel().getSelectedItem().getSzo();
        DB.dbBeIr("ignoraltszavak",szo);
        atnevezLeptet("letilt");
    }

    /**
     * A táblázatban kijelölt sor szavát a gomb megnyomása után elmenti az adatbázis ismertszavak táblájába.
     */
    @FXML
    void ismertMent() {
        String szo = tblTablazat.getSelectionModel().getSelectedItem().getSzo();
        DB.dbBeIr("ismertszavak",szo);
        atnevezLeptet("letilt");
    }

    /**
     * A gomb megnyomása után megnyit egy új ablakot és átadja neki a szo és mondat String tartalmát.
     * @throws Exception 
     */
    @FXML
    void tanulandoMent() throws Exception{
        String szo = tblTablazat.getSelectionModel().getSelectedItem().getSzo();
        String mondat = tblTablazat.getSelectionModel().getSelectedItem().getMondat();
        ablak(szo, mondat);
        atnevezLeptet("letilt");
    }
    
    public void atnevezLeptet(String szoveg) {
        // A listener figyeli, hogy van-e szó, ami változott, ezért nevezzük át (gomb letiltáshoz kell)
        tblTablazat.getSelectionModel().getSelectedItem().setSzo(szoveg);
        // A hozzáadás után a lista következő elemét jelölje ki
        int i = tblTablazat.getSelectionModel().getSelectedIndex();
        tblTablazat.getSelectionModel().select(i+1);
    }
    
    /**
     * A Tanulandó szó -gomb megnyomásakor új ablakot nyit meg és átadja neki a kijelölt sor szavát és mondatát.
     * @param szo:      A kijelölt sor szava.
     * @param mondat:    A kijelölt sor mondata.
     */
    private void ablak(String szo, String mondat) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Forditas.fxml"));
            Parent root = loader.load();
            
            ForditasController fc = loader.getController();
            if (!szo.equals("")) {
                fc.setSzo(szo);
                fc.setMondat(mondat);
            } else {
                System.out.println("Nincsen szó megadva!");
            }
            
            Scene scene = new Scene(root);
            Stage ablak = new Stage();
            ablak.setResizable(false);
            ablak.initModality(Modality.APPLICATION_MODAL);
            ablak.setScene(scene);
            ablak.setTitle("Fordítás hozzáadása, feltöltés adatbázisba");
            ablak.showAndWait();
        } catch (IOException ex) {
            System.out.println("Hiba: " + ex.getMessage());
        }
    }
    
    /**
     * Az ANKI kártya készítése menüpontra kattintva végigmegy a tanulandószavak táblán és ahol az ANKI mező 0,
     * azokból a sorból olyan txt-fájlt generál, amit az ANKI szótanuló program be tud importálni és
     * szókártyát tud készíteni belőle.
     */
    @FXML
    void anki() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("ANKI kártya készítés");
        alert.setHeaderText(null);
        alert.setContentText("Valóban szeretne minden új tanulandó szóból ANKI szókártyát készíteni?");

        ArrayList<String> szavak = new ArrayList<>();
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            String query = "SELECT szavak, mondatok, forditas FROM tanulandoszavak WHERE ANKI == 0";
            try (Connection kapcs = DriverManager.getConnection(dbUrl);
                PreparedStatement ps = kapcs.prepareStatement(query)) {
                ResultSet eredmeny = ps.executeQuery();
                while (eredmeny.next()) {
                    String szo = eredmeny.getString("szavak");
                    String mondat = eredmeny.getString("mondatok");
                    String forditas = eredmeny.getString("forditas");
                    if (ankiKartyatKeszit(szo, mondat, forditas)) {
                        szavak.add(szo);
                    } else {
                        System.out.println("Hiba történt a kártya készítése során!");
                    }
                }
            } catch (SQLException e) {
                System.out.println("Nem sikerült az adatbázis-lekérdezés!");
                System.out.println(e.getMessage());
            }
            // Ha sikeres volt az ANKI kártya készítés, akkor a táblában átírja az ANKI mezőt 0-ról 1-re.
            if (!szavak.isEmpty()) {
                DB.dbModosit("tanulandoszavak",szavak);
                System.out.println("ANKI kártya készítés sikeres!");
            } else {
                figyelmeztetes("Figyelem!", "Nincsen tanulandó szó amiből szókártya készíthető!");
            }
        } else {
            alert.hide();
        }
    }
    
    /**
     * A kapott szóból, mondatból és fordításból olyan .txt fájlt készít, amit az ANKI szótanuló program be tud importálni 
     * és szókártyákat tud belőle készíteni.
     * @param szo:      A tanulandó szó.
     * @param mondat:   A szóhoz tartozó példamondat.
     * @param forditas: Az általunk korábban megadott fordítása a szónak.
     * @return :        Ha sikerült a fájlba írás igazad ad vissza, ha nem akkor false-t.
     */
    public boolean ankiKartyatKeszit(String szo, String mondat, String forditas) {
        try (FileWriter f = new FileWriter("ankiimport.txt",true); PrintWriter p = new PrintWriter(f)) {
            
            // A mondatban a szó előfordulásainak megkeresése, pontokkal helyessítése és így lyukas szöveg gyártása.
            String lyukasMondat = "";
            String [] szavak = mondat.toLowerCase().split(" ");
            for (int i = 0; i < szavak.length; i++) {
                if (szavak[i].equals(szo)) {
                    int szoHossza = szavak[i].length();
                    String lyuk = "";
                    for (int j = 0; j < szoHossza; j++) {
                        lyuk = lyuk + ".";
                    }
                    szavak[i] = lyuk;
                }
                lyukasMondat += szavak[i] + " ";
            }
            
            p.println(szo + "<br><br>" + mondat + "\t" + forditas + "<br><br>" + lyukasMondat);
            return true;
        } catch(IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    /**
     * A menüből a Kilépés-t választva bezárja a programot.
     */
    @FXML
    void kilep() {
        Platform.exit();
    }

    /**
     * A menüből a Névjegy-et választva információt ad a programról és készítőjéről.
     */
    @FXML
    void nevjegy() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Nyelvtanulás program");
        alert.setHeaderText(null);
        alert.setContentText("Készítette: Kremmer Róbert");
        alert.showAndWait();
    }
    
    /**
     * Probléma esetén az adott szöveggel figyelmeztető ablak ugrik fel.
     * @param cim:       Az ablak címe
     * @param szoveg:    A megjelenített üzenet
     */
    static void figyelmeztetes(String cim, String szoveg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(cim);
        alert.setHeaderText(null);
        alert.setContentText(szoveg);
        alert.showAndWait();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Adatbázis helye relatív módon megadva
        String filePath = new File("").getAbsolutePath();
        dbUrl += filePath.concat("\\nyelvtanulas.db");
        DB.setDbUrl(dbUrl);
        
        // A táblázatban az adott oszlopban megjelenő adat a Sor osztály melyik változójából legyen kiszedve
        oSzo.setCellValueFactory(new PropertyValueFactory<>("szo"));
        oMondat.setCellValueFactory(new PropertyValueFactory<>("mondat"));
        oGyak.setCellValueFactory(new PropertyValueFactory<>("gyak"));

        // Listener előkészítése a futtat() metódushoz
        listener = (v, regi, uj) -> {
            String ujSzo = uj.getSzo();
            if (ujSzo.equals("letilt")) {
                btnIsmert.setDisable(true);
                btnIgnore.setDisable(true);
                btnTanulando.setDisable(true);
            } else {
                btnIsmert.setDisable(false);
                btnIgnore.setDisable(false);
                btnTanulando.setDisable(false);
            }

            // Figyeli, hogy a sor mondata változott-e és azt írja ki a táblázat fölötti szövegterületre
            String mondat = uj.getMondat();
            if (mondat != null) {
                txaMondat.setText(mondat);
            } else {
                txaMondat.setText("");
            }
        };
    }    
}