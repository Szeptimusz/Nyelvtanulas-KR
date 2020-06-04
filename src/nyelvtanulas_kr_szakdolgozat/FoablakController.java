package nyelvtanulas_kr_szakdolgozat;

import eu.crydee.syllablecounter.SyllableCounter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static panel.Panel.figyelmeztet;
import static panel.Panel.hiba;
import static panel.Panel.igennem;
import static panel.Panel.tajekoztat;

/**
 * A program indításakor megjelenő ablakot kezelő osztály. Itt történik meg az adatok bevitele, feldolgozása,
 * táblázatban való megjelenítése, majd lehetőség szerint azok adatbázisba mentése. Ebből az
 * ablakből lehet megnyitni a program többi ablakát (menüpontok vagy gomb által).
 * @author Kremmer Róbert
 */
public class FoablakController implements Initializable {

    static String fajlUtvonal;
    static String TablaNevEleje;
    static String forrasNyelvKod;
    static int eredetiOsszesSzo;
    static int toroltSzavak;
    static String mappaUtvonal = System.getProperty("user.dir");
    static HashMap<String, Integer> szavak_indexe = new HashMap<>();
    static HashMap<String, String> nyelvekKodja = new HashMap<>();
    private final ObservableList<Sor> data = FXCollections.observableArrayList();
    
    @FXML
    private TextArea txaBevitel;
    @FXML
    private TextArea txaMondat;
    @FXML
    private CheckBox cxbEgyszer;
    @FXML
    private Button btnIsmert;
    @FXML
    private Button btnTanulando;
    @FXML
    private Button btnIgnore;
    @FXML
    private Label lblTallozasEredmeny;
    @FXML
    private Label lblSzazalekIsmert;
    @FXML
    private Label lblIsmertseg;
    @FXML
    private Label lblOlvashatosag;
    @FXML
    private Label lblOlvashato;
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

    /**
     * A Tallózás-gomb megnyomása után felugró ablakból kiválasztható a beolvasandó fájl.
     * Alapesetben a program mappájából lehet tallózni, de utána már megjegyzi az utolsó használt mappát.
     * A művelet sikerességéről a gomb melletti címkében üzenet jelenik meg és a szövegbeviteli
     * mezőt üresre állítja.
     */
    @FXML
    public void talloz() {
        FileChooser fc = new FileChooser();
        File hasznaltMappa = new File(mappaUtvonal);
        fc.setInitialDirectory(hasznaltMappa);
        File selectedFile = fc.showOpenDialog(null);
        
        if (selectedFile != null) {
            fajlUtvonal = selectedFile.getAbsolutePath();
            txaBevitel.setText("");
            lblTallozasEredmeny.setText("Tallózás sikeres!");
            mappaUtvonal = fajlUtvonal.substring(0, fajlUtvonal.lastIndexOf('\\') + 1);
        } else {
            lblTallozasEredmeny.setText("Sikertelen tallózás!");
        }
    }
    
