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
        "Inglés", "Español", "Francés", "Alemán", "Italiano", "Portugués", "Holandés", "Polaco", "Danés", "Checo", "Eslovaco", "Esloveno", "Húngaro"
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
        "Anglais", "Espagnol", "Français", "Allemand", "Italien", "Portugais", "Néerlandais", "Polonais", "Danois", "Tchèque", "Slovaque", "Slovène", "Hongrois"
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
    
    ////////////////////////////////////////////////////////////////////////////
    // NÉMET FELIRATOK
    ////////////////////////////////////////////////////////////////////////////
    
    public static final String [] FOABLAK_NEMETFELIRATOK = new String [] {
         "Optionen",
         "Vorbereitung der ANKI-Importe",
         "Wörter abfragen",
         "Statistiken",
         "Ausgang",
         "Andere",
         "Visitenkarte",
         "Dateneingabemöglichkeiten:",
         "- Externe Textdatei einfügen",
         "Durchsuche",
         "- Text direkt in das Textfeld kopieren",
         "Listen Sie keine einzelnen Wörter auf:",
         "Ausgangssprache (erforderlich)",
         "Datenverarbeitung",
         "Verarbeitungsergebnis",
         "Wort zum Lernen (1)",
         "Die Einstellungen",
         "Rückzug (2)",
         "Wörter",
         "Sätze",
         "Frequenz",
         "Texterkennung:",
         "Lesbarkeitsindex:",
         "Schnittstellensprache",
         "",
         "",
         "Nächste Seite",
         "Fertigstellung"
    };
    
    public static final String [] ANKI_NEMETFELIRATOK = new String [] {
        "Bitte wählen Sie die Sprache aus, aus der Sie den ANKI-Import lernen möchten.",
         "Karten machen",
         "Stornieren"
    };
    
    public static final String [] FORDITAS_NEMETFELIRATOK = new String [] {
        "Bitte geben Sie vor dem Speichern eine Übersetzung des Wortes an!",
         "Artikel:",
         "Weben",
         "Beginnen Sie mit einem Großbuchstaben:",
         "Beispiel",
         "Ursprünglichen Beispielsatz wiederherstellen",
         "Vorheriger Satz",
         "Nächster Satz",
         "Google Übersetzer",
         "Cambridge Dictionary (nur in Englisch verfügbar)",
         "Duden",
         "Wort übersetzen:",
         "Hinzufügen"
    };
    
    public static final String [] NYELVEK_NEMET = new String [] {
        "Englisch", "Spanisch", "Französisch", "Deutsch", "Italienisch", "Portugiesisch", "Niederländisch", "Polnisch", "Dänisch", "Tschechisch", "Slowakisch", "Slowenisch", "Ungarisch"
    };
    
    public static final String [] KIKERDEZES_NEMETFELIRATOK = new String [] {
         "Bitte wähle eine Sprache:",
         "Umfrage starten",
         "Zeige die Antwort",
         "Nochmal",
         "Schwer",
         "Gut",
         "Einfach"
    };
    
    public static final String [] STATISZTIKA_NEMETFELIRATOK = new String [] {
         "Statistiken",
         "Bitte wähle eine Sprache:",
         "Alle Worte:",
         "Bekannte Wörter:",
         "",
         "Insgesamt zu lernende Wörter:",
         "Exportierte Lernwörter:",
         "Nicht exportierte Lernwörter:"
    };
    
    public static final String [] NEVJEGY_NEMETFELIRATOK = new String [] {
         "Hergestellt von:",
         "Ausführung:",
         "Entwicklerdokumentation in Ihrem Browser anzeigen",
         "Github-Programmseite"
    };
    
    public static final String [] BEALLITASOK_NEMETFELIRATOK = new String [] {
         "Die Einstellungen",
         "Schnittstellensprache:",
         "Zielsprache (bekannte Sprache):",
         "Anzahl der Zeilen in der Tabelle:",
         "Rettung",
         "Stornieren"
    };
    
    public static final HashMap<String, String> UZENETEK_NEMET = new HashMap<String, String>() {{
        // Főablak üzenetei
        put("tallozassikeres",            "Erfolgreich stöbern!");
        put("tallozassikertelen",         "Durchsuchen fehlgeschlagen!");
        put("uresszovegmezo",             "Textfeld leeren! Bitte geben Sie Text ein oder verwenden Sie die Schaltfläche Durchsuchen.");
        put("forrasnyelvis",              "Bitte geben Sie auch die Ausgangssprache an!");
        put("feldolgozasfolyamatban",     "Datenverarbeitung läuft");
        put("nincseredmeny",              "Nach dem Entfernen der falschen Zeichen und dem Synchronisieren der Datenbank sind keine Ergebnisse mehr anzuzeigen!");
        put("feldolgozasbefejezodott",    "Die Datenverarbeitung ist abgeschlossen!");
        put("forditashozzaadas",          "Übersetzung hinzufügen, in Datenbank hochladen");
        put("nemerhetoel",                "Ist nicht verfügbar");
        put("ellenorizelsouzenet",        "Es hat keine Datenverarbeitung stattgefunden, bitte geben Sie Ihre Eingabedaten ein und klicken Sie auf die Schaltfläche 'Datenverarbeitung'!");
        put("ellenorizmasodikuzenet",     "Keine Zeilen in der Tabelle ausgewählt!");
        put("kijeloltsornalnincsvaltozas","Es wurde keine Änderung an der ausgewählten Werbebuchung vorgenommen, die rückgängig gemacht werden sollte!");
        put("ankiimportelkeszites",       "Vorbereitung des Anki-Imports");
        put("adatbazisstatisztika",       "Datenbankstatistik");
        put("szavakkikerdezese",          "Fragen Sie Wörter mit Wortkarten ab");
        put("bezaras",                    "Möchten Sie das Programm wirklich schließen?");
        put("nevjegy",                    "Sprachlernprogramm");
        
        // Anki-import ablak üzenetei
        put("akarankiimportotkesziteni",  "Möchten Sie wirklich ANKI aus jedem neuen Wort importieren, das Sie lernen?");
        put("hibaskartyakeszites",        "Beim Erstellen der Karte ist ein Fehler aufgetreten!");
        put("kartyakelkeszitve",          "Karten erfolgreich erstellt:");
        put("fajlba",                     "_ankiimport Datei!");
        put("nincstanulando",             "Es gibt kein Wort zu lernen, aus dem man eine Wortkarte machen kann!");
        put("adjameganyelvet",            "Bitte geben Sie die Sprache ein!");
        
        // Fordítás ablak üzenetei
        put("irjonbeforditast",           "Bitte geben Sie eine Übersetzung für das Wort ein!");
        put("nincspeldamondat",           "Es gibt keinen Beispielsatz für dieses Wort!");
        
        // Kikérdezés ablak üzenetei
        put("melyiknyelv",                "Bitte wählen Sie die Sprache aus, für die Sie Wortkarten verwenden möchten");
        put("nincsentanulando",           "Im Moment gibt es keine Wörter zu lernen!");
        put("kikerdezesvege",             "Das Verhör ist vorbei!");
        
        // Beállítások ablak üzenetei
        put("beallitasok",                "die Einstellungen");
        put("nemszam",                    "Geben Sie eine positive Ganzzahl ein.");
        put("adjonmegmindenadatot",       "Bitte geben Sie alle Details an!");
        
        // Panel header feliratok
        put("tajekoztat",                 "Bereit!");
        put("figyelmeztet",               "Beachtung!");
        put("hiba",                       "Fehler!");
        put("kilepesmegerosites",         "Bestätigung beenden");
        put("ankiimportkeszites",         "ANKI-Karten herstellen");
        put("kártyakesziteseredmeny",     "Kartenerstellungsergebnis");
        
        // Panel igen-nem gomb
        put("igen",                       "Ja");
        put("nem",                        "Nein");

    }};
    
    ////////////////////////////////////////////////////////////////////////////
    // OLASZ FELIRATOK
    ////////////////////////////////////////////////////////////////////////////
    
    public static final String [] FOABLAK_OLASZFELIRATOK = new String [] {
        "Opzioni",
         "Preparazione delle importazioni ANKI",
         "Parole di query",
         "Statistiche",
         "Uscita",
         "Altro",
         "Biglietto da visita",
         "Opzioni di immissione dati:",
         "- Inserisci file di testo esterno",
         "Navigare",
         "- Copia il testo direttamente nella casella di testo",
         "Non elencare singole parole:",
         "Lingua di origine (obbligatorio)",
         "Elaborazione dati",
         "Risultato elaborazione",
         "Parola da imparare (1)",
         "Impostazioni",
         "Ritiro (2)",
         "Parole",
         "Frasi",
         "Frequenza",
         "Riconoscimento del testo:",
         "Indice di leggibilità:",
         "Lingua dell'interfaccia",
         "",
         "",
         "Pagina successiva",
         "Completamento"
    };
    
    public static final String [] ANKI_OLASZFELIRATOK = new String [] {
         "Seleziona la lingua da cui imparare l'importazione ANKI",
         "Fare carte",
         "Annulla"
    };
    
    public static final String [] FORDITAS_OLASZFELIRATOK = new String [] {
         "Fornisci una traduzione della parola prima di salvare!",
         "Articolo:",
         "Tessere",
         "La parola deve iniziare con una lettera maiuscola:",
         "Esempio",
         "Ripristina la frase di esempio originale",
         "Frase precedente",
         "Frase successiva",
         "Google Traduttore",
         "Cambridge Dictionary (disponibile solo in inglese)",
         "Duden (disponibile solo in tedesco)",
         "Traduci parola:",
         "Inserisci"
    };
    
    public static final String [] NYELVEK_OLASZ = new String [] {
        "Inglese", "Spagnolo", "Francese", "Tedesco", "Italiano", "Portoghese", "Olandese", "Polacco", "Danese", "Ceco", "Slovacco", "Sloveno", "Ungherese"
    };
    
    public static final String [] KIKERDEZES_OLASZFELIRATOK = new String [] {
         "Seleziona una lingua:",
         "Avvia sondaggio",
         "Mostra risposta",
         "Ancora",
         "Difficile",
         "Buona",
         "Facile"
    };
    
    public static final String [] STATISZTIKA_OLASZFELIRATOK = new String [] {
         "Statistiche",
         "Seleziona una lingua:",
         "Tutte le parole:",
         "Parole conosciute:",
         "",
         "Parole totali da imparare:",
         "Parole di apprendimento esportate:",
         "Parole di apprendimento non esportate:"
    };
    
    public static final String [] NEVJEGY_OLASZFELIRATOK = new String [] {
         "Fatto da:",
         "Versione:",
         "Visualizza la documentazione per sviluppatori nel tuo browser",
         "Pagina del programma GitHub"
    };
    
    public static final String [] BEALLITASOK_OLASZFELIRATOK = new String [] {
         "Impostazioni",
         "Lingua dell'interfaccia:",
         "Lingua di destinazione (lingua conosciuta):",
         "Numero di righe nella tabella:",
         "Salvare",
         "Annulla"
    };
    
    public static final HashMap<String, String> UZENETEK_OLASZ = new HashMap<String, String>() {{
        // Főablak üzenetei
        put("tallozassikeres",            "Sfoglia con successo!");
        put("tallozassikertelen",         "Navigazione fallita!");
        put("uresszovegmezo",             "Casella di testo vuota! Immettere il testo o utilizzare il pulsante Sfoglia.");
        put("forrasnyelvis",              "Si prega di specificare anche la lingua di origine!");
        put("feldolgozasfolyamatban",     "Elaborazione dati in corso");
        put("nincseredmeny",              "Dopo aver rimosso i caratteri errati e aver sincronizzato il database, non ci sono risultati da visualizzare!");
        put("feldolgozasbefejezodott",    "L'elaborazione dei dati è terminata!");
        put("forditashozzaadas",          "Aggiungi traduzione, carica nel database");
        put("nemerhetoel",                "Non è disponibile");
        put("ellenorizelsouzenet",        "Non è stata eseguita alcuna elaborazione dei dati, inserire i dati inseriti e selezionare il pulsante \"Elaborazione dati\"!");
        put("ellenorizmasodikuzenet",     "Nessuna riga selezionata nella tabella!");
        put("kijeloltsornalnincsvaltozas","Nessuna modifica all'elemento pubblicitario selezionato che dovrebbe essere annullata.");
        put("ankiimportelkeszites",       "Preparazione dell'importazione ANKI");
        put("adatbazisstatisztika",       "Statistiche del database");
        put("szavakkikerdezese",          "Interroga le parole con le schede di parole");
        put("bezaras",                    "Sei sicuro di voler chiudere il programma?");
        put("nevjegy",                    "Programma di apprendimento delle lingue");
        
        // Anki-import ablak üzenetei
        put("akarankiimportotkesziteni",  "Vuoi davvero importare ANKI da ogni nuova parola che impari?");
        put("hibaskartyakeszites",        "Si è verificato un errore durante la creazione della carta!");
        put("kartyakelkeszitve",          "Carte create con successo:");
        put("fajlba",                     "_ankiimport file!");
        put("nincstanulando",             "Non c'è parola da imparare da cui creare una carta di parole!");
        put("adjameganyelvet",            "Per favore inserisci la lingua!");
        
        // Fordítás ablak üzenetei
        put("irjonbeforditast",           "Inserisci una traduzione per la parola!");
        put("nincspeldamondat",           "Non esiste una frase di esempio per questa parola!");
        
        // Kikérdezés ablak üzenetei
        put("melyiknyelv",                "Seleziona la lingua per la quale desideri utilizzare le schede di parole");
        put("nincsentanulando",           "Non ci sono parole da imparare in questo momento!");
        put("kikerdezesvege",             "L'interrogatorio è finito!");
        
        // Beállítások ablak üzenetei
        put("beallitasok",                "impostazioni");
        put("nemszam",                    "Immettere un numero intero positivo.");
        put("adjonmegmindenadatot",       "Si prega di fornire tutti i dettagli!");
        
        // Panel header feliratok
        put("tajekoztat",                 "Pronto!");
        put("figyelmeztet",               "Attenzione!");
        put("hiba",                       "Colpa!");
        put("kilepesmegerosites",         "Kilépés megerősítés");
        put("ankiimportkeszites",         "Fare carte ANKI");
        put("kártyakesziteseredmeny",     "Risultato della fabbricazione della carta");
        
        // Panel igen-nem gomb
        put("igen",                       "sì");
        put("nem",                        "No");

    }};
    
    ////////////////////////////////////////////////////////////////////////////
    // PORTUGÁL FELIRATOK
    ////////////////////////////////////////////////////////////////////////////
    
    public static final String [] FOABLAK_PORTUGALFELIRATOK = new String [] {
         "Opções",
         "Preparação de importação ANKI",
         "Palavras de consulta",
         "Estatisticas",
         "Saída",
         "De outros",
         "Cartão de visitas",
         "Opções de entrada de dados:",
         "- Inserir arquivo de texto externo",
         "Squeaky toy",
         "- Copie o texto diretamente na caixa de texto",
         "Não liste palavras simples:",
         "Idioma de origem (obrigatório)",
         "Processamento de dados",
         "Processando resultado",
         "Palavra a aprender (1)",
         "Configurações",
         "Retirada (2)",
         "Palavras",
         "Frases",
         "Frequência",
         "Reconhecimento de texto:",
         "Índice de legibilidade:",
         "Interface de linguagem",
         "",
         "",
         "Próxima página",
         "Conclusão" 
    };
    
    public static final String [] ANKI_PORTUGALFELIRATOK = new String [] {
         "Por favor, selecione de qual idioma aprender a importação ANKI,",
         "Fazendo cartões",
         "Cancelar"
    };
    
    public static final String [] FORDITAS_PORTUGALFELIRATOK = new String [] {
         "Forneça uma tradução da palavra antes de salvar!",
         "Artigo:",
         "Tecer",
         "Comece com uma letra maiúscula:",
         "Exemplo",
         "Restaurar frase de exemplo original",
         "Frase anterior",
         "Próxima frase",
         "Google Tradutor",
         "Cambridge Dictionary (disponível apenas em inglês)",
         "Duden (disponível apenas em alemão)",
         "Traduzir palavra:",
         "Adicionar"
    };
    
    public static final String [] NYELVEK_PORTUGAL = new String [] {
        "Inglês", "Espanhol", "Francês", "Alemão", "Italiano", "Português", "Holandês", "Polonês", "Dinamarquês", "Tcheco", "Eslovaco", "Esloveno", "Húngaro"
    };
    
    public static final String [] KIKERDEZES_PORTUGALFELIRATOK = new String [] {
         "Selecione um idioma:",
         "Iniciar votação",
         "Mostre a resposta",
         "Novamente",
         "Difícil",
         "Boa",
         "Fácil"
    };
    
    public static final String [] STATISZTIKA_PORTUGALFELIRATOK = new String [] {
         "Estatisticas",
         "Selecione um idioma:",
         "Todas as palavras:",
         "Palavras conhecidas:",
         "",
         "Total de palavras para aprender:",
         "Palavras de aprendizagem exportadas:",
         "Palavras de aprendizagem não exportadas:"
    };
    
    public static final String [] NEVJEGY_PORTUGALFELIRATOK = new String [] {
         "Feito por:",
         "Versão:",
         "Ver a documentação do desenvolvedor em seu navegador",
         "Página do programa Github"
    };
    
    public static final String [] BEALLITASOK_PORTUGALFELIRATOK = new String [] {
         "Configurações",
         "Interface de linguagem:",
         "Idioma de destino (idioma conhecido):",
         "Número de linhas na tabela:",
         "Resgate",
         "Cancelar"
    };
    
    public static final HashMap<String, String> UZENETEK_PORTUGAL = new HashMap<String, String>() {{
        // Főablak üzenetei
        put("tallozassikeres",            "Navegue com sucesso!");
        put("tallozassikertelen",         "A navegação falhou!");
        put("uresszovegmezo",             "Caixa de texto vazia! Por favor, insira o texto ou use o botão Navegar.");
        put("forrasnyelvis",              "Especifique também o idioma de origem!");
        put("feldolgozasfolyamatban",     "Processamento de dados em andamento");
        put("nincseredmeny",              "Depois de remover os caracteres incorretos e sincronizar o banco de dados, não há resultados para exibir!");
        put("feldolgozasbefejezodott",    "O processamento de dados foi concluído!");
        put("forditashozzaadas",          "Adicionar tradução, fazer upload para banco de dados");
        put("nemerhetoel",                "Está indisponível");
        put("ellenorizelsouzenet",        "Nenhum processamento de dados ocorreu, insira seus dados de entrada e selecione o botão 'Processamento de dados'!");
        put("ellenorizmasodikuzenet",     "Nenhuma linha selecionada na tabela!");
        put("kijeloltsornalnincsvaltozas","Não houve alteração no item de linha selecionado que deva ser desfeita!");
        put("ankiimportelkeszites",       "Preparação de importação ANKI");
        put("adatbazisstatisztika",       "Estatísticas de banco de dados");
        put("szavakkikerdezese",          "Interrogue palavras com cartões de palavras");
        put("bezaras",                    "Tem certeza que deseja fechar o programa?");
        put("nevjegy",                    "Programa de aprendizagem de línguas");
        
        // Anki-import ablak üzenetei
        put("akarankiimportotkesziteni",  "Você realmente deseja importar ANKI de cada nova palavra que aprender?");
        put("hibaskartyakeszites",        "Ocorreu um erro ao criar o cartão!");
        put("kartyakelkeszitve",          "Cartões criados com sucesso:");
        put("fajlba",                     "_ankiimport file!");
        put("nincstanulando",             "Não há palavra para aprender com a qual fazer um cartão de palavras!");
        put("adjameganyelvet",            "Por favor, insira o idioma!");
        
        // Fordítás ablak üzenetei
        put("irjonbeforditast",           "Por favor, insira uma tradução para a palavra!");
        put("nincspeldamondat",           "Não há frase de exemplo para esta palavra!");
        
        // Kikérdezés ablak üzenetei
        put("melyiknyelv",                "Selecione o idioma para o qual deseja usar os cartões de palavras");
        put("nincsentanulando",           "Não há palavras para aprender agora!");
        put("kikerdezesvege",             "A votação acabou!");
        
        // Beállítások ablak üzenetei
        put("beallitasok",                "Configurações");
        put("nemszam",                    "Insira um número inteiro positivo.");
        put("adjonmegmindenadatot",       "Forneça todos os detalhes!");
        
        // Panel header feliratok
        put("tajekoztat",                 "Pronto!");
        put("figyelmeztet",               "Atenção!");
        put("hiba",                       "Culpa!");
        put("kilepesmegerosites",         "Confirmação de saída");
        put("ankiimportkeszites",         "Fazendo cartões ANKI");
        put("kártyakesziteseredmeny",     "Resultado da confecção do cartão");
        
        // Panel igen-nem gomb
        put("igen",                       "Sim");
        put("nem",                        "Não");

    }};
    
    ////////////////////////////////////////////////////////////////////////////
    // HOLLAND FELIRATOK
    ////////////////////////////////////////////////////////////////////////////
    
    public static final String [] FOABLAK_HOLLANDFELIRATOK = new String [] {
         "Opties",
         "Voorbereiding van ANKI-invoer",
         "Zoekwoorden",
         "Statistieken",
         "Uitgang",
         "Anders",
         "Visitekaartje",
         "Opties voor gegevensinvoer:",
         "- Voeg extern tekstbestand in",
         "Bladeren",
         "- Kopieer tekst rechtstreeks naar het tekstvak",
         "Geen enkele woorden vermelden:",
         "Brontaal (verplicht)",
         "Gegevensverwerking",
         "Verwerkingsresultaat",
         "Woord om te leren (1)",
         "Instellingen",
         "Intrekking (2)",
         "Woorden",
         "Zinnen",
         "Frequentie",
         "Tekstherkenning:",
         "Leesbaarheidsindex:",
         "Interfacetaal",
         "",
         "",
         "Volgende pagina",
         "Voltooiing"
    };
    
    public static final String [] ANKI_HOLLANDFELIRATOK = new String [] {
         "Selecteer van welke taal je ANKI-import wilt leren,",
         "Kaarten maken",
         "Annuleren"
    };
    
    public static final String [] FORDITAS_HOLLANDFELIRATOK = new String [] {
         "Geef een vertaling van het woord op voordat u opslaat!",
         "Artikel:",
         "Weven",
         "Het woord moet beginnen met een hoofdletter:",
         "Voorbeeld",
         "Herstel originele voorbeeldzin",
         "Vorige zin",
         "Volgende zin",
         "Google Vertalen",
         "Cambridge Dictionary (alleen beschikbaar in het Engels)",
         "Duden (alleen beschikbaar in het Duits)",
         "Vertaal woord:",
         "Toevoegen"
    };
    
    public static final String [] NYELVEK_HOLLAND = new String [] {
        "Engels", "Spaans", "Frans", "Duits", "Italiaans", "Portugees", "Nederlands", "Pools", "Deens", "Tsjechisch", "Slowaaks", "Sloveens", "Hongaars"
    };
    
    public static final String [] KIKERDEZES_HOLLANDFELIRATOK = new String [] {
         "Selecteer een taal:",
         "Start poll",
         "Toon het antwoord",
         "Nog een keer",
         "Moeilijk",
         "Mooi zo",
         "Gemakkelijk"
    };
    
    public static final String [] STATISZTIKA_HOLLANDFELIRATOK = new String [] {
         "Statistieken",
         "Selecteer een taal:",
         "Alle woorden:",
         "Bekende woorden:",
         "",
         "Totaal aantal woorden om te leren:",
         "Geëxporteerde leerwoorden:",
         "Ongeëxporteerde leerwoorden:"
    };
    
    public static final String [] NEVJEGY_HOLLANDFELIRATOK = new String [] {
         "Gemaakt door:",
         "Versie:",
         "Bekijk de documentatie voor ontwikkelaars in uw browser",
         "Github-programmapagina"
    };
    
    public static final String [] BEALLITASOK_HOLLANDFELIRATOK = new String [] {
         "Instellingen",
         "Interfacetaal:",
         "Doeltaal (bekende taal):",
         "Aantal rijen in de tabel:",
         "Redden",
         "Annuleren"
    };
    
    public static final HashMap<String, String> UZENETEK_HOLLAND = new HashMap<String, String>() {{
        // Főablak üzenetei
        put("tallozassikeres",            "Bladeren succesvol!");
        put("tallozassikertelen",         "Browsen mislukt!");
        put("uresszovegmezo",             "Leeg tekstvak! Voer tekst in of gebruik de knop Bladeren.");
        put("forrasnyelvis",              "Geef ook de brontaal op!");
        put("feldolgozasfolyamatban",     "Gegevens worden verwerkt");
        put("nincseredmeny",              "Na het verwijderen van de verkeerde karakters en het synchroniseren van de database, zijn er geen resultaten meer om weer te geven!");
        put("feldolgozasbefejezodott",    "De gegevensverwerking is voltooid!");
        put("forditashozzaadas",          "Vertaling toevoegen, uploaden naar database");
        put("nemerhetoel",                "Is niet beschikbaar");
        put("ellenorizelsouzenet",        "Geen gegevensverwerking, voer de invoergegevens in en selecteer de knop 'Gegevensverwerking'!");
        put("ellenorizmasodikuzenet",     "Geen rijen geselecteerd in de tabel!");
        put("kijeloltsornalnincsvaltozas","Er was geen wijziging in het geselecteerde regelitem die ongedaan moet worden gemaakt!");
        put("ankiimportelkeszites",       "Voorbereiding van ANKI-import");
        put("adatbazisstatisztika",       "Database statistieken");
        put("szavakkikerdezese",          "Ondervraag woorden met woordkaarten");
        put("bezaras",                    "Weet u zeker dat u het programma wilt sluiten?");
        put("nevjegy",                    "Taal leerprogramma");
        
        // Anki-import ablak üzenetei
        put("akarankiimportotkesziteni",  "Wil je echt ANKI importeren van elk nieuw woord dat je leert?");
        put("hibaskartyakeszites",        "Er is een fout opgetreden bij het maken van de kaart!");
        put("kartyakelkeszitve",          "Kaarten met succes gemaakt:");
        put("fajlba",                     "_ankiimport-bestand!");
        put("nincstanulando",             "Er is geen woord om van te leren om een ​​woordkaart te maken!");
        put("adjameganyelvet",            "Voer de taal in!");
        
        // Fordítás ablak üzenetei
        put("irjonbeforditast",           "Voer een vertaling voor het woord in!");
        put("nincspeldamondat",           "Er is geen voorbeeldzin voor dit woord!");
        
        // Kikérdezés ablak üzenetei
        put("melyiknyelv",                "Selecteer de taal waarvoor u woordkaarten wilt gebruiken");
        put("nincsentanulando",           "Er zijn op dit moment geen woorden om te leren!");
        put("kikerdezesvege",             "Het verhoor is voorbij!");
        
        // Beállítások ablak üzenetei
        put("beallitasok",                "Instellingen");
        put("nemszam",                    "Voer een positief geheel getal in.");
        put("adjonmegmindenadatot",       "Geef alle details op!");
        
        // Panel header feliratok
        put("tajekoztat",                 "Klaar!");
        put("figyelmeztet",               "Aandacht!");
        put("hiba",                       "Fout!");
        put("kilepesmegerosites",         "Bevestiging afsluiten");
        put("ankiimportkeszites",         "ANKI-kaarten maken");
        put("kártyakesziteseredmeny",     "Kaart maken resultaat");
        
        // Panel igen-nem gomb
        put("igen",                       "Ja");
        put("nem",                        "Nee");

    }};
    
    ////////////////////////////////////////////////////////////////////////////
    // LENGYEL FELIRATOK
    ////////////////////////////////////////////////////////////////////////////
    
    public static final String [] FOABLAK_LENGYELFELIRATOK = new String [] {
         "Opcje",
         "Przygotowanie importu ANKI",
         "Zapytanie o słowa",
         "Statystyka",
         "Wyjście",
         "Inny",
         "Wizytówka",
         "Opcje wprowadzania danych:",
         "- Wstaw zewnętrzny plik tekstowy",
         "Przeglądaj",
         "- Kopiuj tekst bezpośrednio do pola tekstowego",
         "Nie wymieniaj pojedynczych słów:",
         "Język źródłowy (wymagany)",
         "Przetwarzanie danych",
         "Wynik przetwarzania",
         "Słowo do nauczenia (1)",
         "Ustawienia",
         "Wycofanie (2)",
         "Słowa",
         "Zdania",
         "Częstotliwość",
         "Rozpoznawanie tekstu:",
         "Indeks czytelności:",
         "Język interfejsu",
         "",
         "",
         "Następna strona",
         "Ukończenie"
    };
    
    public static final String [] ANKI_LENGYELFELIRATOK = new String [] {
         "Wybierz język, z którego chcesz uczyć się importu ANKI",
         "Robienie kartek",
         "Anuluj"
    };
    
    public static final String [] FORDITAS_LENGYELFELIRATOK = new String [] {
         "Proszę podać tłumaczenie słowa przed zapisaniem!",
         "Artykuł:",
         "Splot",
         "Słowo musi zaczynać się wielką literą:",
         "Przykład",
         "Przywróć oryginalne zdanie przykładowe",
         "Poprzednie zdanie",
         "Następne zdanie",
         "tłumacz Google",
         "Cambridge Dictionary (dostępny tylko w języku angielskim)",
         "Duden (dostępne tylko w języku niemieckim)",
         "Przetłumacz słowo:",
         "Dodaj"
    };
    
    public static final String [] NYELVEK_LENGYEL = new String [] {
        "Angielski", "Hiszpański", "Francuski", "Niemiecki", "Włoski", "Portugalski", "Niderlandzki", "Polski", "Duński", "Czeski", "Słowacki", "Słoweński", "Węgierski"
    };
    
    public static final String [] KIKERDEZES_LENGYELFELIRATOK = new String [] {
         "Proszę wybierz język:",
         "Rozpocznij ankietę",
         "Pokaż odpowiedź",
         "Jeszcze raz",
         "Ciężko",
         "Dobry",
         "Łatwy"
    };
    
    public static final String [] STATISZTIKA_LENGYELFELIRATOK = new String [] {
         "Statystyka",
         "Proszę wybierz język:",
         "Wszystkie słowa:",
         "Znane słowa:",
         "",
         "Razem słów do nauczenia:",
         "Wyeksportowane słowa nauki:",
         "Niewysłane słowa do nauki:"
    };
    
    public static final String [] NEVJEGY_LENGYELFELIRATOK = new String [] {
         "Zrobione przez:",
         "Wersja:",
         "Wyświetl dokumentację programisty w przeglądarce",
         "Strona programu Github"
    };
    
    public static final String [] BEALLITASOK_LENGYELFELIRATOK = new String [] {
         "Ustawienia",
         "Język interfejsu:",
         "Język docelowy (znany język):",
         "Liczba wierszy w tabeli:",
         "Ratować",
         "Anuluj"
    };
    
    public static final HashMap<String, String> UZENETEK_LENGYEL = new HashMap<String, String>() {{
        // Főablak üzenetei
        put("tallozassikeres",            "Przeglądaj pomyślnie!");
        put("tallozassikertelen",         "Przeglądanie nie powiodło się!");
        put("uresszovegmezo",             "Puste pole tekstowe! Wprowadź tekst lub użyj przycisku Przeglądaj.");
        put("forrasnyelvis",              "Proszę również podać język źródłowy!");
        put("feldolgozasfolyamatban",     "Trwa przetwarzanie danych");
        put("nincseredmeny",              "Po usunięciu nieprawidłowych znaków i zsynchronizowaniu bazy danych nie ma już żadnych wyników do wyświetlenia!");
        put("feldolgozasbefejezodott",    "Przetwarzanie danych zakończone!");
        put("forditashozzaadas",          "Dodaj tłumaczenie, załaduj do bazy danych");
        put("nemerhetoel",                "Jest niedostępny");
        put("ellenorizelsouzenet",        "Żadne przetwarzanie danych nie miało miejsca, wprowadź swoje dane wejściowe i wybierz przycisk „Przetwarzanie danych”!");
        put("ellenorizmasodikuzenet",     "Nie wybrano wierszy w tabeli!");
        put("kijeloltsornalnincsvaltozas","Nie było zmian w wybranym elemencie zamówienia, które należy cofnąć!");
        put("ankiimportelkeszites",       "Przygotowanie importu ANKI");
        put("adatbazisstatisztika",       "Statystyki baz danych");
        put("szavakkikerdezese",          "Przesłuchuj słowa za pomocą kart ze słowami");
        put("bezaras",                    "Czy na pewno chcesz zamknąć program?");
        put("nevjegy",                    "Program do nauki języków");
        
        // Anki-import ablak üzenetei
        put("akarankiimportotkesziteni",  "Czy naprawdę chcesz importować ANKI z każdego nowego słowa, którego się uczysz?");
        put("hibaskartyakeszites",        "Wystąpił błąd podczas tworzenia karty!");
        put("kartyakelkeszitve",          "Karty pomyślnie utworzone:");
        put("fajlba",                     "plik _ankiimport!");
        put("nincstanulando",             "Nie ma słowa, z którego można by się uczyć, aby stworzyć kartę słowną!");
        put("adjameganyelvet",            "Proszę podać język!");
        
        // Fordítás ablak üzenetei
        put("irjonbeforditast",           "Wprowadź tłumaczenie słowa!");
        put("nincspeldamondat",           "Nie ma przykładowego zdania dla tego słowa!");
        
        // Kikérdezés ablak üzenetei
        put("melyiknyelv",                "Wybierz język, dla którego chcesz używać kart słownych");
        put("nincsentanulando",           "W tej chwili nie ma słów do nauczenia!");
        put("kikerdezesvege",             "Przesłuchanie się skończyło!");
        
        // Beállítások ablak üzenetei
        put("beallitasok",                "Ustawienia");
        put("nemszam",                    "Wpisz dodatnią liczbę całkowitą.");
        put("adjonmegmindenadatot",       "Proszę podać wszystkie szczegóły!");
        
        // Panel header feliratok
        put("tajekoztat",                 "Gotowy!");
        put("figyelmeztet",               "Uwaga!");
        put("hiba",                       "Wina!");
        put("kilepesmegerosites",         "Wyjście z potwierdzenia");
        put("ankiimportkeszites",         "Tworzenie kart ANKI");
        put("kártyakesziteseredmeny",     "Wynik tworzenia kart");
        
        // Panel igen-nem gomb
        put("igen",                       "Tak");
        put("nem",                        "Nie");

    }};
    
    ////////////////////////////////////////////////////////////////////////////
    // DÁN FELIRATOK
    ////////////////////////////////////////////////////////////////////////////
    
    public static final String [] FOABLAK_DANFELIRATOK = new String [] {
         "Muligheder",
         "Forberedelse af ANKI-import",
         "Forespørgselsord",
         "Statistikker",
         "Afslut",
         "Andet",
         "Visitkort",
         "Indstillinger for dataindtastning:",
         "- Indsæt ekstern tekstfil",
         "Gennemse",
         "- Kopier tekst direkte i tekstfeltet",
         "Angiv ikke enkelte ord:",
         "Kildesprog (påkrævet)",
         "Databehandling",
         "Behandlingsresultat",
         "Ord at lære (1)",
         "Indstillinger",
         "Tilbagetrækning (2)",
         "Ord",
         "Sætninger",
         "Frekvens",
         "Tekstgenkendelse:",
         "Læsbarhedsindeks:",
         "Grænsefladesprog",
         "",
         "",
         "Næste side",
         "Færdiggørelse"
    };
    
    public static final String [] ANKI_DANFELIRATOK = new String [] {
         "Vælg hvilket sprog du vil lære ANKI-import fra,",
         "At lave kort",
         "Afbestille"
    };
    
    public static final String [] FORDITAS_DANFELIRATOK = new String [] {
         "Angiv venligst en oversættelse af ordet, før du gemmer!",
         "Artikel:",
         "Væve",
         "Ordet skal starte med et stort bogstav:",
         "Eksempel",
         "Gendan original sætning",
         "Forrige sætning",
         "Næste sætning",
         "Google Oversæt",
         "Cambridge Dictionary (kun tilgængelig på engelsk)",
         "Duden (kun tilgængelig på tysk)",
         "Oversæt ord:",
         "Tilføje"
    };
    
    public static final String [] NYELVEK_DAN = new String [] {
        "Engelsk", "Spansk", "Fransk", "Tysk", "Italiensk", "Portugisisk", "Hollandsk", "Polsk", "Dansk", "Tjekkisk", "Slovakisk", "Slovensk", "Ungarsk"
    };
    
    public static final String [] KIKERDEZES_DANFELIRATOK = new String [] {
         "Vælg et sprog:",
         "Start afstemning",
         "Vis svar",
         "Igen",
         "Svært",
         "Godt",
         "Let"
    };
    
    public static final String [] STATISZTIKA_DANFELIRATOK = new String [] {
         "Statistikker",
         "Vælg et sprog:",
         "Alle ord:",
         "Kendte ord:",
         "",
         "Samlede ord at lære:",
         "Eksporterede læringsord:",
         "Uporterede læringsord:"
    };
    
    public static final String [] NEVJEGY_DANFELIRATOK = new String [] {
         "Lavet af:",
         "Version:",
         "Se dokumentation til udvikler i din browser",
         "Github-programside"
    };
    
    public static final String [] BEALLITASOK_DANFELIRATOK = new String [] {
         "Indstillinger",
         "Grænsefladesprog:",
         "Målsprog (kendt sprog):",
         "Antal rækker i tabellen:",
         "Redde",
         "Afbestille"
    };
    
    public static final HashMap<String, String> UZENETEK_DAN = new HashMap<String, String>() {{
        // Főablak üzenetei
        put("tallozassikeres",            "Gennemse vellykket!");
        put("tallozassikertelen",         "Browsing mislykkedes!");
        put("uresszovegmezo",             "Tom tekstboks! Indtast venligst tekst, eller brug knappen Gennemse.");
        put("forrasnyelvis",              "Angiv også kildesproget!");
        put("feldolgozasfolyamatban",     "Databehandling er i gang");
        put("nincseredmeny",              "Efter fjernelse af de forkerte tegn og synkronisering af databasen er der ingen resultater tilbage at vise!");
        put("feldolgozasbefejezodott",    "Databehandlingen er afsluttet!");
        put("forditashozzaadas",          "Tilføj oversættelse, upload til database");
        put("nemerhetoel",                "Er ikke tilgængelig");
        put("ellenorizelsouzenet",        "Ingen databehandling har fundet sted, bedes du indtaste dine inputdata og vælge knappen 'Databehandling'!");
        put("ellenorizmasodikuzenet",     "Ingen rækker valgt i tabellen!");
        put("kijeloltsornalnincsvaltozas","Der var ingen ændringer i den valgte linjepost, der skulle fortrydes!");
        put("ankiimportelkeszites",       "Forberedelse af ANKI import");
        put("adatbazisstatisztika",       "Databasestatistikker");
        put("szavakkikerdezese",          "Afhør ord med ordkort");
        put("bezaras",                    "Er du sikker på, at du vil lukke programmet?");
        put("nevjegy",                    "Program for sprogindlæring");
        
        // Anki-import ablak üzenetei
        put("akarankiimportotkesziteni",  "Vil du virkelig importere ANKI fra hvert nye ord, du lærer?");
        put("hibaskartyakeszites",        "Der opstod en fejl under oprettelsen af ​​kortet!");
        put("kartyakelkeszitve",          "Kort oprettet med succes:");
        put("fajlba",                     "_ankiimport-fil!");
        put("nincstanulando",             "Der er intet ord at lære at lave et ordkort fra!");
        put("adjameganyelvet",            "Indtast venligst sproget!");
        
        // Fordítás ablak üzenetei
        put("irjonbeforditast",           "Indtast venligst en oversættelse af ordet!");
        put("nincspeldamondat",           "Der er ingen eksempler på sætning for dette ord!");
        
        // Kikérdezés ablak üzenetei
        put("melyiknyelv",                "Vælg det sprog, du vil bruge ordkort til");
        put("nincsentanulando",           "Der er ingen ord at lære lige nu!");
        put("kikerdezesvege",             "Afhør er overstået!");
        
        // Beállítások ablak üzenetei
        put("beallitasok",                "Indstillinger");
        put("nemszam",                    "Indtast et positivt heltal.");
        put("adjonmegmindenadatot",       "Angiv alle detaljer!");
        
        // Panel header feliratok
        put("tajekoztat",                 "Parat!");
        put("figyelmeztet",               "Opmærksomhed!");
        put("hiba",                       "Fejl!");
        put("kilepesmegerosites",         "Bekræftelse af udgang");
        put("ankiimportkeszites",         "Fremstilling af ANKI-kort");
        put("kártyakesziteseredmeny",     "Resultat af kortfremstilling");
        
        // Panel igen-nem gomb
        put("igen",                       "Ja");
        put("nem",                        "Ingen");

    }};
    
    ////////////////////////////////////////////////////////////////////////////
    // CSEH FELIRATOK
    ////////////////////////////////////////////////////////////////////////////
    
    public static final String [] FOABLAK_CSEHFELIRATOK = new String [] {
        "Možnosti",
         "Příprava importu ANKI",
         "Dotazová slova",
         "Statistika",
         "Výstup",
         "Jiný",
         "Vizitka",
         "Možnosti zadávání údajů:",
         "- Vložit externí textový soubor",
         "Procházet",
         "- Zkopírovat text přímo do textového pole",
         "Neuvádějte jednotlivá slova:",
         "Zdrojový jazyk (povinný)",
         "Zpracování dat",
         "Výsledek zpracování",
         "Word to learn (1)",
         "Nastavení",
         "Odstoupení (2)",
         "Slova",
         "Věty",
         "Frekvence",
         "Rozpoznávání textu:",
         "Readability index:",
         "Language interface",
         "",
         "",
         "Další strana",
         "Dokončení"
    };
    
    public static final String [] ANKI_CSEHFELIRATOK = new String [] {
         "Vyberte, ze kterého jazyka se chcete naučit importovat ANKI,",
         "Vytváření karet",
         "Zrušení"
    };
    
    public static final String [] FORDITAS_CSEHFELIRATOK = new String [] {
         "Před uložením prosím poskytněte překlad slova!",
         "Článek:",
         "Vazba",
         "Začít velkým písmenem:",
         "Příklad",
         "Obnovit původní ukázkovou větu",
         "Předchozí věta",
         "Další věta",
         "Google Překladač",
         "Cambridge Dictionary (k dispozici pouze v angličtině)",
         "Duden (k dispozici pouze v němčině)",
         "Přeložit slovo:",
         "Přidat"
    };
    
    public static final String [] NYELVEK_CSEH = new String [] {
        "Angličtina", "Španělština", "Francouzština", "Němčina", "Italština", "Portugalština", "Holandština", "Polština", "Dánština", "Čeština", "Slovenština", "Slovinština", "Maďarština"
    };
    
    public static final String [] KIKERDEZES_CSEHFELIRATOK = new String [] {
         "Prosím vyberte jazyk:",
         "Zahájit hlasování",
         "Ukaž odpověď",
         "Znovu",
         "Tvrdý",
         "Dobrý",
         "Snadný"
    };
    
    public static final String [] STATISZTIKA_CSEHFELIRATOK = new String [] {
         "Statistika",
         "Prosím vyberte jazyk:",
         "Všechna slova:",
         "Známá slova:",
         "",
         "Total words to learn:",
         "Exported learning words:",
         "Neexportovaná výuková slova:"
    };
    
    public static final String [] NEVJEGY_CSEHFELIRATOK = new String [] {
         "Vytvořil:",
         "Verze:",
         "Zobrazit dokumentaci pro vývojáře ve vašem prohlížeči",
         "Stránka programu Github"
    };
    
    public static final String [] BEALLITASOK_CSEHFELIRATOK = new String [] {
         "Nastavení",
         "Language interface:",
         "Target language (known language):",
         "Počet řádků v tabulce:",
         "Zachránit",
         "Zrušení"
    };
    
    public static final HashMap<String, String> UZENETEK_CSEH = new HashMap<String, String>() {{
        // Főablak üzenetei
        put("tallozassikeres",            "Procházet úspěšné!");
        put("tallozassikertelen",         "Procházení se nezdařilo!");
        put("uresszovegmezo",             "Prázdné textové pole! Zadejte prosím text nebo použijte tlačítko Procházet.");
        put("forrasnyelvis",              "Uveďte prosím také zdrojový jazyk!");
        put("feldolgozasfolyamatban",     "Probíhá zpracování dat");
        put("nincseredmeny",              "Po odstranění nesprávných znaků a synchronizaci databáze nezbývají žádné výsledky k zobrazení!");
        put("feldolgozasbefejezodott",    "Zpracování dat je dokončeno!");
        put("forditashozzaadas",          "Přidejte překlad, nahrajte do databáze");
        put("nemerhetoel",                "Není k dispozici");
        put("ellenorizelsouzenet",        "Žádné zpracování údajů neproběhlo, zadejte své vstupní údaje a klikněte na tlačítko „Zpracování údajů“!");
        put("ellenorizmasodikuzenet",     "V tabulce nejsou vybrány žádné řádky!");
        put("kijeloltsornalnincsvaltozas","U vybrané řádkové položky nedošlo k žádné změně, kterou by bylo možné vrátit zpět!");
        put("ankiimportelkeszites",       "Příprava importu ANKI");
        put("adatbazisstatisztika",       "Statistiky databáze");
        put("szavakkikerdezese",          "Vyslýchejte slova pomocí slovních karet");
        put("bezaras",                    "Opravdu chcete program ukončit?");
        put("nevjegy",                    "Program výuky jazyků");
        
        // Anki-import ablak üzenetei
        put("akarankiimportotkesziteni",  "Opravdu chcete importovat ANKI z každého nového slova, které se naučíte?");
        put("hibaskartyakeszites",        "Při vytváření karty došlo k chybě!");
        put("kartyakelkeszitve",          "Karty úspěšně vytvořené:");
        put("fajlba",                     "_ankiimport soubor!");
        put("nincstanulando",             "Neexistuje slovo, ze kterého se můžete naučit, jak vytvořit kartu se slovy!");
        put("adjameganyelvet",            "Zadejte prosím jazyk!");
        
        // Fordítás ablak üzenetei
        put("irjonbeforditast",           "Zadejte překlad slova!");
        put("nincspeldamondat",           "Pro toto slovo neexistuje žádná příkladná věta!");
        
        // Kikérdezés ablak üzenetei
        put("melyiknyelv",                "Vyberte prosím jazyk, ve kterém chcete používat slovní pohlednice");
        put("nincsentanulando",           "Právě teď se nemusíte učit žádná slova!");
        put("kikerdezesvege",             "Výslech je u konce!");
        
        // Beállítások ablak üzenetei
        put("beallitasok",                "Nastavení");
        put("nemszam",                    "Zadejte kladné celé číslo.");
        put("adjonmegmindenadatot",       "Uveďte prosím všechny podrobnosti!");
        
        // Panel header feliratok
        put("tajekoztat",                 "Připraven!");
        put("figyelmeztet",               "Pozornost!");
        put("hiba",                       "Chyba!");
        put("kilepesmegerosites",         "Potvrzení o ukončení");
        put("ankiimportkeszites",         "Výroba ANKI karet");
        put("kártyakesziteseredmeny",     "Výsledek tvorby karty");
        
        // Panel igen-nem gomb
        put("igen",                       "Ano");
        put("nem",                        "Ne");

    }};
    
    ////////////////////////////////////////////////////////////////////////////
    // SZLOVÁK FELIRATOK
    ////////////////////////////////////////////////////////////////////////////
    
    public static final String [] FOABLAK_SZLOVAKFELIRATOK = new String [] {
        "Možnosti",
         "Príprava dovozu ANKI",
         "Query words",
         "Statistics",
         "Východ",
         "Iné",
         "Vizitka",
         "Možnosti zadávania údajov:",
         "- Vložiť externý textový súbor",
         "Prechádzať",
         "- Kopírovať text priamo do textového poľa",
         "Neuvádzajte jednotlivé slová:",
         "Zdrojový jazyk (povinný)",
         "Spracovanie dát",
         "Výsledok spracovania",
         "Word to learn (1)",
         "Nastavenie",
         "Odstúpenie (2)",
         "Slová",
         "Vety",
         "Frequency",
         "Povedomie o texte:",
         "Index čitateľnosti:",
         "Jazyk rozhrania",
         "",
         "",
         "Ďalšia strana",
         "Dokončenie"
    };
    
    public static final String [] ANKI_SZLOVAKFELIRATOK = new String [] {
         "Vyberte, z ktorého jazyka sa chcete učiť importovať ANKI,",
         "Výroba kariet",
         "Zrušiť"
    };
    
    public static final String [] FORDITAS_SZLOVAKFELIRATOK = new String [] {
         "Pred uložením poskytnite preklad slova!",
         "Article:",
         "Tkať",
         "Začnite veľkým písmenom:",
         "Príklad",
         "Obnoviť pôvodnú vzorovú vetu",
         "Predchádzajúca veta",
         "Ďalšia veta",
         "Google Translate",
         "Cambridge Dictionary (k dispozícii iba v angličtine)",
         "Duden (k dispozícii iba v nemčine)",
         "Preložiť slovo:",
         "Pridať"
    };
    
    public static final String [] NYELVEK_SZLOVAK = new String [] {
        "Angličtina", "Španielčina", "Francúzština", "Nemčina", "Taliančina", "Portugalčina", "Holandčina", "Poľština", "Dánčina", "Čeština", "Slovenčina", "Slovinčina", "Maďarčina"
    };
    
    public static final String [] KIKERDEZES_SZLOVAKFELIRATOK = new String [] {
         "Prosím, zvoľte jazyk:",
         "Spustiť anketu",
         "Ukáž odpoveď",
         "Again",
         "Tvrdé",
         "Dobré",
         "Ľahké"
    };
    
    public static final String [] STATISZTIKA_SZLOVAKFELIRATOK = new String [] {
         "Statistics",
         "Prosím vyberte jazyk:",
         "All words:",
         "Známe slová:",
         "",
         "Total words to learn:",
         "Exported learning words:",
         "Nevydané učiace sa slová:"
    };
    
    public static final String [] NEVJEGY_SZLOVAKFELIRATOK = new String [] {
         "Vytvorené:",
         "Verzia:",
         "Zobraziť dokumentáciu pre vývojárov vo vašom prehliadači",
         "Stránka programu Github"
    };
    
    public static final String [] BEALLITASOK_SZLOVAKFELIRATOK = new String [] {
         "Nastavenie",
         "Language interface:",
         "Cieľový jazyk (známy jazyk):",
         "Počet riadkov v tabuľke:",
         "Záchranný",
         "Zrušiť"
    };
    
    public static final HashMap<String, String> UZENETEK_SZLOVAK = new HashMap<String, String>() {{
        // Főablak üzenetei
        put("tallozassikeres",            "Prehliadajte úspešne!");
        put("tallozassikertelen",         "Prehliadanie zlyhalo!");
        put("uresszovegmezo",             "Prázdne textové pole! Zadajte text alebo použite tlačidlo Prehľadávať.");
        put("forrasnyelvis",              "Zadajte tiež zdrojový jazyk!");
        put("feldolgozasfolyamatban",     "Prebieha spracovanie údajov");
        put("nincseredmeny",              "Po odstránení nesprávnych znakov a synchronizácii databázy nezostávajú žiadne výsledky na zobrazenie!");
        put("feldolgozasbefejezodott",    "Spracovanie údajov je dokončené!");
        put("forditashozzaadas",          "Pridajte preklad, nahrajte do databázy");
        put("nemerhetoel",                "Nie je k dispozícii");
        put("ellenorizelsouzenet",        "K spracovaniu údajov nedošlo, zadajte svoje vstupné údaje a kliknite na tlačidlo „Spracovanie údajov“!");
        put("ellenorizmasodikuzenet",     "V tabuľke nie sú vybraté žiadne riadky!");
        put("kijeloltsornalnincsvaltozas","Vo vybratej riadkovej položke nedošlo k nijakej zmene, ktorú by bolo možné vrátiť späť!");
        put("ankiimportelkeszites",       "Príprava importu ANKI");
        put("adatbazisstatisztika",       "Štatistika databázy");
        put("szavakkikerdezese",          "Vypočujte slová pomocou slovných kariet");
        put("bezaras",                    "Naozaj chcete program ukončiť?");
        put("nevjegy",                    "Program výučby jazykov");
        
        // Anki-import ablak üzenetei
        put("akarankiimportotkesziteni",  "Naozaj chcete importovať ANKI z každého nového slova, ktoré sa naučíte?");
        put("hibaskartyakeszites",        "Pri vytváraní karty sa vyskytla chyba!");
        put("kartyakelkeszitve",          "Karty úspešne vytvorené:");
        put("fajlba",                     "_ankiimport súbor!");
        put("nincstanulando",             "Neexistuje slovo, z ktorého by sa dalo vyrobiť slovné spojenie!");
        put("adjameganyelvet",            "Zadajte jazyk!");
        
        // Fordítás ablak üzenetei
        put("irjonbeforditast",           "Zadajte preklad slova!");
        put("nincspeldamondat",           "Pre toto slovo neexistuje príkladná veta!");
        
        // Kikérdezés ablak üzenetei
        put("melyiknyelv",                "Vyberte jazyk, pre ktorý chcete používať vizitky");
        put("nincsentanulando",           "Momentálne nie sú slová, ktoré by ste sa mali naučiť!");
        put("kikerdezesvege",             "Anketa sa skončila!");
        
        // Beállítások ablak üzenetei
        put("beallitasok",                "nastavenie");
        put("nemszam",                    "Zadajte kladné celé číslo.");
        put("adjonmegmindenadatot",       "Uveďte všetky podrobnosti!");
        
        // Panel header feliratok
        put("tajekoztat",                 "Pripravený!");
        put("figyelmeztet",               "Pozor!");
        put("hiba",                       "Porucha!");
        put("kilepesmegerosites",         "Potvrdenie o výstupe");
        put("ankiimportkeszites",         "Výroba kariet ANKI");
        put("kártyakesziteseredmeny",     "Výsledok výroby kariet");
        
        // Panel igen-nem gomb
        put("igen",                       "Áno");
        put("nem",                        "Nie");

    }};
    
    ////////////////////////////////////////////////////////////////////////////
    // SZLOVÉN FELIRATOK
    ////////////////////////////////////////////////////////////////////////////
    
    public static final String [] FOABLAK_SZLOVENFELIRATOK = new String [] {
         "Opcije",
         "Priprava uvoza ANKI",
         "Poizvedbene besede",
         "Statistika",
         "Izhod",
         "Drugo",
         "Vizitka",
         "Možnosti vnosa podatkov:",
         "- Vstavi zunanjo besedilno datoteko",
         "Brskaj",
         "- Kopiraj besedilo neposredno v besedilno polje",
         "Ne navajaj posameznih besed:",
         "Izvorni jezik (obvezno)",
         "Obdelava podatkov",
         "Obdelava rezultata",
         "Beseda za učenje (1)",
         "Nastavitve",
         "Umik (2)",
         "Besede",
         "Stavki",
         "Frekvenca",
         "Zavedanje besedila:",
         "Indeks berljivosti:",
         "Jezik vmesnika",
         "",
         "",
         "Naslednja stran",
         "Dokončanje"
    };
    
    public static final String [] ANKI_SZLOVENFELIRATOK = new String [] {
         "Izberite jezik, iz katerega se boste učili ANKI uvoz,",
         "Izdelovanje kart",
         "Prekliči"
    };
    
    public static final String [] FORDITAS_SZLOVENFELIRATOK = new String [] {
         "Pred shranjevanjem navedite prevod besede!",
         "Članek:",
         "Tkanje",
         "Beseda se mora začeti z veliko začetnico:",
         "Primer",
         "Obnovi izvirni primer stavka",
         "Prejšnji stavek",
         "Naslednji stavek",
         "Google prevajalnik",
         "Cambridge Dictionary (na voljo samo v angleščini)",
         "Duden (na voljo samo v nemščini)",
         "Prevedi besedo:",
         "Dodaj"
    };
    
    public static final String [] NYELVEK_SZLOVEN = new String [] {
        "Angleščina", "Španščina", "Francoščina", "Nemščina", "Italijanščina", "Portugalščina", "Nizozemščina", "Poljščina", "Danska", "Češčina", "Slovaščina", "Slovenščina", "Madžarščina"
    };
    
    public static final String [] KIKERDEZES_SZLOVENFELIRATOK = new String [] {
         "Izberite jezik:",
         "Začni anketo",
         "Pokaži odgovor",
         "Ponovno",
         "Težko",
         "Dobro",
         "Enostavno"
    };
    
    public static final String [] STATISZTIKA_SZLOVENFELIRATOK = new String [] {
         "Statistika",
         "Izberite jezik:",
         "Vse besede:",
         "Znane besede:",
         "",
         "Skupaj besed za učenje:",
         "Izvožene učne besede:",
         "Neizvožene učne besede:"
    };
    
    public static final String [] NEVJEGY_SZLOVENFELIRATOK = new String [] {
         "Narejeno v:",
         "Različica:",
         "Ogled dokumentacije za razvijalce v brskalniku",
         "Stran programa Github"
    };
    
    public static final String [] BEALLITASOK_SZLOVENFELIRATOK = new String [] {
         "Nastavitve",
         "Jezik vmesnika:",
         "Ciljni jezik (znani jezik):",
         "Število vrstic v tabeli:",
         "Reševanje",
         "Prekliči"
    };
    
    public static final HashMap<String, String> UZENETEK_SZLOVEN = new HashMap<String, String>() {{
        // Főablak üzenetei
        put("tallozassikeres",            "Brskanje uspešno!");
        put("tallozassikertelen",         "Brskanje ni uspelo!");
        put("uresszovegmezo",             "Prazno polje z besedilom! Vnesite besedilo ali uporabite gumb Prebrskaj.");
        put("forrasnyelvis",              "Navedite tudi izvorni jezik!");
        put("feldolgozasfolyamatban",     "Obdelava podatkov v teku");
        put("nincseredmeny",              "Po odstranitvi nepravilnih znakov in sinhronizaciji baze podatkov ni več rezultatov za prikaz!");
        put("feldolgozasbefejezodott",    "Obdelava podatkov je končana!");
        put("forditashozzaadas",          "Dodajte prevod, naložite v bazo podatkov");
        put("nemerhetoel",                "Ni na voljo");
        put("ellenorizelsouzenet",        "Obdelava podatkov ni bila izvedena, vnesite vhodne podatke in izberite gumb 'Obdelava podatkov'!");
        put("ellenorizmasodikuzenet",     "V tabeli ni izbrana nobena vrstica!");
        put("kijeloltsornalnincsvaltozas","Izbrane postavke vrstice ni bilo mogoče spremeniti!");
        put("ankiimportelkeszites",       "Priprava uvoza ANKI");
        put("adatbazisstatisztika",       "Statistika zbirk podatkov");
        put("szavakkikerdezese",          "Z besednimi karticami zaslišujte besede");
        put("bezaras",                    "Ali ste prepričani, da želite zapreti program?");
        put("nevjegy",                    "Program učenja jezikov");
        
        // Anki-import ablak üzenetei
        put("akarankiimportotkesziteni",  "Ali res želite uvoziti ANKI iz vsake nove naučene besede?");
        put("hibaskartyakeszites",        "Pri ustvarjanju kartice je prišlo do napake!");
        put("kartyakelkeszitve",          "Kartice so bile uspešno ustvarjene:");
        put("fajlba",                     "_ankiimport datoteka!");
        put("nincstanulando",             "Ni se mogoče naučiti besede, iz katere bi naredili besedno kartico!");
        put("adjameganyelvet",            "Prosimo, vnesite jezik!");
        
        // Fordítás ablak üzenetei
        put("irjonbeforditast",           "Prosimo, vnesite prevod besede!");
        put("nincspeldamondat",           "Za to besedo ni primera stavka!");
        
        // Kikérdezés ablak üzenetei
        put("melyiknyelv",                "Izberite jezik, za katerega želite uporabljati besedne kartice");
        put("nincsentanulando",           "Trenutno se ni mogoče naučiti besed!");
        put("kikerdezesvege",             "Anketa je končana!");
        
        // Beállítások ablak üzenetei
        put("beallitasok",                "Nastavitve");
        put("nemszam",                    "Vnesite pozitivno celo število.");
        put("adjonmegmindenadatot",       "Prosimo, navedite vse podrobnosti!");
        
        // Panel header feliratok
        put("tajekoztat",                 "Pripravljeni!");
        put("figyelmeztet",               "Pozor!");
        put("hiba",                       "Napaka!");
        put("kilepesmegerosites",         "Potrditev izstopa");
        put("ankiimportkeszites",         "Izdelava kartic ANKI");
        put("kártyakesziteseredmeny",     "Rezultat izdelave kart");
        
        // Panel igen-nem gomb
        put("igen",                       "Da");
        put("nem",                        "Ne");

    }};
}
