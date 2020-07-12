package nyelvtanulas_kr_szakdolgozat;

import java.util.HashMap;

public interface Feliratok {
    
    ////////////////////////////////////////////////////////////////////////////
    // MAGYAR FELIRATOK
    ////////////////////////////////////////////////////////////////////////////
    
    public static final String [] FOABLAK_MAGYARFELIRATOK = new String [] {
        "Opciók",
        "ANKI-import készítése",
        "Szavak kikérdezése",
        "Statisztika",
        "Kilépés",
        "Egyéb",
        "Névjegy",
        "Adatbeviteli lehetőségek:",
        "- Külső szöveges fájl betallózása",
        "Tallózás",
        "- Szöveg közvetlen bemásolása a szövegdobozba",
        "Egyszeri szavakat ne listázza:",
        "Forrásnyelv (kötelező)",
        "Adatok feldolgozása",
        "Feldolgozás eredménye",
        "Ismert szó (1)",
        "Tanulandó szó (2)",
        "Figyelmen kívül hagyás (3)",
        "Visszavonás (4)",
        "Szavak",
        "Mondatok",
        "Gyakoriság",
        "Szöveg ismertsége:",
        "Olvashatósági index:",
        "Felület nyelve",
        "Magyar",
        "English"
    };
    
    public static final String [] ANKI_MAGYARFELIRATOK = new String [] {
        "Kérem válassza ki, hogy melyik nyelv tanulandó szavaiból legyen ANKI-import készítve",
        "Kártyák készítése",
        "Mégsem"
    };
    
    public static final String [] FORDITAS_MAGYARFELIRATOK = new String [] {
        "A mentés előtt kérem adja meg a szó fordítását!",
        "Névelő:",
        "Szó",
        "A szó nagybetűvel kezdődjön:",
        "Példamondat",
        "Eredeti példamondat visszaállítása",
        "Előző mondat",
        "Következő mondat",
        "Google Translate",
        "Cambridge Dictionary (kizárólag angol nyelvnél elérhető)",
        "Duden (kizárólag német nyelvnél elérhető)",
        "Szó fordítása:",
        "Hozzáadás"
    };
    
    public static final String [] NYELVEK_MAGYAR = new String [] {
        "Angol","Spanyol","Francia","Német","Olasz","Portugál","Holland","Lengyel","Dán","Cseh","Szlovák","Szlovén"
    };
    
    public static final String [] KIKERDEZES_MAGYARFELIRATOK = new String [] {
        "Kérem válassza ki a nyelvet:",
        "Kikérdezés elindítása",
        "Válasz mutatása",
        "Újra",
        "Nehéz",
        "Jó",
        "Könnyű"
    };
    
    public static final String [] STATISZTIKA_MAGYARFELIRATOK = new String [] {
        "Statisztika",
        "Kérem válasszon ki egy nyelvet:",
        "Összes szó:",
        "Ismert szavak:",
        "Figyelmen kívül hagyott szavak:",
        "Tanulandó szavak összesen:",
        "Exportált tanulandó szavak:",
        "Nem exportált tanulandó szavak:"
    };
    
    public static final String [] NEVJEGY_MAGYARFELIRATOK = new String [] {
        "Készítette:",
        "Verzió:",
        "Fejlesztői dokumentáció megtekintése a böngészőben",
        "Program Github oldala"
    };
    