    /**
     * Az 'Adatok feldolgozása'-gomb megnyomásakor lefutó metódus. Csak akkor kezdődik meg
     * a bevitt adatok feldolgozása, ha tallózással, vagy a szövegbeviteli mezót használva meg lett adva bemenő adat, illetve
     * ha lett kiválasztva forrás nyelv a legördülő listából. A feldolgozás előtt a szavakat tároló lista és a szavak indexét tároló
     * HashMap tartalmát törli, a mondatok szövegmezőt üresre állítja és a táblázathoz rendelt listenert eltávolítja. Beállítja az
     * adott nyelvhez tartozó kódot, ami alapján az egyedi nevű táblákat létre is hozza az adatbázisban. Lefut az előfeldolgozás, a feldolgozás, az
     * azonos szavak törlése - és így a szógyakoriság számlálása - majd az egyedi szavakat összeveti az adatbázis szavaival és 
     * törli a listából ami már szerepel az adatbázisban. A már szinkronizált lista tartalmát megjeleníti a táblázatban és hozzáadja
     * a táblázathoz a listenert. A tallózásról tájékoztató címkét kiüríti. Megjeleníti, hogy a szöveg hány százaléka ismert + tanulando + ignoralt szó.
     * @throws java.io.IOException
     */
    @FXML
    public void futtat() throws IOException{
        // Ha nem tallózott, és szöveget sem írt be, akkor nem futnak le a metódusok, csak figyelmeztető ablakot nyit meg
        if (txaBevitel.getText().equals("") && fajlUtvonal == null) {
            figyelmeztet("Figyelem!", "Üres szövegmező! Kérem adjon meg szöveget,"
                         + " vagy használja a Tallózás gombot!");
            
        } else if (cbxForras.getValue() == null) {
            figyelmeztet("Figyelem!", "Kérem adja meg a forrásnyelvet is!");
            
        } else {
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
            txaMondat.setText("");
            lblSzazalekIsmert.setText("");
            // A megadott forrásnyelv beállítása (pl: 'Német' -> 'de')
            forrasNyelvKod = nyelvekKodja.get(cbxForras.getValue());
            TablaNevEleje = forrasNyelvKod + "_"; 
            beolvasasFeldolgozas();
            azonosakTorlese();
            DB.tablakatKeszit(TablaNevEleje);
            DB.adatbazistListavalOsszevet(TablaNevEleje + "szavak",data,szavak_indexe);
            DB.adatbazistListavalOsszevet(TablaNevEleje + "tanulando",data,szavak_indexe);
            listaTorlesek();
            // A táblázatban már a gyakoriság szerint jelenjenek meg a szavak
            data.sort((s1, s2) -> Integer.compare(s2.getGyak(), s1.getGyak()));
            tblTablazat.setItems(data);
            // Listener beállítása az adatok táblázatba betöltése után
            tblTablazat.getSelectionModel().selectedItemProperty().addListener(listener);
            lblTallozasEredmeny.setText("");
            lblSzazalekIsmert.setText((int)((double)toroltSzavak / eredetiOsszesSzo * 10000) / 100.0 + " %");

            ablak.hide();

            if (data.isEmpty())
                figyelmeztet("Figyelem!", "A nem megfelelő karakterek eltávolítása és az adatbázis szinkronizálás után nem "
                        + "maradt megjeleníthető eredmény!");
            else
                tajekoztat("Kész!", "Az adatok feldolgozása befejeződött!");  
        }
    }

    
    /**
     * Ha lett megadva tallózással fájlútvonal, akkor egyben beolvassa a fájlból az adatokat a Cp1250-es kódtábla alapján, majd
     * beleteszi egy Stringbe. Eltávolítja belőle a tabulátorokat az új sorokat és a felesleges szóközöket, majd az eredménnyel meghívja a feldolgozást.
     * Végül azért, hogy a következő futtatás előtt is dönteni lehessen a tallózás-
     * szövegmező között, a fájlútvonalat null-ra állítja.
     * Ha nem lett megadva fájlútvonal akkor a szövegterületről olvassa ki a szöveget, eltávolítja a tabulátorokat és az új sorokat,
     * majd meghívja a kapott Stringgel az előfeldolgozást és a feldolgozást.
     * @throws java.io.IOException
     */
    public void beolvasasFeldolgozas() throws IOException{
        if (fajlUtvonal != null) {
            feldolgozas(new String(Files.readAllBytes(Paths.get(fajlUtvonal)),"Cp1250")
                        .replaceAll("\t|\n|\r|\\  ", ""));
            fajlUtvonal = null;
        } else {
            feldolgozas(txaBevitel.getText().replaceAll("\t|\n|\\  ", ""));
        }
    }
   
