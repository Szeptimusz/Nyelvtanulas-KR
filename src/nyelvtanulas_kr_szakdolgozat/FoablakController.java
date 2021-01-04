package nyelvtanulas_kr_szakdolgozat;

import eu.crydee.syllablecounter.SyllableCounter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static nyelvtanulas_kr_szakdolgozat.Panel.figyelmeztet;
import static nyelvtanulas_kr_szakdolgozat.Panel.hiba;
import static nyelvtanulas_kr_szakdolgozat.Panel.igennem;
import static nyelvtanulas_kr_szakdolgozat.Panel.tajekoztat;

/**
 * A program indításakor megjelenő ablakot kezelő osztály. Itt történik meg az adatok bevitele, feldolgozása,
 * táblázatban való megjelenítése, majd lehetőség szerint azok adatbázisba mentése. Ebből az
 * ablakből lehet megnyitni a program többi ablakát (menüpontok vagy gomb által).
 * @author Kremmer Róbert
 */
public class FoablakController implements Initializable, Feliratok {

    String fajlUtvonal;
    String TablaNevEleje;
    String forrasNyelvKod;
    String mappaUtvonal = System.getProperty("user.dir");
    int    eredetiOsszesSzo;
    int    toroltSzavak;
    int    progress = 1;
    int    dataIndex = 0;
    int    vegPont = 0;
    int    sorokSzama;
    double progressbarJelenlegiErtek;
    double progressbarMaximumErtek;
    double fleschScore;
    HashMap<String, Integer> szavak_indexe = new HashMap<>();
    
    // Statikus adatmezők a felület nyelvének beállítására és a nyelvek kódjainak
    // meghatározására (más osztályokból is)
    static HashMap<String, String>  nyelvekKodja  = new HashMap<>();
    static HashMap<String, String>  kodhozNyelv   = new HashMap<>();
    static HashMap<String, String>  uzenetek      = new HashMap<>();
    static int       beolvasottSorokSzama;
    static String    celNyelvKod;
    static String    feluletNyelve;
    static String    feluletNyelvenekNeveAdottNyelven;
    static String [] foablakFelirat;
    static String [] ankiFelirat;
    static String [] forditasFelirat;
    static String [] nyelvek;
    static String [] kikerdezesFelirat;
    static String [] statisztikaFelirat;
    static String [] nevjegyFelirat;
    static String [] beallitasokFelirat;
    
    private final ObservableList<Sor> data = FXCollections.observableArrayList();
    
