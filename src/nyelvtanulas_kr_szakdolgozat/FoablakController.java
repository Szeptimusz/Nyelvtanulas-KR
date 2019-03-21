package nyelvtanulas_kr_szakdolgozat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static panel.Panel.figyelmeztet;
import static panel.Panel.hiba;
import static panel.Panel.tajekoztat;

/**
 *
 * @author Kremmer Róbert
 */
public class FoablakController implements Initializable {

    static String adatbazisUtvonal = "jdbc:sqlite:";
    static String minden = "";
    static String fajlUtvonal;
    static String TablaNevEleje;
    static String forrasNyelvKod;
    static HashMap<String, Integer> szavak_indexe = new HashMap<>();
    static HashMap<String, String> nyelvekKodja = new HashMap<>();
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
    @FXML
    private ComboBox<String> cbxForras;
    
    private ChangeListener<Sor> listener;

    @FXML
    void futtat() {
        // Ha nem tallózott, és szöveget sem írt be, akkor nem futnak le a metódusok, csak figyelmeztető ablakot nyit meg
        if (txaBevitel.getText().equals("") && fajlUtvonal == null) {
            figyelmeztet("Figyelem!", "Üres szövegmező! Kérem adjon meg szöveget, vagy használja "
                + "a Tallózás gombot!");
            
        } else if (cbxForras.getValue() == null){
            figyelmeztet("Figyelem!", "Kérem adja meg a forrásnyelvet is!");
            
        } else {
            try {
                // Töltés ablak a feldolgozás alatt
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Toltes.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage ablak = new Stage();
                ablak.setResizable(false);
                ablak.initModality(Modality.APPLICATION_MODAL);
                ablak.setScene(scene);
                ablak.setTitle("Feldolgozás");
                ablak.show();

                // Korábbi listener eltávolítása
                tblTablazat.getSelectionModel().selectedItemProperty().removeListener(listener);
                // Korábbi HashMap beállítások törlése.
                szavak_indexe.clear();
                // Korábbi lista törlése.
                data.clear();
                // A megadott forrásnyelv beállítása (pl: 'Német' -> 'de')
                forrasNyelvKod = nyelvekKodja.get(cbxForras.getValue());
                TablaNevEleje = forrasNyelvKod + "_";
                beolvasas();
                eloFeldolgozas();
                feldolgozas();
                azonosakTorlese();
                DB.tablakatKeszit(TablaNevEleje);
                DB.adatbazistListavalOsszevet(TablaNevEleje + "szavak",data,szavak_indexe, "ismertignoralt");
                DB.adatbazistListavalOsszevet(TablaNevEleje + "tanulando",data,szavak_indexe, "tanulando");
                listaTorlesek();
                tblTablazat.setItems(data);
                // Listener beállítása az adatok táblázatba betöltése után
                tblTablazat.getSelectionModel().selectedItemProperty().addListener(listener);
                lblTallozasEredmeny.setText("");

                ablak.hide();
                tajekoztat("Kész!", "Az adatok feldolgozása befejeződött!");
            } catch (IOException e) {
                hiba("Hiba!",e.getMessage());
            }
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
                hiba("Hiba!",e.getMessage());
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
     a példamondatok jobban hasonlítanak az eredeti szövegben lévő mondatra.
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
                // karakter, ezért nem dolgozzuk fel. Illetve ha 2-nél kevesebb karakterből áll.
                szok[j] = szok[j].substring(eleje, vege + 1);
                if (szok[j].length() > 30 || szok[j].length() < 2) {
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
     * Azonos szavak törlése a listából, az így megmaradó egyedi szavak lista-indexének tárolása valamint szógyakoriság számolása.
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
       akkor törli a listából és hozzáadja a szavak táblához görgetett állapottal.
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
            }
        }
    }

