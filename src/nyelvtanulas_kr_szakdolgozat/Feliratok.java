package nyelvtanulas_kr_szakdolgozat;

import java.util.HashMap;

/**
 * A feliratok különböző nyelveken tömbökben eltárolása.
 * @author Kremmer Róbert
 */
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
        "Tanulandó szó (1)",
        "Beállítások",
        "Visszavonás (2)",
        "Szavak",
        "Mondatok",
        "Gyakoriság",
        "Szöveg ismertsége:",
        "Olvashatósági index:",
        "Felület nyelve",
        "",
        "",
        "Következő oldal",
        "Befejezés"
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
        "Angol","Spanyol","Francia","Német","Olasz","Portugál","Holland","Lengyel","Dán","Cseh","Szlovák","Szlovén","Magyar"
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
        "",
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
    
    public static final String [] BEALLITASOK_MAGYARFELIRATOK = new String [] {
        "Beállítások",
        "Felület nyelve:",
        "Célnyelv (ismert nyelv):",
        "Sorok száma a táblázatban:",
        "Mentés",
        "Mégse"
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
        
        // Beállítások ablak üzenetei
        put("beallitasok",                "Beállítások");
        put("nemszam",                    "Pozitív egész számot adjon meg!");
        put("adjonmegmindenadatot",       "Kérem adjon meg minden adatot!");
        
        // Panel header feliratok
        put("tajekoztat",                 "Kész!");
        put("figyelmeztet",               "Figyelem!");
        put("hiba",                       "Hiba!");
        put("kilepesmegerosites",         "Kilépés megerősítés");
        put("ankiimportkeszites",         "ANKI kártya készítés");
        put("kártyakesziteseredmeny",     "Kártya készítés eredmény");
        
        // Panel igen-nem gomb
        put("igen",                       "Igen");
        put("nem",                        "Nem");

    }};
    
    ////////////////////////////////////////////////////////////////////////////
    // ANGOL FELIRATOK
    ////////////////////////////////////////////////////////////////////////////
    
    public static final String [] FOABLAK_ANGOLFELIRATOK = new String [] {
        "Options",
        "Create ANKI-import",
        "Review cards",
        "Statistics",
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
        "Word to learn (1)",
        "Settings",
        "Undo (2)",
        "Words",
        "Sentences",
        "Frequency",
        "How much of the text is known:",
        "Readability index:",
        "Language",
        "",
        "",
        "Next page",
        "Finish"
    };
    
    public static final String [] ANKI_ANGOLFELIRATOK = new String [] {
        "Please select ANKI import language",
        "Create cards",
        "Cancel"
    };

    public static final String [] FORDITAS_ANGOLFELIRATOK = new String [] {
        "Please provide a translation before adding word to the database",
        "Article:",
        "Word",
        "Word begins with a capital letter:",
        "Example sentence",
        "Restore sentence",
        "Previous sentence",
        "Next sentence",
        "Google Translate",
        "Cambridge Dictionary (only available for english source language)",
        "Duden (only available for german source language)",
        "Translation of the word:",
        "Add to the Database"
    };
    
    public static final String [] NYELVEK_ANGOL = new String [] {
        "English","Spanish","French","German","Italian","Portuguese","Dutch","Polish","Danish","Czech","Slovak","Slovenian","Hungarian"
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
        "",
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
    
    public static final String [] BEALLITASOK_ANGOLFELIRATOK = new String [] {
        "Settings",
        "Display language:",
        "Your language:",
        "Number of rows in the table:",
        "Save",
        "Cancel"
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
        
        // Beállítás ablak üzenetei
        put("beallitasok",                "Settings");
        put("nemszam",                    "Enter a positive integer! (x > 0)");
        put("adjonmegmindenadatot",       "Please enter all data!");
        
        // Panel header feliratok
        put("tajekoztat",                 "Ready!");
        put("figyelmeztet",               "Attention!");
        put("hiba",                       "Error!");
        put("kilepesmegerosites",         "Exit confirmation");
        put("ankiimportkeszites",         "Creating ANKI cards");
        put("kártyakesziteseredmeny",     "Card making result");
        
        // Panel igen-nem gomb
        put("igen",                       "Yes");
        put("nem",                        "No");

    }};
    
    ////////////////////////////////////////////////////////////////////////////
    // SPANYOL FELIRATOK
    ////////////////////////////////////////////////////////////////////////////
    
    public static final String [] FOABLAK_SPANYOLFELIRATOK = new String [] {
        "Opciones",
        "Crear importación ANKI",
        "Palabras de consulta",
        "Estadísticas",
        "Salida",
        "Otro",
        "Tarjeta de visita",
        "Opciones de entrada de datos:",
        "- Insertar un archivo de texto externo",
        "Vistazo",
        "- Copiar texto directamente en el cuadro de texto",
        "No enumere palabras de una sola vez:",
        "Idioma de origen (obligatorio)",
        "Procesamiento de datos",
        "Resultado del procesamiento",
        "Palabra para aprender (1)",
        "Configuraciones",
        "Revocación (2)",
        "Palabras",
        "Frases",
        "Frecuencia",
        "Conciencia de texto:",
        "Índice de legibilidad:",
        "Lenguaje de interfaz",
        "",
        "",
        "Siguiente página",
        "Terminación"
    };
    
    public static final String [] ANKI_SPANYOLFELIRATOK = new String [] {
        "Seleccione desde qué idioma aprender a importar ANKI",
        "Hacer cartas",
        "Cancelar"
    };
    
    public static final String [] FORDITAS_SPANYOLFELIRATOK = new String [] {
        "¡Proporcione una traducción de la palabra antes de guardar!",
         "Artículo:",
         "Tejido",
         "La palabra debe empezar con mayúscula:",
         "Ejemplo",
         "Restaurar la oración de ejemplo original",
         "Oración anterior",
         "Siguiente oración",
         "Traductor de google",
         "Diccionario Cambridge (disponible solo en inglés)",
         "Duden (disponible solo en alemán)",
         "Traducir palabra:",
         "Añadir"
    };
    
    public static final String [] NYELVEK_SPANYOL = new String [] {
        "Inglés", "Español", "Francés", "Alemán", "Italiano", "Portugués", "Holandés", "Polaco", "Danés", "Checo", "Eslovaco", "Esloveno", "Húngaro "
    };
    
    public static final String [] KIKERDEZES_SPANYOLFELIRATOK = new String [] {
         "Por favor, seleccione un idioma:",
         "Iniciar encuesta",
         "Mostrar respuesta",
         "Otra vez",
         "Difícil",
         "Bueno",
         "Fácil"
    };
    
    public static final String [] STATISZTIKA_SPANYOLFELIRATOK = new String [] {
         "Estadísticas",
         "Por favor, seleccione un idioma:",
         "Todas las palabras:",
         "Palabras conocidas:",
         "",
         "Total de palabras para aprender:",
         "Palabras de aprendizaje exportadas:",
         "Palabras de aprendizaje no exportadas:" 
    };
    
    public static final String [] NEVJEGY_SPANYOLFELIRATOK = new String [] {
         "Hecho por:",
         "Versión:",
         "Ver la documentación del desarrollador en su navegador",
         "Página del programa Github"
    };
    
    public static final String [] BEALLITASOK_SPANYOLFELIRATOK = new String [] {
         "Configuración",
         "Lenguaje de interfaz:",
         "Idioma de destino (idioma conocido):",
         "Número de filas en la tabla:",
         "Rescate",
         "Cancelar"
    };
    
    public static final HashMap<String, String> UZENETEK_SPANYOL = new HashMap<String, String>() {{
        // Főablak üzenetei
        put("tallozassikeres",            "¡Navega con éxito!");
        put("tallozassikertelen",         "¡La navegación falló!");
        put("uresszovegmezo",             "¡Cuadro de texto vacío! Ingrese texto o use el botón Examinar.");
        put("forrasnyelvis",              "Por favor, especifique también el idioma de origen.");
        put("feldolgozasfolyamatban",     "Procesamiento de datos en curso");
        put("nincseredmeny",              "Después de eliminar los caracteres incorrectos y sincronizar la base de datos, ¡no quedan resultados para mostrar!");
        put("feldolgozasbefejezodott",    "¡El procesamiento de datos está completo!");
        put("forditashozzaadas",          "Agregar traducción, subir a la base de datos");
        put("nemerhetoel",                "No está disponible");
        put("ellenorizelsouzenet",        "No se ha realizado ningún procesamiento de datos, ingrese sus datos de entrada y seleccione el botón 'Procesamiento de datos'.");
        put("ellenorizmasodikuzenet",     "¡No hay filas seleccionadas en la tabla!");
        put("kijeloltsornalnincsvaltozas","No hubo ningún cambio en la línea de pedido seleccionada que deba deshacerse.");
        put("ankiimportelkeszites",       "Preparación de la importación ANKI");
        put("adatbazisstatisztika",       "Estadísticas de la base de datos");
        put("szavakkikerdezese",          "Interrogar palabras con tarjetas de palabras");
        put("bezaras",                    "¿Estás seguro de que quieres cerrar el programa?");
        put("nevjegy",                    "Programa de aprendizaje de idiomas");
        
        // Anki-import ablak üzenetei
        put("akarankiimportotkesziteni",  "¿Realmente desea importar ANKI de cada nueva palabra que aprenda?");
        put("hibaskartyakeszites",        "¡Se produjo un error al crear la tarjeta!");
        put("kartyakelkeszitve",          "Tarjetas creadas con éxito:");
        put("fajlba",                     "archivo _ankiimport!");
        put("nincstanulando",             "¡No hay palabra que aprender de la cual hacer una tarjeta de palabras!");
        put("adjameganyelvet",            "Por favor ingrese el idioma!");
        
        // Fordítás ablak üzenetei
        put("irjonbeforditast",           "Introduzca una traducción para la palabra.");
        put("nincspeldamondat",           "¡No hay una oración de ejemplo para esta palabra!");
        
        // Kikérdezés ablak üzenetei
        put("melyiknyelv",                "Seleccione el idioma para el que desea utilizar las tarjetas de palabras");
        put("nincsentanulando",           "¡No hay palabras para aprender ahora mismo!");
        put("kikerdezesvege",             "¡El interrogatorio ha terminado!");
        
        // Beállítások ablak üzenetei
        put("beallitasok",                "Configuraciones");
        put("nemszam",                    "Ingrese un número entero positivo.");
        put("adjonmegmindenadatot",       "Proporcione todos los detalles.");
        
        // Panel header feliratok
        put("tajekoztat",                 "¡Listo!");
        put("figyelmeztet",               "¡Atención!");
        put("hiba",                       "¡Culpa!");
        put("kilepesmegerosites",         "Confirmación de salida");
        put("ankiimportkeszites",         "Hacer tarjetas ANKI");
        put("kártyakesziteseredmeny",     "Resultado de fabricación de tarjetas");
        

        // Panel igen-nem gomb
        put("igen",                       "Si");
        put("nem",                        "No");
        
    }};
    
    
    ////////////////////////////////////////////////////////////////////////////
    // FRANCIA FELIRATOK
    ////////////////////////////////////////////////////////////////////////////
    
    public static final String [] FOABLAK_FRANCIAFELIRATOK = new String [] {
         "Options",
         "Préparation des importations ANKI",
         "Mots de requête",
         "Statistiques",
         "Sortie",
         "Autre",
         "Carte de visite",
         "Options de saisie des données:",
         "- Insérer un fichier texte externe",
         "Feuilleter",
         "- Copier le texte directement dans la zone de texte",
         "Ne pas lister les mots simples:",
         "Langue source (obligatoire)",
         "Traitement de l'information",
         "Résultat du traitement",
         "Mot à apprendre (1)",
         "Réglages",
         "Retrait (2)",
         "Mots",
         "Phrases",
         "La fréquence",
         "Détection du texte:",
         "Indice de lisibilité:",
         "Langue de l'interface",
         "",
         "",
         "Page suivante",
         "Achèvement"
    };
    
    public static final String [] ANKI_FRANCIAFELIRATOK = new String [] {
         "Veuillez sélectionner la langue à partir de laquelle apprendre l'importation ANKI",
         "Faire des cartes",
         "Annuler"
    };
    
    public static final String [] FORDITAS_FRANCIAFELIRATOK = new String [] {
         "Veuillez fournir une traduction du mot avant de sauvegarder!",
         "Article:",
         "Tisser",
         "Le mot doit commencer par une majuscule:",
         "Exemple",
         "Restaurer la phrase d'exemple d'origine",
         "Phrase précédente",
         "Phrase suivante",
         "Google Traduction",
         "Dictionnaire Cambridge (disponible en anglais uniquement)",
         "Duden (disponible en allemand uniquement)",
         "Traduire le mot:",
         "Ajouter"
    };
    
    public static final String [] NYELVEK_FRANCIA = new String [] {
        "Anglais", "Espagnol", "Français", "Allemand", "Italien", "Portugais", "Néerlandais", "Polonais", "Danois", "Tchèque", "Slovaque", "Slovène", "Hongrois "
    };
    
    public static final String [] KIKERDEZES_FRANCIAFELIRATOK = new String [] {
         "Veuillez sélectionner une langue:",
         "Lancer le sondage",
         "Montrer la réponse",
         "Encore",
         "Dur",
         "Bien",
         "Facile"
    };
    
    public static final String [] STATISZTIKA_FRANCIAFELIRATOK = new String [] {
         "Statistiques",
         "Veuillez sélectionner une langue:",
         "Tous les mots:",
         "Mots connus:",
         "",
         "Nombre total de mots à apprendre:",
         "Mots d'apprentissage exportés:",
         "Mots d'apprentissage non exportés:"
    };
    
    public static final String [] NEVJEGY_FRANCIAFELIRATOK = new String [] {
         "Faite par:",
         "Version:",
         "Afficher la documentation destinée aux développeurs dans votre navigateur",
         "Page du programme Github"
    };
    
    public static final String [] BEALLITASOK_FRANCIAFELIRATOK = new String [] {
         "Réglages",
         "Langue de l'interface:",
         "Langue cible (langue connue):",
         "Nombre de lignes du tableau:",
         "Porter secours",
         "Annuler"
    };
    
    public static final HashMap<String, String> UZENETEK_FRANCIA = new HashMap<String, String>() {{
        // Főablak üzenetei
        put("tallozassikeres",            "Naviguez avec succès!");
        put("tallozassikertelen",         "La navigation a échoué!");
        put("uresszovegmezo",             "Zone de texte vide! Veuillez saisir du texte ou utiliser le bouton Parcourir.");
        put("forrasnyelvis",              "Veuillez également spécifier la langue source!");
        put("feldolgozasfolyamatban",     "Traitement des données en cours");
        put("nincseredmeny",              "Après avoir supprimé les caractères incorrects et synchronisé la base de données, il ne reste plus de résultats à afficher!");
        put("feldolgozasbefejezodott",    "Le traitement des données est terminé!");
        put("forditashozzaadas",          "Ajouter une traduction, télécharger dans la base de données");
        put("nemerhetoel",                "Est indisponible");
        put("ellenorizelsouzenet",        "Aucun traitement de données n'a eu lieu, veuillez saisir vos données d'entrée et sélectionner le bouton «Traitement des données»!");
        put("ellenorizmasodikuzenet",     "Aucune ligne sélectionnée dans le tableau!");
        put("kijeloltsornalnincsvaltozas","Il n'y a eu aucune modification de l'élément de campagne sélectionné qui devrait être annulée!");
        put("ankiimportelkeszites",       "Préparation de l'import ANKI");
        put("adatbazisstatisztika",       "Statistiques de la base de données");
        put("szavakkikerdezese",          "Interroger des mots avec des cartes de mots");
        put("bezaras",                    "Voulez-vous vraiment fermer le programme?");
        put("nevjegy",                    "Programme d'apprentissage des langues");
        
        // Anki-import ablak üzenetei
        put("akarankiimportotkesziteni",  "Voulez-vous vraiment importer ANKI à partir de chaque nouveau mot que vous apprenez?");
        put("hibaskartyakeszites",        "Une erreur s'est produite lors de la création de la carte!");
        put("kartyakelkeszitve",          "Cartes créées avec succès:");
        put("fajlba",                     " _ankiimport fichier!");
        put("nincstanulando",             "Il n'y a pas de mot à retenir pour faire une carte de mots!");
        put("adjameganyelvet",            "Veuillez entrer la langue!");
        
        // Fordítás ablak üzenetei
        put("irjonbeforditast",           "Veuillez saisir une traduction pour le mot!");
        put("nincspeldamondat",           "Il n'y a pas de phrase d'exemple pour ce mot!");
        
        // Kikérdezés ablak üzenetei
        put("melyiknyelv",                "Veuillez sélectionner la langue dans laquelle vous souhaitez utiliser les cartes de mots");
        put("nincsentanulando",           "Il n'y a pas de mots à apprendre pour le moment!");
        put("kikerdezesvege",             "Le sondage est terminé!");
        
        // Beállítások ablak üzenetei
        put("beallitasok",                "Réglages");
        put("nemszam",                    "Entrez un entier positif.");
        put("adjonmegmindenadatot",       "Veuillez fournir tous les détails!");
        
        // Panel header feliratok
        put("tajekoztat",                 "Prêt!");
        put("figyelmeztet",               "Attention!");
        put("hiba",                       "Faute!");
        put("kilepesmegerosites",         "Confirmation de sortie");
        put("ankiimportkeszites",         "Faire des cartes ANKI");
        put("kártyakesziteseredmeny",     "Résultat de fabrication de la carte");
        

        // Panel igen-nem gomb
        put("igen",                       "Oui");
        put("nem",                        "Non");
        
    }};
}