    public static final HashMap<String, String> UZENETEK_MAGYAR = new HashMap<String, String>() {{
        // Főablak üzenetei
        put("tallozassikeres",            "Tallózás sikeres!");
        put("tallozassikertelen",         "Sikertelen tallózás!");
        put("uresszovegmezo",             "Üres szövegmező! Kérem adjon meg szöveget, vagy használja a Tallózás gombot!");
        put("forrasnyelvis",              "Kérem adja meg a forrásnyelvet is!");
        put("feldolgozasfolyamatban",     "Adatok feldolgozása folyamatban");
        put("nincseredmeny",              "A nem megfelelő karakterek eltávolítása és az adatbázis szinkronizálás után nem maradt megjeleníthető eredmény!");
        put("feldolgozasbefejezodott",    "Az adatok feldolgozása befejeződött!");
        put("forditashozzaadas",          "Fordítás hozzáadása, feltöltés adatbázisba");
        put("nemerhetoel",                "Nem érhető el");
        put("ellenorizelsouzenet",        "Nem történt adatfeldolgozás, kérem adjon meg bemenő adatot és válassza az 'Adatok feldolgozása' gombot!");
        put("ellenorizmasodikuzenet",     "Nincs kijelölve sor a táblázatban!");
        put("kijeloltsornalnincsvaltozas","A kijelölt sornál nem történt változás amit vissza kéne vonni!");
        put("ankiimportelkeszites",       "ANKI-import elkészítése");
        put("adatbazisstatisztika",       "Adatbázis-statisztika");
        put("szavakkikerdezese",          "Szavak kikérdezése szókártyákkal");
        put("bezaras",                    "Valóban be szeretné zárni a programot?");
        put("nevjegy",                    "Nyelvtanulás program");
        
        // Anki-import ablak üzenetei
        put("akarankiimportotkesziteni",  "Valóban szeretne minden új tanulandó szóból ANKI-importot készíteni?");
        put("hibaskartyakeszites",        "Hiba történt a kártya készítése során!");
        put("kartyakelkeszitve",          "A kártyák sikeresen elkészítve a(z):  ");
        put("fajlba",                     " _ankiimport fájlba!");
        put("nincstanulando",             "Nincsen tanulandó szó amiből szókártya készíthető!");
        put("adjameganyelvet",            "Kérem adja meg a nyelvet!");
        
        // Fordítás ablak üzenetei
        put("irjonbeforditast",           "Kérem írjon be fordítást a szóhoz!");
        put("nincspeldamondat",           "Az adott szóhoz nincsen megadva példamondat!");
        
        // Kikérdezés ablak üzenetei
        put("melyiknyelv",                "Kérem válassza ki, hogy melyik nyelv szókártyáit szeretné használni");
        put("nincsentanulando",           "Nincsen aktuálisan tanulandó szó!");
        put("kikerdezesvege",             "Véget ért a kikérdezés!");
        
        // Panel header feliratok
        put("tajekoztat",                 "Kész!");
        put("figyelmeztet",               "Figyelem!");
        put("hiba",                       "Hiba!");
        put("kilepesmegerosites",         "Kilépés megerősítés");
        put("ankiimportkeszites",         "ANKI kártya készítés");
        put("kártyakesziteseredmeny",     "Kártya készítés eredmény");

    }};
    
    ////////////////////////////////////////////////////////////////////////////
    // ANGOL FELIRATOK
    ////////////////////////////////////////////////////////////////////////////
    
    public static final String [] FOABLAK_ANGOLFELIRATOK = new String [] {
        "Options",
        "Create ANKI-import",
        "Review cards",
        "Statistic",
        "Exit",
        "Other",
        "About",
        "Data entry options:",
        "- Browse an external text file",
        "Browse",
        "- Copy text directly into the text box",
        "Do not list one-time words:",
        "Source language (required)",
        "Data processing",
        "Result of processing",
        "Known word (1)",
        "Word to learn (2)",
        "Ignore (3)",
        "Undo (4)",
        "Words",
        "Sentences",
        "Frequency",
        "How much of the text is known:",
        "Readability index:",
        "Language",
        "Magyar",
        "English"
    };
    
    public static final String [] ANKI_ANGOLFELIRATOK = new String [] {
        "Please select ANKI import language",
        "Create cards",
        "Cancel"
    };