    /**
     * Ha a feldolgozás után a lista nem üres, akkor a táblázatban kijelölt sor szavát a gomb megnyomása 
     * után elmenti a szavak táblába ignoralt állapottal.
     */
    @FXML
    void ignoralMent() {
        if (data.isEmpty()) {
            figyelmeztet("Figyelem!", "Nem történt adatfeldolgozás, kérem adjon meg bemenő adatot"
                    + " és válassza az 'Adatok feldolgozása' gombot!");
        } else {
            String szo = tblTablazat.getSelectionModel().getSelectedItem().getSzo();
            DB.szotBeirAdatbazisba(TablaNevEleje + "szavak",szo, "ignoralt");
            letiltLeptet(TablaNevEleje + "szavak");
        }
    }

    /**
     * Ha a feldolgozás után a lista nem üres, akkor a táblázatban kijelölt sor szavát a gomb megnyomása 
     * után elmenti a szavak táblába ismert állapottal.
     */
    @FXML
    void ismertMent() {
        if (data.isEmpty()) {
            figyelmeztet("Figyelem!", "Nem történt adatfeldolgozás, kérem adjon meg bemenő adatot"
                    + " és válassza az 'Adatok feldolgozása' gombot!");
        } else {
            String szo = tblTablazat.getSelectionModel().getSelectedItem().getSzo();
            DB.szotBeirAdatbazisba(TablaNevEleje + "szavak",szo, "ismert");
            letiltLeptet(TablaNevEleje + "szavak");
        }
    }

    /**
     * Ha a feldolgozás után a lista nem üres, akkor a gomb megnyomása után megnyit egy új ablakot és 
     * átadja neki a szo és mondat String tartalmát.
     * @throws Exception 
     */
    @FXML
    void tanulandoMent() throws Exception{
        if (data.isEmpty()) {
            figyelmeztet("Figyelem!", "Nem történt adatfeldolgozás, kérem adjon meg bemenő adatot"
                    + " és válassza az 'Adatok feldolgozása' gombot!");
        } else {
            String szo = tblTablazat.getSelectionModel().getSelectedItem().getSzo();
            String mondat = tblTablazat.getSelectionModel().getSelectedItem().getMondat();
            // Új ablakot nyit meg és átadja neki a kijelölt sor szavát, mondatát és a forrásnyelv kódját.
            ablakotNyit("Forditas.fxml", "Fordítás hozzáadása, feltöltés adatbázisba", szo, mondat);
            if (ForditasController.isTanulandoElmentve()) {
                letiltLeptet(TablaNevEleje + "tanulando");
                // Miután elmentette és léptetett a táblázatban, visszaállítja a ForditasController osztályban false-ra
                ForditasController.setTanulandoElmentve(false);
            }
        }
    }
    
    /**
     * Letiltja az adott táblázatbeli sor gombjainak a használatát, tárolja
     * a tábla nevét ahova a beírás történt és kijelöli a táblázat következő elemét.
     * @param tabla: A kapott tábla, ahova az adatbázisban a szó el lett mentve
     */
    public void letiltLeptet(String tabla) {
        tblTablazat.getSelectionModel().getSelectedItem().setTilt(true);
        tblTablazat.getSelectionModel().getSelectedItem().setTabla(tabla);
        int i = tblTablazat.getSelectionModel().getSelectedIndex();
        if (i + 1 < data.size()) {
            tblTablazat.getSelectionModel().select(i+1);
        } else {
            tblTablazat.getSelectionModel().select(i-1);
        }
    }
    
    /**
     * A Visszavonás -gombra kattintva (ha a feldolgozás után a lista nem üres és ha volt adatbázisba mentés a 3 gombbal), 
     * a 3 gomb tiltását feloldja, törli a korábban adatbázisba írt szót és visszaállítja a tabla változót null-ra, 
     * hogy egy sort többször is lehessen módosítani.
     */
    @FXML
    void visszavon() {
        if (data.isEmpty()) {
            figyelmeztet("Figyelem!", "Nem történt adatfeldolgozás, kérem adjon meg bemenő adatot"
                    + " és válassza a 'Adatok feldolgozása' gombot");
        } else {
            String tabla = tblTablazat.getSelectionModel().getSelectedItem().getTabla();
            if (tabla != null) {
                tblTablazat.getSelectionModel().getSelectedItem().setTilt(false);
                btnIsmert.setDisable(false);
                btnIgnore.setDisable(false);
                btnTanulando.setDisable(false);
                DB.szotTorolAdatbazisbol(tabla, tblTablazat.getSelectionModel().getSelectedItem().getSzo());
                tblTablazat.getSelectionModel().getSelectedItem().setTabla(null);
            } else {
                figyelmeztet("Figyelem!", "A kijelölt sornál nem történt változás amit vissza kéne vonni!");
            }
        }
    }

