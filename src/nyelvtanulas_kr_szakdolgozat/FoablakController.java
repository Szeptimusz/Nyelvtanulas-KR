package nyelvtanulas_kr_szakdolgozat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
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

    static String dbUrl = "jdbc:sqlite:";
    static String minden = "";
    static String fajlUtvonal;
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
    private Button btnVisszavon;
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
        // Ha nem tallózott, és szöveget sem írt be, akkor nem futnak le a metódusok, csak figyelmeztető ablakot nyit meg
        if (txaBevitel.getText().equals("") && fajlUtvonal == null) {
            figyelmeztet("Figyelem!", "Üres szövegmező! Kérem adjon meg szöveget, vagy használja "
                + "a Tallózás gombot!");
        } else {
        ButtonType btn = new ButtonType("NE KATTINTSON RÁ!"); 
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Adatok feldolgozása folyamatban... \n"
                + " NE KATTINTSON ERRE AZ ABLAKRA!",btn);
        alert.setHeaderText(null);
        alert.show();    
            
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
        DB.dbOsszevet("ismertszavak",data,szavak_indexe);
        DB.dbOsszevet("ignoraltszavak",data,szavak_indexe);
        DB.dbOsszevet("tanulandoszavak",data,szavak_indexe);
        // Ha be lett pipálva a checkbox
        if (cxbEgyszer.isSelected()) {
            DB.dbOsszevet("gorgetettszavak",data,szavak_indexe);
        }
        listaTorlesek();
        tblTablazat.setItems(data);
        // Listener beállítása az adatok táblázatba betöltése után
        tblTablazat.getSelectionModel().selectedItemProperty().addListener(listener);
        lblTallozasEredmeny.setText("");
        
        alert.hide();
        tajekoztat("Kész!", "Az adatok feldolgozása befejeződött!");
        }
    }

    /**
     * A Tallózás-gomb megnyomása után felugró ablakból kiválasztható a beolvasandó fájl.
     */
    @FXML
    void talloz() {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            fajlUtvonal = selectedFile.getAbsolutePath();
            txaBevitel.setText("");
            lblTallozasEredmeny.setText("Tallózás sikeres!");
        } else {
            System.out.println("File is not valid");
            lblTallozasEredmeny.setText("Sikertelen tallózás!");
        }
    }
    
    /**
     * Adatok beolvasása a betallózott fájlból vagy a szövegterületből. Az egész szöveget egyszerre olvassa be.
     */
    public void beolvasas() {
        if (fajlUtvonal != null) {
            File f = new File(fajlUtvonal);
            try (FileInputStream fis = new FileInputStream(f);){
                byte[] adat = new byte[(int) f.length()];
                fis.read(adat);
                minden = new String(adat, "Cp1250");
                minden = minden.replaceAll("\t|\n|\r", "");
                /* Ha egyszer lefuttatuk tallózott fájllal, kiszedjük a fájltnevet, hogy újra dönteni lehessen 
                   a tallózás-szövegdoboz között különben a korábban tallózott fájlnév megmarad és így nem lehet
                   használni a szövegmezőt.*/
                fajlUtvonal = null;
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            // A szövegdobozos szövegből kiszedjük a tabulátorokat és az új sorokat
            minden = txaBevitel.getText().replaceAll("\t|\n", "");
        }
    }

    /**
     * A feldolgozás() metódus előtt szükséges azokat az egymás után többször előforduló karaktereket törölni, amik alapján majd a 
    splittelés történik ("." "?" "!").
    Végigmegy a teljes szövegen karakterenként, ha a karakter ".?!" , akkor ha a következő karakter is
    ugyanolyan, addig törli a következőket amíg nem talál egy más karaktert. Azzal, hogy csak ezt a 3 karaktert nézi
    * a példamondatok jobban hasonlítanak az eredeti szövegben lévő mondatra.
     */
    public void eloFeldolgozas() {
        StringBuilder sb = new StringBuilder(minden);
        for (int i = 0; i < sb.length()-1; i++) {
            char c = sb.charAt(i);
            if (c == '.' || c == '?' || c == '!' || c == ' ') {
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
     * A beolvasott sorokat .?!-szerint mondatokká, azokat szóköz és vessző szerint szavakká vágva tárolja tömbben. A karakterkezelések 
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
                // veszi és 0, 1 karakter hosszú töredékek keletkeznek mint szó. Ilyen esetekben a szót figyelmen kívül hagyjuk
                if (szok[j].length() < 2) {
                    continue;
                }
                
                // Szó elejének megtisztítása az első szöveges karakterig
                int eleje = 0;
                int vege = szok[j].length()-1;
                while (szok[j].charAt(eleje) < 'A' 
                        || (szok[j].charAt(eleje) > 'z' && szok[j].charAt(eleje) < 193) 
                        || (szok[j].charAt(eleje) > 'Z' && szok[j].charAt(eleje) < 'a') 
                        || (szok[j].charAt(eleje) >= '0' && szok[j].charAt(eleje) <= '9') 
                        || szok[j].charAt(eleje) > 382) {
                    if (eleje == szok[j].length()-1) {
                        break;
                    }
                    eleje++;
                }
                // Ha teljesen elfogyott a szó a tisztítás során akkor nem dolgozza fel
                if (eleje == szok[j].length()-1) {
                        continue;
                } else {
                // Különben ha nem fogyott el a szó, akkor a szó végéről indulva is megtisztítja
                    while (szok[j].charAt(vege) < 'A' 
                            || (szok[j].charAt(vege) > 'z' && szok[j].charAt(vege) < 193) 
                            || (szok[j].charAt(vege) > 'Z' && szok[j].charAt(vege) < 'a') 
                            || (szok[j].charAt(vege) >= '0' && szok[j].charAt(vege) <= '9') 
                            || szok[j].charAt(vege) > 382) {
                        if (vege == 0) {
                            break;
                        }
                        vege--;
                    }
                }
                // Ha még a levágás után is több mint 30 karakter a szó, akkor valószínűleg a belsejében van sok nem megfelelő
                // karakter, ezért nem dolgozzuk fel.
                szok[j] = szok[j].substring(eleje, vege + 1);
                if (szok[j].length() > 30) {
                    continue;
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
        // Lista rendezése szavak szerint, majd addig törli az adott szót, amíg előfordul
        Collections.sort(data,(Sor s1, Sor s2) -> s1.getSzo().compareTo(s2.getSzo()));
        int i = 0;
        while (i < data.size() - 1) {
            szavak_indexe.put(data.get(i).getSzo(), i);
            int db = 1;
            while (data.get(i).getSzo().equals(data.get(i + 1).getSzo())) {
                data.remove(i + 1);
                db++;
                if (i + 1 == data.size()) {
                    break;
                }
            }
            data.get(i).setGyak(db);
            i++;
        }
        /* A lista utolsó szavánál is beállítja az indexes hashmap-et (az lista azonos szavainak törlésénél csak
           az utolsó előtti elemig mentünk el) */
        szavak_indexe.put(data.get(data.size()-1).getSzo(), data.size()-1);
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
        letiltLeptet(true,"ignoraltszavak");
    }

    /**
     * A táblázatban kijelölt sor szavát a gomb megnyomása után elmenti az adatbázis ismertszavak táblájába.
     */
    @FXML
    void ismertMent() {
        String szo = tblTablazat.getSelectionModel().getSelectedItem().getSzo();
        DB.dbBeIr("ismertszavak",szo);
        letiltLeptet(true,"ismertszavak");
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
        if (ForditasController.isTanulandoElmentve()) {
            letiltLeptet(true, "tanulandoszavak");
        }
    }
    
    public void letiltLeptet(boolean tilt, String tabla) {
        // A listener figyeli az objektum tilt változóját, ezért igazra állítjuk a gombok letiltásához
        tblTablazat.getSelectionModel().getSelectedItem().setTilt(tilt);
        // Minden táblázatbeli sor esetén tárolja, hogy melyik táblába lett elmentve
        tblTablazat.getSelectionModel().getSelectedItem().setTabla(tabla);
        // A hozzáadás után a lista következő elemét jelölje ki
        int i = tblTablazat.getSelectionModel().getSelectedIndex();
        tblTablazat.getSelectionModel().select(i+1);
    }
    
    /**
     * A Visszavonás -gombra kattintva (ha volt adatbázisba mentés a 3 gombbal), a 3 gomb tiltását feloldja, törli a korábban
     * adatbázisba írt szót és visszaállítja a tablazat változót null-ra, hogy egy sort többször is lehessen módosítani.
     */
    @FXML
    void visszavon() {
        String tabla = tblTablazat.getSelectionModel().getSelectedItem().getTabla();
        if (tabla != null) {
            tblTablazat.getSelectionModel().getSelectedItem().setTilt(false);
            btnIsmert.setDisable(false);
            btnIgnore.setDisable(false);
            btnTanulando.setDisable(false);
            DB.dbSzotTorol(tabla, tblTablazat.getSelectionModel().getSelectedItem().getSzo());
            tblTablazat.getSelectionModel().getSelectedItem().setTabla(null);
        } else {
            figyelmeztet("Figyelem!", "A kijelölt sornál nem történt változás amit vissza kéne vonni!");
        }
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
        } catch (IOException e) {
            System.out.println("Hiba: " + e.getMessage());
        }
    }
    
    /**
     * Az ANKI kártya készítése menüpontra kattintva végigmegy a tanulandószavak táblán és ahol az ANKI mező 0,
     * azokból a sorból olyan txt-fájlt generál, amit az ANKI szótanuló program be tud importálni és
     * szókártyát tud készíteni belőle.
     */
    @FXML
    void ankiImport() {
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
                tajekoztat("Kártya készítés eredmény", "A kártyák sikeresen elkészítve az ankiimport fájlba!");
                System.out.println("ANKI kártya készítés sikeres!");
            } else {
                figyelmeztet("Figyelem!", "Nincsen tanulandó szó amiből szókártya készíthető!");
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
        // Kiírás FileOutputStream-mel, mert így megadható az utf-8 kódolás (az ANKI program csak ezt tudja beimportálni)
        try (OutputStreamWriter writer =
             new OutputStreamWriter(new FileOutputStream("ankiimport.txt",true), StandardCharsets.UTF_8)) {
            
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
            
            writer.write(szo + "<br><br>" + mondat + "\t" + forditas + "<br><br>" + lyukasMondat + "\n");
            return true;
        } catch(IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
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
            boolean tiltva = uj.isTilt();
            if (tiltva) {
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
        tajekoztat("Nyelvtanulás program", "Készítette: Kremmer Róbert");
    }
    
    /**
     * Megnyit egy ablakot az adott szöveggel, amikor tájékoztató jellegű visszajelzést kell küldeni a felhasználónak.
     * @param cim:      Az ablak címe.
     * @param szoveg    A megjelenített üzenet.
     */
    static void tajekoztat(String cim, String szoveg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,szoveg);
        alert.setTitle(cim);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
    
    /**
     * Probléma esetén az adott szöveggel figyelmeztető ablak ugrik fel.
     * @param cim:       Az ablak címe
     * @param szoveg:    A megjelenített üzenet
     */
    static void figyelmeztet(String cim, String szoveg) {
        Alert alert = new Alert(Alert.AlertType.WARNING,szoveg);
        alert.setTitle(cim);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}