    public static final String [] FORDITAS_ANGOLFELIRATOK = new String [] {
        "Please provide a translation of the word before adding word to the database",
        "Article:",
        "Word",
        "Word begins with a capital letter:",
        "Example sentence",
        "Restore the original example sentence",
        "Previous sentence",
        "Next sentence",
        "Google Translate",
        "Cambridge Dictionary (only available for english source language)",
        "Duden (only available for german source language)",
        "Translation of the word:",
        "Add to the Database"
    };
    
    public static final String [] NYELVEK_ANGOL = new String [] {
        "English","Spanish","French","German","Italian","Portuguese","Dutch","Polish","Danish","Czech","Slovak","Slovenian"
    };
    
    public static final String [] KIKERDEZES_ANGOLFELIRATOK = new String [] {
        "Please select the language:",
        "Start review",
        "Show answer",
        "Again",
        "Difficult",
        "Good",
        "Easy"
    };
    
    public static final String [] STATISZTIKA_ANGOLFELIRATOK = new String [] {
        "Statistics",
        "Please select a language:",
        "All words:",
        "Known words:",
        "Ignored words:",
        "All the words to learn:",
        "Exported words to learn:",
        "Unexported words to learn:"
    };
    
    public static final String [] NEVJEGY_ANGOLFELIRATOK = new String [] {
        "Developer:",
        "Version:",
        "Developer documentation",
        "Github page"
    };
    
    public static final HashMap<String, String> UZENETEK_ANGOL = new HashMap<String, String>() {{
        // Főablak üzenetei
        put("tallozassikeres",            "Browse successful!");
        put("tallozassikertelen",         "Browsing failed!");
        put("uresszovegmezo",             "Empty text box! Please enter text or use the Browse button!");
        put("forrasnyelvis",              "Please also specify the source language!");
        put("feldolgozasfolyamatban",     "Data processing in progress");
        put("nincseredmeny",              "After removing the incorrect characters and synchronizing the database, no results were displayed!");
        put("feldolgozasbefejezodott",    "Data processing is complete!");
        put("forditashozzaadas",          "Add translation, upload to database");
        put("nemerhetoel",                "Not available");
        put("ellenorizelsouzenet",        "No data has been processed, please enter your input data and select the 'Data Processing' button!");
        put("ellenorizmasodikuzenet",     "No rows selected in the table!");
        put("kijeloltsornalnincsvaltozas","There is no change to the selected row that can be undone!");
        put("ankiimportelkeszites",       "Create ANKI-import");
        put("adatbazisstatisztika",       "Stats Database");
        put("szavakkikerdezese",          "Review flashcards");
        put("bezaras",                    "Are you sure you want to close the program?");
        put("nevjegy",                    "Language learning program");
        
        // Anki-import ablak üzenetei
        put("akarankiimportotkesziteni",  "Do you really want to create ANKI-import from each new word you learn?");
        put("hibaskartyakeszites",        "An error occurred while creating the card!");
        put("kartyakelkeszitve",          "Cards successfully created in the file:  ");
        put("fajlba",                     " _ankiimport!");
        put("nincstanulando",             "There is no word to learn from which to make a word card!");
        put("adjameganyelvet",            "Please enter the language!");
        
        // Fordítás ablak üzenetei
        put("irjonbeforditast",           "Please enter a translation for the word!");
        put("nincspeldamondat",           "There is no example sentence!");
        
        // Kikérdezés ablak üzenetei
        put("melyiknyelv",                "Please select which language you want to use word cards for");
        put("nincsentanulando",           "There are no words to learn right now!");
        put("kikerdezesvege",             "The review is over!");
        
        // Panel header feliratok
        put("tajekoztat",                 "Ready!");
        put("figyelmeztet",               "Attention!");
        put("hiba",                       "Error!");
        put("kilepesmegerosites",         "Exit confirmation");
        put("ankiimportkeszites",         "Creating ANKI cards");
        put("kártyakesziteseredmeny",     "Card making result");

    }};
}