    // Új ablakot nyit meg, ahol a nyelv megadása után ANKI-import fájl készíthető.
    @FXML
    void ankiImportAblak() {
        ablakotNyit("Anki.fxml", "ANKI-import elkészítése", "", "");
    }
    
    // Külön ablakban megjeleníti az adott nyelvhez tartozó statisztikát
    @FXML
    void statisztikaAblak() {
        ablakotNyit("Statisztika.fxml", "Adatbázis-statisztika", "", "");
    }
    
    /**
     * A kapott fxml fájl alapján új ablakot nyit meg.
     * @param fxmlFajl:     A kapott fxml fájl
     * @param ablakCim:     A megnyitott ablak címe
     * @param szo:          Fordítás ablak esetében a kapott szó
     * @param mondat        Fordítás ablak esetében a kapott mondat
     */
    private void ablakotNyit(String fxmlFajl, String ablakCim, String szo, String mondat) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFajl));
            Parent root = loader.load();
            if (!szo.isEmpty()) {
                ForditasController fc = loader.getController();
                fc.setSzo(szo);
                fc.setMondat(mondat);
                fc.setForrasNyelvKod(forrasNyelvKod);
            }
            Scene scene = new Scene(root);
            Stage ablak = new Stage();
            ablak.setResizable(false);
            ablak.initModality(Modality.APPLICATION_MODAL);
            ablak.setScene(scene);
            ablak.setTitle(ablakCim);
            ablak.showAndWait();
        } catch (IOException e) {
            hiba("Hiba!",e.getMessage());
        }
    }
    
    /**
     * A kapott comboboxba beállítja a nyelvek teljes nevét, majd hashmap-ben tárolja a hozzá tartozó rövidítést.
     * @param combobox  A kapott legördülő lista
     * @param hashmap   A kapott hashmap a teljesnév-rövidítettnév tárolására
     */
    public static void nyelvekBeallitasa(ComboBox<String> combobox, HashMap<String, String> hashmap) {
        String roviditettNyelv [] = {"en","es","fr","de","it","pt","nl","pl","da","cs","sk","sl"};
        String teljesNyelv [] = {"Angol","Spanyol","Francia","Német","Olasz","Portugál","Holland","Lengyel","Dán","Cseh","Szlovák","Szlovén"};
        combobox.getItems().addAll(teljesNyelv);
        for (int i = 0; i < teljesNyelv.length; i++) {
            hashmap.put(teljesNyelv[i], roviditettNyelv[i]);
        }
    } 

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Adatbázis helye relatív módon megadva
        String utvonal = new File("").getAbsolutePath();
        adatbazisUtvonal += utvonal + ("\\nyelvtanulas.db");
        
        // Legördülő lista nyelveinek beállítása
        nyelvekBeallitasa(cbxForras, nyelvekKodja);
        
        // A táblázatban az adott oszlopban megjelenő adat a Sor osztály melyik mezőjéből legyen kiszedve
        oSzo.setCellValueFactory(new PropertyValueFactory<>("szo"));
        oMondat.setCellValueFactory(new PropertyValueFactory<>("mondat"));
        oGyak.setCellValueFactory(new PropertyValueFactory<>("gyak"));

        // Listener előkészítése a futtat() metódushoz
        listener = (v, regi, uj) -> {
            // Lekérdezi, hogy az adott sor gombjainak tiltása true-ra vagy false-ra változott
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
}