    /**
     * A kapott szöveget "." "?" "!" és ezek szóközzel ellátott verziói alapján splitteli a mondatok tömbbe, majd a mondatok tömböt
     * " " ", " ";" "—" és "," alapján splitteli a szavak tömbbe. Ha a szó legalább 2 karakter akkor megtisztítja a nem megfelelő karakterektől elölről és hátulról, ha ezek után 
     * legalább 2 de maximum 30 karakter hosszú akkor kisbetűssé alakítva a szintén megtisztított mondattal együtt -Sor típusú objektumként-
     * hozzáadja a listához. A Flesch-Kincaid képlet alapján kiszámítja a szöveg olvahatósági indexét és kiírja címkébe.
     * @param szoveg A feldolgozandó szöveg
     */
    public void feldolgozas(String szoveg) {
        int szotagokSzama = 0;
        // A szöveg szétvágása mondatokká
        String mondatok [] = szoveg.split("\\. |\\.|\\? |\\?|\\! |\\!");
        SyllableCounter sc = new SyllableCounter();
        for (String mondat : mondatok) {
            // Mondat szétvágása szavakká
            String[] szok = mondat.split(" |\\, |\\,|\\; |\\;|\\—");
            for (String szo : szok) {
                // Mozaikszavaknál, rövidítéseknél sok pont lehet közel egymáshoz, ilyenkor mindegyiket külön mondatnak
                // veszi és 0, 1 karakter hosszú töredékek keletkeznek mint szó. Ilyen esetekben a szót figyelmen kívül hagyjuk
                if (szo.length() < 2) continue;

                szotagokSzama += sc.count(szo);
                
                String megtisztitottSzo = megtisztit(szo);
                // Ha még a megtisztítás után is több mint 30 karakter a szó, akkor valószínűleg a belsejében van sok nem megfelelő
                // karakter, ezért nem dolgozzuk fel. Illetve ha 2-nél kevesebb karakterből áll.
                int szoHossza = megtisztitottSzo.length();
                if (szoHossza > 30 || szoHossza < 2) continue;
        
                data.add(new Sor(megtisztitottSzo.toLowerCase(), megtisztit(mondat), 1));
            }
        }
        eredetiOsszesSzo = data.size();
        double fleschScore = 206.835 - 1.015 * ((double)eredetiOsszesSzo / mondatok.length)
                - 84.6 * ((double)szotagokSzama / eredetiOsszesSzo);
        
        if (fleschScore > 90) lblOlvashatosag.setText((int)fleschScore + "  (Very easy to read)");
        else if (fleschScore > 80) lblOlvashatosag.setText((int)fleschScore + "  (Easy to read)");
        else if (fleschScore > 70) lblOlvashatosag.setText((int)fleschScore + "  (Fairly easy to read)");
        else if (fleschScore > 60) lblOlvashatosag.setText((int)fleschScore + "  (Plain English)");
        else if (fleschScore > 50) lblOlvashatosag.setText((int)fleschScore + "  (Fairly difficult to read)");
        else if (fleschScore > 30) lblOlvashatosag.setText((int)fleschScore + "  (Difficult to read)");
        else lblOlvashatosag.setText((int)fleschScore + "  (Very difficult to read)");
    }

    /**
     * A kapott szöveg elejét és végét megtisztítja azoktól a karakterektől amik nem a támogatott idegen nyelvek részei.
     * Ha a tisztítás során minden karakter elfogy, akkor üres String-et ad vissza.
     * @param szoveg  A megtisztítandó szöveg
     * @return Visszadja a megtisztított szöveget
     */
    public String megtisztit(String szoveg) {
        int eleje = 0;
        int vege = szoveg.length()-1;
        
        // Szó elejének megtisztítása az első szöveges karakterig
        char karakter = szoveg.charAt(eleje);
        while (karakter < 'A' 
                || (karakter > 'z' && karakter < 193) 
                || (karakter > 'Z' && karakter < 'a') 
                || karakter > 382) {
            if (eleje == szoveg.length()-1) return "";
            
            eleje++;
            karakter = szoveg.charAt(eleje);
        }
        
        // Különben ha nem fogyott el a szó, akkor a szó végéről indulva is megtisztítja
        karakter = szoveg.charAt(vege);
        while (karakter < 'A' 
                || (karakter > 'z' && karakter < 193) 
                || (karakter > 'Z' && karakter < 'a') 
                || karakter > 382) {
            if (vege == 0) return "";
            
            vege--;
            karakter = szoveg.charAt(vege);
        }
        return szoveg.substring(eleje, vege + 1);
    }
    
