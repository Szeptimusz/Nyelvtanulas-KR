package nyelvtanulas_kr_szakdolgozat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
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
     * A művelet sikerességéről a gomb melletti címkében üzenet jelenik meg és a szövegbeviteli
     * mezőt üresre állítja.
     */
    @FXML
    public void talloz() {
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
     * Az 'Adatok feldolgozása'-gomb megnyomásakor lefutó metódus. Csak akkor kezdődik meg
     * a bevitt adatok feldolgozása, ha tallózással, vagy a szövegbeviteli mezót használva meg lett adva bemenő adat, illetve
     * ha lett kiválasztva forrás nyelv a legördülő listából. A feldolgozás előtt a szavakat tároló lista és a szavak indexét tároló
     * HashMap tartalmát törli, a mondatok szövegmezőt üresre állítja és a táblázathoz rendelt listenert eltávolítja. Beállítja az
     * adott nyelvhez tartozó kódot, ami alapján az egyedi nevű táblákat létre is hozza. Lefut az előfeldolgozás, a feldolgozás, az
     * azonos szavak törlése - és így a szógyakoriság számlálása - majd az egyedi szavakat összeveti az adatbázis szavaival és 
     * törli a listából ami már szerepel az adatbázisban. A már szinkronizált lista tartalmát megjeleníti a táblázatban és hozzáadja
     * a táblázathoz a listenert. A tallózásról tájékoztató label-t kiüríti.
     */
    @FXML
    public void futtat() {
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
                txaMondat.setText("");
                // A megadott forrásnyelv beállítása (pl: 'Német' -> 'de')
                forrasNyelvKod = nyelvekKodja.get(cbxForras.getValue());
                TablaNevEleje = forrasNyelvKod + "_";
                beolvasasFeldolgozas();
                azonosakTorlese();
                DB.tablakatKeszit(TablaNevEleje);
                DB.adatbazistListavalOsszevet(TablaNevEleje + "szavak",data,szavak_indexe);
                DB.adatbazistListavalOsszevet(TablaNevEleje + "tanulando",data,szavak_indexe);
                listaTorlesek();
                tblTablazat.setItems(data);
                // Listener beállítása az adatok táblázatba betöltése után
                tblTablazat.getSelectionModel().selectedItemProperty().addListener(listener);
                lblTallozasEredmeny.setText("");

                ablak.hide();
                
                if (data.isEmpty()) {
                    figyelmeztet("Figyelem!", "A nem megfelelő karakterek eltávolítása és az adatbázis szinkronizálás után nem "
                            + "maradt megjeleníthető eredmény!");
                } else {
                    tajekoztat("Kész!", "Az adatok feldolgozása befejeződött!");
                }
            } catch (IOException e) {
                hiba("Hiba!",e.getMessage());
            }
        }
    }

    
    /**
     * Ha lett megadva tallózással fájlútvonal, akkor egyben beolvassa a fájlból az adatokat a Cp1250-es kódtábla alapján, majd
     * beleteszi egy Stringbe. Eltávolítja belőle a tabulátorokat és az új sorokat, majd az eredménnyel meghívja az előfeldolgozást,
     * aminek eredményével meghívja a feldolgozást. Végül azért, hogy a következő futtatás előtt is dönteni lehessen a tallózás-
     * szövegmező között, a fájlútvonalat null-ra állítja.
     * Ha nem lett megadva fájlútvonal akkor a szövegterületről olvassa ki a szöveget, eltávolítja a tabulátorokat és az új sorokat,
     * majd meghívja a kapott Stringgel az előfeldolgozást és a feldolgozást.
     */
    public void beolvasasFeldolgozas() {
        String eredetiSzoveg = "";
        if (fajlUtvonal != null) {
            File f = new File(fajlUtvonal);
            try (FileInputStream fis = new FileInputStream(f)){
                byte[] adat = new byte[(int) f.length()];
                fis.read(adat);
                eredetiSzoveg = new String(adat, "Cp1250");
                eredetiSzoveg = eredetiSzoveg.replaceAll("\t|\n|\r", "");
                feldolgozas(eloFeldolgozas(eredetiSzoveg));
                /* Ha egyszer lefuttatuk tallózott fájllal, kiszedjük a fájltnevet, hogy újra dönteni lehessen 
                   a tallózás-szövegdoboz között különben a korábban tallózott fájlnév megmarad és így nem lehet
                   használni a szövegmezőt.*/
                fajlUtvonal = null;
            } catch (IOException e) {
                hiba("Hiba!",e.getMessage());
            }
        } else {
            // A szövegdobozos szövegből kiszedjük a tabulátorokat és az új sorokat
            eredetiSzoveg = txaBevitel.getText().replaceAll("\t|\n", "");
            feldolgozas(eloFeldolgozas(eredetiSzoveg));
        }
    }
    
    /**
     * Törli azokat a többször egymás után előforduló karaktereket, amik alapján majd a 
     * splittelés történik ("." "?" "!"). Végigmegy a teljes szövegen karakterenként, ha a karakter "." "?" "!" vagy " " , akkor ha a következő karakter is
     * ugyanolyan, addig törli a következőket amíg nem talál egy más karaktert. Mivel a kapott szöveg egy adott karakterét tudni kell törölni, ezért a
     * feldolgozandó szövegből StringBuildert készít. Az előfeldolgozott szöveget visszaalakítja String-é és úgy adja vissza.
     * Így a feldolgozas metódus splittelése után a feltöltött tömbök nem fognak feleslegesen üres String-eket tartalmazni.
     * Továbbá eltünteti a nem megfelelő fájl-konvertálásból eredő felesleges szóközöket mind a szavakban, mind a mondatokban.
     * @param szoveg A feldolgozandó szöveg
     * @return Visszadja a feldolgozott szöveget
     */
    public String eloFeldolgozas(String szoveg) {
        StringBuilder sb = new StringBuilder(szoveg);
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
        szoveg = sb.toString();
        return szoveg;
    } 
   
    /**
     * A kapott szöveget "." "?" "!" és ezek szóközzel ellátott verziói alapján splitteli a mondatok tömbbe, majd a mondatok tömböt
     * " " ", " és "," alapján splitteli a szavak tömbbe. Ha a szó legalább 2 karakter akkor megtisztítja elölről és hátulról, ha ezek után 
     * legalább 2 de maximum 30 karakter hosszú akkor kisbetűssé alakítva a szintén megtisztított mondattal együtt -Sor típusú objektumként-
     * hozzáadja a listához.
     * @param szoveg A feldolgozandó szöveg
     */
    public void feldolgozas(String szoveg) {
        // A szöveg szétvágása "." "?" "!" szerint, plusz azok az esetek amikor szóköz van utánuk
        String mondatok [] = szoveg.split("\\. |\\.|\\? |\\?|\\! |\\!");
        for (int i = 0; i < mondatok.length; i++) {
            // Mondat szétvágása szavakká szóköz vagy vessző szerint
            String[] szok = mondatok[i].split(" |\\, |\\,");
            for (int j = 0; j < szok.length; j++) {
                // Mozaikszavaknál, rövidítéseknél sok pont lehet közel egymáshoz, ilyenkor mindegyiket külön mondatnak
                // veszi és 0, 1 karakter hosszú töredékek keletkeznek mint szó. Ilyen esetekben a szót figyelmen kívül hagyjuk
                if (szok[j].length() < 2) {
                    continue;
                }

                String szo = megtisztit(szok[j]);
                
                // Ha még a megtisztítás után is több mint 30 karakter a szó, akkor valószínűleg a belsejében van sok nem megfelelő
                // karakter, ezért nem dolgozzuk fel. Illetve ha 2-nél kevesebb karakterből áll.
                if (szo.length() > 30 || szo.length() < 2) {
                    continue;
                }
                szo = szo.toLowerCase();
                
                data.add(new Sor(szo, megtisztit(mondatok[i]), 1));
            }
        }
    }

    /**
     * A kapott szöveg elejét és végét megtisztítja azoktól a karakterektől amik nem a támogatott idegen nyelvek részei.
     * Ha a tisztítás során minden karakter elfogy, akkor üres String-et ad vissza.
     * @param szoveg  A megtisztítandó szöveg
     * @return Visszadja a megtisztított szöveget
     */
    public String megtisztit(String szoveg) {
        // Szó elejének megtisztítása az első szöveges karakterig
        int eleje = 0;
        int vege = szoveg.length()-1;
        while (szoveg.charAt(eleje) < 'A' 
                || (szoveg.charAt(eleje) > 'z' && szoveg.charAt(eleje) < 193) 
                || (szoveg.charAt(eleje) > 'Z' && szoveg.charAt(eleje) < 'a') 
                || szoveg.charAt(eleje) > 382) {
            if (eleje == szoveg.length()-1) {
                break;
            }
            eleje++;
        }
        // Ha teljesen elfogyott a szó a tisztítás során akkor üres Stringet ad vissza
        if (eleje == szoveg.length()-1) {
                return "";
        } else {
        // Különben ha nem fogyott el a szó, akkor a szó végéről indulva is megtisztítja
            while (szoveg.charAt(vege) < 'A' 
                    || (szoveg.charAt(vege) > 'z' && szoveg.charAt(vege) < 193) 
                    || (szoveg.charAt(vege) > 'Z' && szoveg.charAt(vege) < 'a') 
                    || szoveg.charAt(vege) > 382) {
                if (vege == 0) {
                    break;
                }
                vege--;
            }
        }

        szoveg = szoveg.substring(eleje, vege + 1);
        return szoveg;
    }
    
    /**
     * Beépített rendezéssel rendezi a Sor típusú elemekből álló listát a szavak alapján, így az azonos szavak egymás mellé kerülnek. Majd addig 
     * törli az azonos szavakat, amíg csak egyetlen példány marad, közben számolja a szavak gyakoriságát. A megmaradó szó gyakoriság 
     * mezőjének értékére beállítja a törlések során számolt gyakoriságot.
     * Egy HashMap-ben tárolja az így már egyszer előforduló szóhoz tartozó lista-indexet, így keresés nélkül megállíptható, hogy 
     * egy adott szó benne van-e a listában. Végül ha a lista nem üres, akkor az utolsó elemre is beállítja a HashMap-et.
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
        if (!data.isEmpty()) {
            szavak_indexe.put(data.get(data.size()-1).getSzo(), data.size()-1);
        }
    }
    
    /**
     * Végigmegy a listán és ha a szó "torlendo", akkor törli onnan. Különben ha be volt jelölve az egyszer előforduló
     * szavak megjelenítésének tiltása és egyszer fordul elő a szó, akkor szintén törli a listából.
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
            }
        }
    }

    /**
     * Ha a feldolgozás után a lista nem üres, akkor a táblázatban kijelölt sor szavát a gomb megnyomása 
     * után elmenti a szavak táblába ignoralt állapottal, majd letiltja a sorhoz tartozó gombokat és léptet a táblázatban.
     */
    @FXML
    public void ignoralMent() {
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
     * után elmenti a szavak táblába ismert állapottal, majd letiltja a sorhoz tartozó gombokat és léptet a táblázatban.
     */
    @FXML
    public void ismertMent() {
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
     * átadja neki a szo és mondat String tartalmát. Ha az új ablakban a tanulandó szó sikeresen el lett mentve,
     * akkor letiltja a sorhoz tartozó gombokat és léptet a táblázatban.
     * @throws Exception Hiba esetén kivételt dob
     */
    @FXML
    public void tanulandoMent() throws Exception{
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
     * Letiltja az adott táblázatbeli sor 3 gombjának (ismert, tanulandó, ignorált) használatát, tárolja
     * a tábla nevét ahova a beírás történt és kijelöli a táblázat következő elemét. Ha a táblázat utolsó
     * eleménél tart, akkor a léptetés visszafelé történik, így az utolsó sor 3 gombja letiltottnak 
     * fog látszódni kijelöléskor.
     * @param tabla A tábla, ahova az adatbázisban a szó el lett mentve
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
     * hogy később is ellenőrizni lehessen történt-e változás.
     */
    @FXML
    public void visszavon() {
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

    /**
     * Új ablakot nyit meg, ahol ANKI-import fájl készíthető.
     */
    @FXML
    public void ankiImportAblak() {
        ablakotNyit("Anki.fxml", "ANKI-import elkészítése", "", "");
    }
    
    /**
     * Új ablakban megjeleníti az adott nyelvhez tartozó statisztikát
     */
    @FXML
    public void statisztikaAblak() {
        ablakotNyit("Statisztika.fxml", "Adatbázis-statisztika", "", "");
    }
    
    /**
     * Új ablakban megjeleníti a szókártya-kikérdezés felületet
     */
    @FXML
    public void kikerdezesAblak() {
        ablakotNyit("Kikerdezes.fxml","Szavak kikérdezése szókártyákkal","","");
    }
    
    /**
     * A kapott fxml fájlnév alapján új ablakot nyit meg. Ha a szó paraméter nem üres, akkor a fordítás ablakot
     * nyitja meg, ekkor az új ablakhoz tartozó controller osztályban beállítja a szó, mondat és forrás nyelv kód mezők értékeit.
     * @param fxmlFajl     Az fxml fájl neve
     * @param ablakCim     A megnyitott ablak címe
     * @param szo          Fordítás ablak esetében a kapott szó
     * @param mondat       Fordítás ablak esetében a kapott mondat
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
     * adatbázis, akkor előtte létrehozza a projekt mappájába. Beállítja a Főbablak legördülő listájának nyelveit.
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
     * a verziószámról és megnyitható böngészőben a fejlesztői dokumentáció.
     * @throws java.lang.Exception Hiba esetén kivételt dob
     */
    @FXML
    public void nevjegy() throws Exception {
        ablakotNyit("Nevjegy.fxml", "Nyelvtanulás program","","");
    }
}