    @FXML
    private Menu             menuOpciok;
    @FXML
    private MenuItem         menuiAnki;
    @FXML
    private MenuItem         menuiKikerdezes;
    @FXML
    private MenuItem         menuiStatisztika;
    @FXML
    private MenuItem         menuiBeallitasok;
    @FXML
    private MenuItem         menuiKilepes;
    @FXML
    private Menu             menuEgyeb;
    @FXML
    private MenuItem         menuiNevjegy;
    @FXML
    private SplitPane        anchor;
    @FXML
    private Label            lblLehetoseg;
    @FXML
    private Label            lblKulsoSzovegesTallozas;
    @FXML
    private Button           btnTalloz;
    @FXML
    private Label            lblSzovegKozvetlenBemasolas;
    @FXML
    private TextArea         txaBevitel;
    @FXML
    private Label            lblEgyszeritNeListazza;
    @FXML
    private Label            lblForrasnyelv;
    @FXML
    private Button           btnFeldolgoz;
    @FXML
    private CheckBox         cxbEgyszer;
    @FXML
    private ComboBox<String> cbxForras;
    @FXML
    private Label            lblTallozasEredmeny;
    @FXML
    private Label            lblFeldolgozasEredmeny;
    @FXML
    private Button           btnTanulando;
    @FXML
    private Button           btnVisszavon;
    @FXML
    private Button           btnKovetkezoOldal;
    @FXML
    private TextArea         txaMondat;
    @FXML
    private TableView<Sor>   tblTablazat;
    @FXML
    private TableColumn<Sor, String>  oSzo;
    @FXML
    private TableColumn<Sor, String>  oMondat;
    @FXML
    private TableColumn<Sor, Integer> oGyak;
    @FXML
    private Label            lblIsmertseg;
    @FXML
    private Label            lblSzazalekIsmert;
    @FXML
    private Label            lblOlvashato;
    @FXML
    private Label            lblOlvashatosag;
    @FXML
    private ProgressBar      pbarOldal;
    
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
            lblTallozasEredmeny.setText(uzenetek.get("tallozassikeres"));
            mappaUtvonal = fajlUtvonal.substring(0, fajlUtvonal.lastIndexOf('\\') + 1);
        } else {
            lblTallozasEredmeny.setText(uzenetek.get("tallozassikertelen"));
        }
    }
    

    Task copyWorker;
    
    /**
     * Egy új szálon a háttérben végzi el az adatok feldolgozását. Szövegterületről,
     * vagy fájlból beolvassa az adatokat, szavak és mondatok szerint listához adja,
     * számolja a szógyakoriságot, törli az azonos szavakat, összeveti az adatbázis 
     * szavaival (törli a listából amik már az adatbázisban vannak), gyakoriság 
     * szerint rendezi.
     * @return 
     */
    public Task createWorker() {
        return new Task() {
          @Override
          protected Object call() throws Exception {
            progress = 1;
            
            // Beolvasás fájlból vagy szövegterületről
            String szoveg;
            if (fajlUtvonal != null) {
                szoveg = new String(Files.readAllBytes(Paths.get(fajlUtvonal)),"UTF-8")
                            .replaceAll("\t|\n|\r|\\  ", "");
                fajlUtvonal = null;
                
            } else {
                szoveg = txaBevitel.getText().replaceAll("\t|\n|\\  ", "");
            }
            updateProgress(progress++, 15);

            
            // Szöveg szétvágása mondatok és szavak alapján, szavak megtisztítása, listához adás
            int    szotagokSzama = 0;
            int    mondatokSzama = 0;
            double szazalek      = 0.2;
            String mondatok []   = szoveg.split("\\. |\\.|\\? |\\?|\\! |\\!");
            SyllableCounter sc   = new SyllableCounter();
            
            for (String mondat : mondatok) {
                
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
                
                if (++mondatokSzama >= mondatok.length * szazalek) {
                    updateProgress(progress++, 15);
                    szazalek += 0.2;
                }
            }           
            eredetiOsszesSzo = data.size();
            fleschScore = 206.835 - 1.015 * ((double)eredetiOsszesSzo / mondatok.length)
                    - 84.6 * ((double)szotagokSzama / eredetiOsszesSzo);
            
            
            // A rendezés után az azonos szavak törlése, gyakoriság számolása
            data.sort((s1, s2) -> s1.getSzo().compareTo(s2.getSzo()));
            data.add(new Sor("", "", 1));
            LinkedList<Sor> csatoltLista = new LinkedList<>(data);
            ListIterator<Sor> it = csatoltLista.listIterator();
            updateProgress(progress++, 15);
            
            int listaEredetiMeret = csatoltLista.size();
            szazalek = 0.2;
            int szavakSzama = 0;
            int i = 0;
            while (it.hasNext()) {
                Sor s = it.next();
                s.mondatotHozzaad(s.getMondat());
                szavak_indexe.put(s.getSzo(), i);
                int db = 1;
                if (it.hasNext()) {
                    Sor s2 = it.next();
                    szavakSzama++;
                    while(it.hasNext() && s.getSzo().equals(s2.getSzo())) {
                        s.mondatotHozzaad(s2.getMondat());
                        it.remove();
                        db++;
                        s2 = it.next();
                        szavakSzama++;
                    }
                    it.previous();
                    if (--szavakSzama >= listaEredetiMeret * szazalek) {
                        updateProgress(progress++, 15);
                        szazalek += 0.2;
                    }
                }
                csatoltLista.get(i).setGyak(db);
                i++;
                s.azonosakTorleseListabol();
            }
            data.clear();
            data.addAll(csatoltLista);
            data.remove(data.size()-1);
            updateProgress(progress++, 15);
              
            
            // Adatbázis táblák készítése, a lista szavainak szinkronizálása az adatbázissal
            DB.tablakatKeszit(TablaNevEleje);
            DB.adatbazistListavalOsszevet(TablaNevEleje + "szavak",data,szavak_indexe);
            DB.adatbazistListavalOsszevet(TablaNevEleje + "tanulando",data,szavak_indexe);
            updateProgress(progress++, 15);
            
            
            // Adatbázis szinkronizálás alapján a törlendő szavak törlése
            toroltSzavak = 0;
            for (int j = 0; j < data.size(); j++) {
                String szo = data.get(j).getSzo();

                if (cxbEgyszer.isSelected() && data.get(j).getGyak() == 1 && !szo.equals("torlendo")) {
                    data.remove(j--);
                    eredetiOsszesSzo--;
                } else if (szo.equals("torlendo")) {
                    toroltSzavak += data.get(j).getGyak();
                    data.remove(j--);
                }
            }
            
            // Rendezés, hogy a táblázatban gyakoriság szerint legyenek a szavak
            data.sort((s1, s2) -> Integer.compare(s2.getGyak(), s1.getGyak()));
            updateProgress(progress++, 15);

            return true;
          }
        };
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
                || (karakter > 'z' && karakter < 192) 
                || (karakter > 'Z' && karakter < 'a') 
                || (karakter > 687) ){
            if (eleje == szoveg.length()-1) return "";
            
            eleje++;
            karakter = szoveg.charAt(eleje);
        }
        
        // Különben ha nem fogyott el a szó, akkor a szó végéről indulva is megtisztítja
        karakter = szoveg.charAt(vege);
        while (karakter < 'A' 
                || (karakter > 'z' && karakter < 192) 
                || (karakter > 'Z' && karakter < 'a') 
                || (karakter > 687) ){
            if (vege == 0) return "";
            
            vege--;
            karakter = szoveg.charAt(vege);
        }
        return szoveg.substring(eleje, vege + 1);
    }

    /**
     * Ha van bemenő adat a felhasználótól, akkor alaphelyzetbe állítja a főablakot,
     * beállítja a töltés ablakot, letiltja a futtatás gombot, elindítja azt a szálat
     * ami a háttérben az adatok feldolgozását végzi. Ha befejezte a feldolgozást, akkor
     * leveszi a tiltást a futtatás gombról, felugró ablakban tájékoztat, és megjeleníti
     * az eredményeket a főablakban.
     */
    @FXML
    public void futtat() {
        
        // Ellenőrzi, hogy van-e bemenő feldolgozandó szöveg
        if (txaBevitel.getText().equals("") && fajlUtvonal == null) {
            figyelmeztet(uzenetek.get("figyelmeztet"), uzenetek.get("uresszovegmezo"));
            
        // Ellenőrzi, hogy ki van-e válaszva a forrásnyelv
        } else if (cbxForras.getValue() == null) {
            figyelmeztet(uzenetek.get("figyelmeztet"), uzenetek.get("forrasnyelvis"));
            
        } else {
        
            // Korábbi listener eltávolítása, lista és hashmap adatok törlése
            tblTablazat.getSelectionModel().selectedItemProperty().removeListener(listener);
            szavak_indexe.clear();
            tblTablazat.getItems().clear();
            data.clear();
            txaMondat.setText("");
            lblSzazalekIsmert.setText("");
            dataIndex = 0;
            vegPont = 0;
            btnKovetkezoOldal.setDisable(false);
            btnKovetkezoOldal.setText(foablakFelirat[26]);
            progressbarJelenlegiErtek = 0;
            pbarOldal.setProgress(progressbarJelenlegiErtek);
            // A megadott forrásnyelv beállítása (pl: 'Német' -> 'de')
            forrasNyelvKod = nyelvekKodja.get(cbxForras.getValue());
            TablaNevEleje = forrasNyelvKod + "_";
            sorokSzama = beolvasottSorokSzama;
            
            Stage primaryStage = new Stage();
            Group root = new Group();
            Scene scene = new Scene(root, 330, 120);
            BorderPane mainPane = new BorderPane();
            root.getChildren().add(mainPane);
            
            final Label label = new Label(uzenetek.get("feldolgozasfolyamatban"));
            final ProgressBar progressBar = new ProgressBar(0);
            final VBox vb = new VBox();
            
            progressBar.setPrefWidth(250);
            vb.setPrefWidth(330);
            vb.setPrefHeight(120);
            vb.setSpacing(20);
            vb.setAlignment(Pos.CENTER);
            vb.getChildren().addAll(label, progressBar);
            mainPane.setTop(vb);
            
            progressBar.setProgress(0);
            copyWorker = createWorker();
            progressBar.progressProperty().unbind();
            progressBar.progressProperty().bind(copyWorker.progressProperty());
            
            btnFeldolgoz.setDisable(true);
            new Thread(copyWorker).start();
            
            copyWorker.setOnSucceeded(e -> {
                primaryStage.hide();
                btnFeldolgoz.setDisable(false);
                
                if (data.isEmpty()) {
                    figyelmeztet(uzenetek.get("figyelmeztet"), uzenetek.get("nincseredmeny"));
                    btnKovetkezoOldal.setDisable(true);
                } else {
                    tajekoztat(uzenetek.get("tajekoztat"), uzenetek.get("feldolgozasbefejezodott"));
                    
                    // Oldalak számának meghatározása, az elején a progress 1-re állítása
                    if (data.size() % sorokSzama == 0) {
                        progressbarMaximumErtek = data.size() / (sorokSzama * 1.0);
                    } else {
                        progressbarMaximumErtek = data.size() / (sorokSzama * 1.0) + 1;
                    }

                    progressbarJelenlegiErtek = 1;
                    pbarOldal.setProgress(1.0 * progressbarJelenlegiErtek / progressbarMaximumErtek);
                    System.out.println(1.0 * progressbarJelenlegiErtek / progressbarMaximumErtek);
                }
                
                
                // Első maximum 22 sor betöltése a táblázatba
                for (int i = 0; i < data.size(); i++) {
                    tblTablazat.getItems().add(data.get(i));
                    vegPont++;
                    if (i == sorokSzama - 1) break;
                }
                
                
                
                tblTablazat.refresh();
                // Listener beállítása az adatok táblázatba betöltése után
                tblTablazat.getSelectionModel().selectedItemProperty().addListener(listener);
                lblTallozasEredmeny.setText("");
                lblSzazalekIsmert.setText((int)((double)toroltSzavak / eredetiOsszesSzo * 10000) / 100.0 + " %");
                
                if (data.size() < sorokSzama + 1) btnKovetkezoOldal.setText(foablakFelirat[27]);
                
                if (forrasNyelvKod.equals("en")) {
                    if      (fleschScore > 90) lblOlvashatosag.setText((int)fleschScore + "  (Very easy to read)");
                    else if (fleschScore > 80) lblOlvashatosag.setText((int)fleschScore + "  (Easy to read)");
                    else if (fleschScore > 70) lblOlvashatosag.setText((int)fleschScore + "  (Fairly easy to read)");
                    else if (fleschScore > 60) lblOlvashatosag.setText((int)fleschScore + "  (Plain English)");
                    else if (fleschScore > 50) lblOlvashatosag.setText((int)fleschScore + "  (Fairly difficult to read)");
                    else if (fleschScore > 30) lblOlvashatosag.setText((int)fleschScore + "  (Difficult to read)");
                    else                       lblOlvashatosag.setText((int)fleschScore + "  (Very difficult to read)");
                } else {
                    lblOlvashatosag.setText(uzenetek.get("nemerhetoel"));
                }
                
                
            });
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        
    }

    @FXML
    void kovetkezooldal() {
        if (data.isEmpty()) return;
        pbarOldal.setProgress(++progressbarJelenlegiErtek * 1.0 / progressbarMaximumErtek);

        for (; dataIndex < vegPont; dataIndex++) {
            if (!data.get(dataIndex).isTanulando()) {
                Sor jelenlegiSor = data.get(dataIndex);
                DB.szotBeirAdatbazisba(TablaNevEleje + "szavak",jelenlegiSor.getSzo());
                jelenlegiSor.setTabla(TablaNevEleje + "szavak");
            }
        }

        tblTablazat.getSelectionModel().selectedItemProperty().removeListener(listener);
        tblTablazat.getItems().clear();

        if (vegPont == data.size()) {
            btnKovetkezoOldal.setDisable(true);
            tajekoztat(uzenetek.get("tajekoztat"), uzenetek.get("tajekoztat"));
        }

        int db = 1;
        for (; vegPont < data.size(); vegPont++) {
            tblTablazat.getItems().add(data.get(vegPont));
            if (db == sorokSzama) {
                vegPont++;
                break;
            }
            db++;
        }

        if (vegPont == data.size()) btnKovetkezoOldal.setText(foablakFelirat[27]);

        tblTablazat.getSelectionModel().selectedItemProperty().addListener(listener);
        
    }

    /**
     * Ha a feldolgozás után a lista nem üres, akkor a gomb megnyomása után megnyit egy új ablakot és 
     * átadja neki a szo és mondat String tartalmát. Ha az új ablakban a tanulandó szó sikeresen el lett mentve,
     * akkor letiltja a sorhoz tartozó gombokat és léptet a táblázatban.
     * @throws Exception Hiba esetén kivételt dob
     */
    @FXML
    public void tanulandoMent() throws Exception{
        String uzenet = ellenoriz();
        if (uzenet != null) {
            figyelmeztet(uzenetek.get("figyelmeztet"), uzenet);
            return;
        }
        
        String szo = tblTablazat.getSelectionModel().getSelectedItem().getSzo(); 
        List<String> mondatok = tblTablazat.getSelectionModel().getSelectedItem().getMondatok();
        
        ablakotNyit("Forditas.fxml", uzenetek.get("forditashozzaadas"), szo, mondatok,true);
        if (ForditasController.isTanulandoElmentve()) {
            tblTablazat.getSelectionModel().getSelectedItem().setTanulando(true);
            letiltLeptet(TablaNevEleje + "tanulando");
            // Miután elmentette és léptetett a táblázatban, visszaállítja a ForditasController osztályban false-ra
            ForditasController.setTanulandoElmentve(false);
        }
    }
    
    /**
     * Ellenőrzi, hogy a listában vannak-e elemek és van-e kijelölt sor a táblázatban.
     * @return A visszaadott (megjelenítendő) üzenet.
     */
    public String ellenoriz() {
        if (data.isEmpty())
            return uzenetek.get("ellenorizelsouzenet");
        else if (tblTablazat.getSelectionModel().getSelectedItem() == null)
            return uzenetek.get("ellenorizmasodikuzenet");
        else
            return null; 
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
        String uzenet = ellenoriz();
        if (uzenet != null) {
            figyelmeztet(uzenetek.get("figyelmeztet"), uzenet);
            return;
        }
        
        String tabla = tblTablazat.getSelectionModel().getSelectedItem().getTabla();
        if (tblTablazat.getSelectionModel().getSelectedItem().isTanulando()) {
            Sor kivalasztottSor = tblTablazat.getSelectionModel().getSelectedItem();
            kivalasztottSor.setTilt(false);
            kivalasztottSor.setTanulando(false);
            btnTanulando.setDisable(false);
            DB.szotTorolAdatbazisbol(tabla, kivalasztottSor.getSzo());
            kivalasztottSor.setTabla(null);
        } else {
            figyelmeztet(uzenetek.get("figyelmeztet"), uzenetek.get("kijeloltsornalnincsvaltozas"));
        }
    }

    /*** Új ablakot nyit meg, ahol ANKI-import fájl készíthető.*/
    @FXML
    public void ankiImportAblak()  { ablakotNyit("Anki.fxml", uzenetek.get("ankiimportelkeszites"), "", null,false); }
    
    /*** Új ablakban megjeleníti az adott nyelvhez tartozó statisztikát*/
    @FXML
    public void statisztikaAblak() { ablakotNyit("Statisztika.fxml", uzenetek.get("adatbazisstatisztika"), "", null,false); }
    
    /*** Új ablakban megjeleníti a szókártya-kikérdezés felületet*/
    @FXML
    public void kikerdezesAblak()  { ablakotNyit("Kikerdezes.fxml",uzenetek.get("szavakkikerdezese"),"",null,false); }
    
    @FXML
    public void beallitasokAblak() { 
        ablakotNyit("Beallitasok.fxml",uzenetek.get("beallitasok"),"",null,false);
        foablakFeliratokatBeallit(feluletNyelve);
    }
    
    /**
     * A kapott fxml fájlnév alapján új ablakot nyit meg. Ha a szó paraméter nem üres, akkor a fordítás ablakot
     * nyitja meg, ekkor az új ablakhoz tartozó controller osztályban beállítja a szó, mondat és forrás nyelv kód mezők értékeit.
     * @param fxmlFajl     Az fxml fájl neve
     * @param ablakCim     A megnyitott ablak címe
     * @param szo          Fordítás ablak esetében a kapott szó
     * @param mondat       Fordítás ablak esetében a kapott mondat
     */
    private void ablakotNyit(String fxmlFajl, String ablakCim, String szo, List<String> mondatok, boolean resize) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFajl));
            Parent root = loader.load();
            if (!szo.isEmpty()) {
                ForditasController fc = loader.getController();
                fc.setForditasAblakAdatok(szo,mondatok,forrasNyelvKod,celNyelvKod);
            }
            Scene scene = new Scene(root);
            Stage ablak = new Stage();
            ablak.setResizable(resize);
            ablak.initModality(Modality.APPLICATION_MODAL);
            ablak.setScene(scene);
            ablak.setTitle(ablakCim);
            ablak.showAndWait();
        } catch (IOException e) {
            hiba(uzenetek.get("hiba"),e.getMessage());
        }
    }
    

    /**
     * Megadja, hogy a táblázat egy adott oszlopának értéke 
     * a Sor osztály melyik mezőjéből legyen kiszedve. Az ismert-tanulandó-ignorált gombok letiltásához és 
     * a táblázat feletti mondatkiíráshoz definiál egy listenert, amit még nem rendel hozzá semmihez.
     * Beállítja a szavakat adatbázisba mentő gombokra a hotkey-ket. Fájlból beolvassa a felület nyelvét,
     * beállítja a felület feliratait. A DB osztály osztályváltozójára beállítja az adatbázis elérési útvonalát,
     * ha nincsen adatbázis, akkor előtte létrehozza a projekt mappájába.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // A táblázatban az adott oszlopban megjelenő adat a Sor osztály melyik mezőjéből legyen kiszedve
        oSzo.setCellValueFactory(new PropertyValueFactory<>("szo"));
        oMondat.setCellValueFactory(new PropertyValueFactory<>("mondat"));
        oGyak.setCellValueFactory(new PropertyValueFactory<>("gyak"));
        
        // Listener előkészítése a futtat() metódushoz
        listener = (v, regi, uj) -> {
            // Lekérdezi, hogy az adott sor gombjainak tiltása true-ra vagy false-ra változott
            boolean tiltva = uj.isTilt();
            if (tiltva) {
                btnTanulando.setDisable(true);
            } else {
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
            
        };
        
        // Hotkey-k beállítása a főablak 3 elmentési és a visszavonási gombjára
            Platform.runLater(() -> {
                btnTanulando.getScene().setOnKeyPressed((final KeyEvent keyEvent) -> {
                    if (keyEvent.getCode() == KeyCode.DIGIT1) {
                        try {
                            tanulandoMent();
                        } catch (Exception ex) { Logger.getLogger(FoablakController.class.getName()).log(Level.SEVERE, null, ex); }
                        
                        keyEvent.consume();
                    }

                    if (keyEvent.getCode() == KeyCode.DIGIT2) {
                        try {
                            visszavon();
                        } catch (Exception ex) { Logger.getLogger(FoablakController.class.getName()).log(Level.SEVERE, null, ex); }
                        
                        keyEvent.consume();
                    }
                });
            });
            
            
            try (Scanner be = new Scanner(new File("settings.txt"))) {
                
                String felNyelv = be.nextLine();
                feluletNyelve = felNyelv;
                foablakFeliratokatBeallit(felNyelv);
                
                celNyelvKod = be.nextLine();
                beolvasottSorokSzama = Integer.parseInt(be.nextLine());
                
            } catch (FileNotFoundException e) {
                
                Locale currentLocale = Locale.getDefault();
                String helyiNyelv = currentLocale.getDisplayLanguage();
                feluletNyelve = helyiNyelv;
                foablakFeliratokatBeallit(helyiNyelv);
                
                try (PrintWriter ki = new PrintWriter("settings.txt")) { 
                    
                    // Fájlba írja a 3 beállítást
                    ki.println(helyiNyelv);
                    ki.println(nyelvekKodja.get(helyiNyelv.substring(0,1).toUpperCase() + helyiNyelv.substring(1)));
                    ki.println("15");
                    
                    // Static változóba menti a beállításokat
                    foablakFeliratokatBeallit(helyiNyelv);
                    celNyelvKod = nyelvekKodja.get(helyiNyelv.substring(0,1).toUpperCase() + helyiNyelv.substring(1));
                    beolvasottSorokSzama = 15;
                    
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
                
            }
            
  
            
        // Adatbázis elérési útvonalát beállítja, ha nincs adatbázis akkor létrehozza
        DB.adatbazistKeszit("\\nyelvtanulas.db");
    }
    
    /**
     * A kapott nyelv alapján a statikus felirat mezőkre beállítja a megfelelő 
     * Feliratok interface-beli string tömböket (más java osztály hozzá tud férni, hogy
     * egy másik ablak feliratait beállítsa). A főablakon alkalmazza a feliratokat.
     * @param nyelv A feliratok nyelvét határozza meg
     */
    public void foablakFeliratokatBeallit(String nyelv) {
        
        if (nyelv.matches("magyar|Magyar|Hungarian|hungarian|húngaro|Húngaro|hongrois|hongrois|ungarisch|Ungarisch|ungherese|Ungherese|húngaro|Húngaro|Hongaars|hongaars|język węgierski|Język węgierski|Ungarsk|ungarsk|maďarský|Maďarský|Maďarský|maďarský|Madžarsko|madžarsko")) {
            
                feluletNyelvenekNeveAdottNyelven = "Magyar";
            
                foablakFelirat     = FOABLAK_MAGYARFELIRATOK;
                ankiFelirat        = ANKI_MAGYARFELIRATOK;
                forditasFelirat    = FORDITAS_MAGYARFELIRATOK;
                nyelvek            = NYELVEK_MAGYAR;
                kikerdezesFelirat  = KIKERDEZES_MAGYARFELIRATOK;
                statisztikaFelirat = STATISZTIKA_MAGYARFELIRATOK;
                nevjegyFelirat     = NEVJEGY_MAGYARFELIRATOK;
                uzenetek           = UZENETEK_MAGYAR;
                beallitasokFelirat = BEALLITASOK_MAGYARFELIRATOK;
                
        } else if (nyelv.matches("english|English|angol|Angol|Anglais|anglais|Inglés|inglés|Englisch|englisch|Inglese|inglese|Inglês|inglês|Engels|engels|język angielski|Język angielski|engelsk|Engelsk|Angličtina|angličtina|Angličtina|angličtina|angleščina|Angleščina")) {

                feluletNyelvenekNeveAdottNyelven = "English";
            
                foablakFelirat     = FOABLAK_ANGOLFELIRATOK;
                ankiFelirat        = ANKI_ANGOLFELIRATOK;
                forditasFelirat    = FORDITAS_ANGOLFELIRATOK;
                nyelvek            = NYELVEK_ANGOL;
                kikerdezesFelirat  = KIKERDEZES_ANGOLFELIRATOK;
                statisztikaFelirat = STATISZTIKA_ANGOLFELIRATOK;
                nevjegyFelirat     = NEVJEGY_ANGOLFELIRATOK;
                uzenetek           = UZENETEK_ANGOL;
                beallitasokFelirat = BEALLITASOK_ANGOLFELIRATOK;
              
                
        } else if (nyelv.matches("Español|español|Espagnol|espagnol|Spanyol|spanyol|Spanish|spanish|Spanisch|spanisch|spagnolo|Spagnolo|espanhol|Espanhol|Spaans|spaans|hiszpański|Hiszpański|spansk|Spansk|španělština|Španělština|Španielsky|španielsky|španski|Španski")) {

                feluletNyelvenekNeveAdottNyelven = "Español";
            
                foablakFelirat     = FOABLAK_SPANYOLFELIRATOK;
                ankiFelirat        = ANKI_SPANYOLFELIRATOK;
                forditasFelirat    = FORDITAS_SPANYOLFELIRATOK;
                nyelvek            = NYELVEK_SPANYOL;
                kikerdezesFelirat  = KIKERDEZES_SPANYOLFELIRATOK;
                statisztikaFelirat = STATISZTIKA_SPANYOLFELIRATOK;
                nevjegyFelirat     = NEVJEGY_SPANYOLFELIRATOK;
                uzenetek           = UZENETEK_SPANYOL;
                beallitasokFelirat = BEALLITASOK_SPANYOLFELIRATOK;
                
                
        } else if (nyelv.matches("français|Français|francés|Francés|French|french|Francia|francia|Französisch|französisch|francese|Francese|francês|Francês|Frans|frans|Francuski|francuski|fransk|Fransk|francouzština|Francouzština|Francúzsky|francúzsky|Francosko|francosko")) {

                feluletNyelvenekNeveAdottNyelven = "Français";
            
                foablakFelirat     = FOABLAK_FRANCIAFELIRATOK;
                ankiFelirat        = ANKI_FRANCIAFELIRATOK;
                forditasFelirat    = FORDITAS_FRANCIAFELIRATOK;
                nyelvek            = NYELVEK_FRANCIA;
                kikerdezesFelirat  = KIKERDEZES_FRANCIAFELIRATOK;
                statisztikaFelirat = STATISZTIKA_FRANCIAFELIRATOK;
                nevjegyFelirat     = NEVJEGY_FRANCIAFELIRATOK;
                uzenetek           = UZENETEK_FRANCIA;
                beallitasokFelirat = BEALLITASOK_FRANCIAFELIRATOK;
                
        } else { 
            
                feluletNyelvenekNeveAdottNyelven = "English";
            
                foablakFelirat     = FOABLAK_ANGOLFELIRATOK;
                ankiFelirat        = ANKI_ANGOLFELIRATOK;
                forditasFelirat    = FORDITAS_ANGOLFELIRATOK;
                nyelvek            = NYELVEK_ANGOL;
                kikerdezesFelirat  = KIKERDEZES_ANGOLFELIRATOK;
                statisztikaFelirat = STATISZTIKA_ANGOLFELIRATOK;
                nevjegyFelirat     = NEVJEGY_ANGOLFELIRATOK;
                uzenetek           = UZENETEK_ANGOL;
                beallitasokFelirat = BEALLITASOK_ANGOLFELIRATOK;
        }
        
        /* Beállítja a legördülő lista nyelveit (a felületen használt nyelven) és
           a nyelvek nevéhez statikus hashmap-ben hozzárendeli a nyelv kódját (más
           osztály használni tudja a nyelvkód megállapításához */
        String roviditettNyelv [] = {"en","es","fr","de","it","pt","nl","pl","da","cs","sk","sl","hu"};
        cbxForras.getItems().clear();
        cbxForras.getItems().addAll(nyelvek);
        for (int i = 0; i < nyelvek.length; i++) {
            nyelvekKodja.put(nyelvek[i], roviditettNyelv[i]);
            kodhozNyelv.put(roviditettNyelv[i], nyelvek[i]);
        }
        
        menuOpciok.setText(foablakFelirat[0]);
        menuiAnki.setText(foablakFelirat[1]);
        menuiKikerdezes.setText(foablakFelirat[2]);
        menuiStatisztika.setText(foablakFelirat[3]);
        menuiKilepes.setText(foablakFelirat[4]);
        menuEgyeb.setText(foablakFelirat[5]);
        menuiNevjegy.setText(foablakFelirat[6]);
        lblLehetoseg.setText(foablakFelirat[7]);
        lblKulsoSzovegesTallozas.setText(foablakFelirat[8]);
        btnTalloz.setText(foablakFelirat[9]);
        lblSzovegKozvetlenBemasolas.setText(foablakFelirat[10]);
        lblEgyszeritNeListazza.setText(foablakFelirat[11]);
        lblForrasnyelv.setText(foablakFelirat[12]);
        btnFeldolgoz.setText(foablakFelirat[13]);
        lblFeldolgozasEredmeny.setText(foablakFelirat[14]);
        btnTanulando.setText(foablakFelirat[15]);
        btnVisszavon.setText(foablakFelirat[17]);
        oSzo.setText(foablakFelirat[18]);
        oMondat.setText(foablakFelirat[19]);
        oGyak.setText(foablakFelirat[20]);
        lblIsmertseg.setText(foablakFelirat[21]);
        lblOlvashato.setText(foablakFelirat[22]);
        btnKovetkezoOldal.setText(foablakFelirat[26]);
        menuiBeallitasok.setText(foablakFelirat[16]);
    }
    
    /**
     * A menüből a Kilépés-t választva megerősítést vár a kilépésre, igen válasz
     * esetén bezárja a programot.
     */
    @FXML
    public void kilep() {
        if (igennem(uzenetek.get("kilepesmegerosites"), uzenetek.get("bezaras"))) {
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
        ablakotNyit("Nevjegy.fxml", uzenetek.get("nevjegy"),"",null,false);
    }
}