    /**
     * Beépített rendezéssel rendezi a Sor típusú elemekből álló listát a szavak alapján, így az azonos szavak egymás mellé kerülnek. Majd addig 
     * törli az azonos szavakat, amíg csak egyetlen példány marad, közben számolja a szavak gyakoriságát. A megmaradó szó gyakoriság 
     * mezőjének értékére beállítja a törlések során számolt gyakoriságot.
     * Egy HashMap-ben tárolja az így már egyszer előforduló szóhoz tartozó lista-indexet, így keresés nélkül megállapítható, hogy 
     * egy adott szó benne van-e a listában.
     */
    public void azonosakTorlese() {
        data.sort((s1, s2) -> s1.getSzo().compareTo(s2.getSzo()));
        data.add(new Sor("", "", 1));
        LinkedList<Sor> csatoltLista = new LinkedList<>(data);
        ListIterator<Sor> it = csatoltLista.listIterator();
        int i = 0;
        while (it.hasNext()) {
            Sor s = it.next();
            s.mondatotHozzaad(s.getMondat());
            szavak_indexe.put(s.getSzo(), i);
            int db = 1;
            if (it.hasNext()) {
                Sor s2 = it.next();
                while(it.hasNext() && s.getSzo().equals(s2.getSzo())) {
                    s.mondatotHozzaad(s2.getMondat());
                    it.remove();
                    db++;
                    s2 = it.next();
                }
                it.previous();
            }
            csatoltLista.get(i).setGyak(db);
            i++;
            s.azonosakTorleseListabol();
        }
        data.clear();
        data.addAll(csatoltLista);
        data.remove(data.size()-1);
    }
    
    /**
     * Végigmegy a listán és ha a szó "torlendo", akkor törli onnan. Különben ha be volt jelölve az egyszer előforduló
     * szavak megjelenítésének tiltása és egyszer fordul elő a szó, akkor szintén törli a listából. Számolja a törölt szavak gyakoriságát.
     */
    public void listaTorlesek() {
        toroltSzavak = 0;
        for (int i = 0; i < data.size(); i++) {
            String szo = data.get(i).getSzo();
            
            if (cxbEgyszer.isSelected() && data.get(i).getGyak() == 1 && !szo.equals("torlendo")) {
                data.remove(i--);
                eredetiOsszesSzo--;
            } else if (szo.equals("torlendo")) {
                toroltSzavak += data.get(i).getGyak();
                data.remove(i--);
            }
        }
    }

    /**
     * Ha a feldolgozás után a lista nem üres, akkor a táblázatban kijelölt sor szavát a gomb megnyomása 
     * után elmenti a szavak táblába ignoralt állapottal, majd letiltja a sorhoz tartozó gombokat és léptet a táblázatban.
     */
    @FXML
    public void ignoralMent() {
        if (!ellenoriz().isEmpty()) {
            figyelmeztet("Figyelem!", ellenoriz());
            return;
        }
        String szo = tblTablazat.getSelectionModel().getSelectedItem().getSzo();
        DB.szotBeirAdatbazisba(TablaNevEleje + "szavak",szo, "ignoralt");
        letiltLeptet(TablaNevEleje + "szavak");

    }
   
    /**
     * Ha a feldolgozás után a lista nem üres, akkor a táblázatban kijelölt sor szavát a gomb megnyomása 
     * után elmenti a szavak táblába ismert állapottal, majd letiltja a sorhoz tartozó gombokat és léptet a táblázatban.
     */
    @FXML
    public void ismertMent() {
        if (!ellenoriz().isEmpty()) {
            figyelmeztet("Figyelem!", ellenoriz());
            return;
        }
        String szo = tblTablazat.getSelectionModel().getSelectedItem().getSzo();
        DB.szotBeirAdatbazisba(TablaNevEleje + "szavak",szo, "ismert");
        letiltLeptet(TablaNevEleje + "szavak");
    }

    /**
     * Ha a feldolgozás után a lista nem üres, akkor a gomb megnyomása után megnyit egy új ablakot és 
     * átadja neki a szo és mondat String tartalmát. Ha az új ablakban a tanulandó szó sikeresen el lett mentve,
     * akkor letiltja a sorhoz tartozó gombokat és léptet a táblázatban.
     * @throws Exception Hiba esetén kivételt dob
     */
    @FXML
    public void tanulandoMent() throws Exception{
        if (!ellenoriz().isEmpty()) {
            figyelmeztet("Figyelem!", ellenoriz());
            return;
        }
        
        String szo = tblTablazat.getSelectionModel().getSelectedItem().getSzo();
        List<String> mondatok = tblTablazat.getSelectionModel().getSelectedItem().getMondatok();
        
        ablakotNyit("Forditas.fxml", "Fordítás hozzáadása, feltöltés adatbázisba", szo, mondatok);
        if (ForditasController.isTanulandoElmentve()) {
            letiltLeptet(TablaNevEleje + "tanulando");
            // Miután elmentette és léptetett a táblázatban, visszaállítja a ForditasController osztályban false-ra
            ForditasController.setTanulandoElmentve(false);
        }
    }
    
    /**
     * Ellenőrzi, hogy a listában vannak-e elemek
     * @return Ha üres a lista, akkor üzenetet ad vissza, különben üres Stringet
     */
    public String ellenoriz() {
        return data.isEmpty() ? "Nem történt adatfeldolgozás, kérem adjon meg bemenő adatot"
                    + " és válassza az 'Adatok feldolgozása' gombot!" : "";
    }
    
    /**
     * Letiltja az adott táblázatbeli sor 3 gombjának (ismert, tanulandó, ignorált) használatát, tárolja
     * a tábla nevét ahova a beírás történt és kijelöli a táblázat következő elemét. Ha a táblázat utolsó
     * eleménél tart, akkor a léptetés visszafelé történik, így az utolsó sor 3 gombja letiltottnak 
     * fog látszódni kijelöléskor.
     * @param tabla A tábla, ahova az adatbázisban a szó el lett mentve
     */
    public void letiltLeptet(String tabla) {
        Sor kivalasztottSor = tblTablazat.getSelectionModel().getSelectedItem();
        kivalasztottSor.setTilt(true);
        kivalasztottSor.setTabla(tabla);
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
     * hogy később is ellenőrizni lehessen történt-e változás.
     */
    @FXML
    public void visszavon() {
        if (!ellenoriz().isEmpty()) {
            figyelmeztet("Figyelem!", ellenoriz());
            return;
        }
        String tabla = tblTablazat.getSelectionModel().getSelectedItem().getTabla();
        if (tabla != null) {
            Sor kivalasztottSor = tblTablazat.getSelectionModel().getSelectedItem();
            kivalasztottSor.setTilt(false);
            btnIsmert.setDisable(false);
            btnIgnore.setDisable(false);
            btnTanulando.setDisable(false);
            DB.szotTorolAdatbazisbol(tabla, kivalasztottSor.getSzo());
            kivalasztottSor.setTabla(null);
        } else {
            figyelmeztet("Figyelem!", "A kijelölt sornál nem történt változás amit vissza kéne vonni!");
        }
    }

    /*** Új ablakot nyit meg, ahol ANKI-import fájl készíthető.*/
    @FXML
    public void ankiImportAblak() { ablakotNyit("Anki.fxml", "ANKI-import elkészítése", "", null); }
    
    /*** Új ablakban megjeleníti az adott nyelvhez tartozó statisztikát*/
    @FXML
    public void statisztikaAblak() { ablakotNyit("Statisztika.fxml", "Adatbázis-statisztika", "", null); }
    
    /*** Új ablakban megjeleníti a szókártya-kikérdezés felületet*/
    @FXML
    public void kikerdezesAblak() { ablakotNyit("Kikerdezes.fxml","Szavak kikérdezése szókártyákkal","",null); }
    
    /**
     * A kapott fxml fájlnév alapján új ablakot nyit meg. Ha a szó paraméter nem üres, akkor a fordítás ablakot
     * nyitja meg, ekkor az új ablakhoz tartozó controller osztályban beállítja a szó, mondat és forrás nyelv kód mezők értékeit.
     * @param fxmlFajl     Az fxml fájl neve
     * @param ablakCim     A megnyitott ablak címe
     * @param szo          Fordítás ablak esetében a kapott szó
     * @param mondat       Fordítás ablak esetében a kapott mondat
     */
    private void ablakotNyit(String fxmlFajl, String ablakCim, String szo, List<String> mondatok) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFajl));
            Parent root = loader.load();
            if (!szo.isEmpty()) {
                ForditasController fc = loader.getController();
                fc.setSzo(szo);
                fc.setMondatok(mondatok);
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
     * @param combobox  A legördülő lista neve
     * @param hashmap   Hashmap neve
     */
    public static void nyelvekBeallitasa(ComboBox<String> combobox, HashMap<String, String> hashmap) {
        String roviditettNyelv [] = {"en","es","fr","de","it","pt","nl","pl","da","cs","sk","sl"};
        String teljesNyelv [] = {"Angol","Spanyol","Francia","Német","Olasz","Portugál","Holland","Lengyel","Dán","Cseh","Szlovák","Szlovén"};
        combobox.getItems().addAll(teljesNyelv);
        for (int i = 0; i < teljesNyelv.length; i++) {
            hashmap.put(teljesNyelv[i], roviditettNyelv[i]);
        }
    } 

    /**
     * A program indulásakor a DB osztály osztályváltozójára beállítja az adatbázis elérési útvonalát, ha nincsen 
     * adatbázis, akkor előtte létrehozza a projekt mappájába. Beállítja a Főablak legördülő listájának nyelveit.
     * Megadja, hogy a táblázat egy adott oszlopának értéke a Sor osztály melyik mezőjéből legyen kiszedve. Az ismert-tanulandó-
     * ignorált gombok letiltásához és a táblázat feletti mondatkiíráshoz definiál egy listenert, amit még nem rendel hozzá semmihez.
     * 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Adatbázis elérési útvonalát beállítja, ha nincs adatbázis akkor létrehozza
        DB.adatbazistKeszit("\\nyelvtanulas.db");

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
            
            // Figyeli, hogy a sor mondata változott-e és azt írja ki a táblázat fölötti szövegterületre, kijelöli az adott szót
            String mondat = uj.getMondat();
            String szo = uj.getSzo();
            if (mondat != null) {
                txaMondat.setText(mondat);
                txaMondat.selectRange(mondat.toLowerCase().indexOf(szo.toLowerCase()), 
                                      mondat.toLowerCase().indexOf(szo.toLowerCase()) + szo.length());
            } else {
                txaMondat.setText("");
            }
            
            // Hotkey-k beállítása a főablak 3 elmentési és a visszavonási gombjára
            btnTanulando.getScene().setOnKeyPressed((final KeyEvent keyEvent) -> {
                if (keyEvent.getCode() == KeyCode.DIGIT1) {
                    try {
                        ismertMent();
                    } catch (Exception ex) { Logger.getLogger(FoablakController.class.getName()).log(Level.SEVERE, null, ex); }
                    keyEvent.consume();
                }
                
                if (keyEvent.getCode() == KeyCode.DIGIT2) {
                    try {
                        tanulandoMent();
                    } catch (Exception ex) { Logger.getLogger(FoablakController.class.getName()).log(Level.SEVERE, null, ex); }
                    keyEvent.consume();
                }
                
                if (keyEvent.getCode() == KeyCode.DIGIT3) {
                    try {
                        ignoralMent();
                    } catch (Exception ex) { Logger.getLogger(FoablakController.class.getName()).log(Level.SEVERE, null, ex); }
                    keyEvent.consume();
                }
                
                if (keyEvent.getCode() == KeyCode.DIGIT4) {
                    try {
                        visszavon();
                    } catch (Exception ex) { Logger.getLogger(FoablakController.class.getName()).log(Level.SEVERE, null, ex); }
                    keyEvent.consume();
                }
            });
        };
    }
    
    /**
     * A menüből a Kilépés-t választva megerősítést vár a kilépésre, igen válasz
     * esetén bezárja a programot.
     */
    @FXML
    public void kilep() {
        if (igennem("Kilépés megerősítés", "Valóban be szeretné zárni a programot?")) {
            Platform.exit();
        }
    }

    /**
     * A menüből a Névjegy-et választva új ablakot nyit meg, ahol tájékoztat a program készítőjéről,
     * a verziószámról és megnyitható böngészőben a fejlesztői dokumentáció osztályokat, metódusokat és függvényeket 
     * leíró része.
     * @throws java.lang.Exception Hiba esetén kivételt dob
     */
    @FXML
    public void nevjegy() throws Exception {
        ablakotNyit("Nevjegy.fxml", "Nyelvtanulás program","",null);
    }
}