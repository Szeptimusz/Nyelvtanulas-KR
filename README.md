# Nyelvtanulas-KR
Készítette: Kremmer Róbert

Program működésének rövid leírása:
------------------------------
Nyelvtanulási céllal egy adott szöveget feldolgoz, megjenelíti a szöveget felépítő szavakat példamondatokkal együtt, és az ismeretlen
(tanulandó) szavakból .txt állományt készít, amit az ANKI szókártya program be tud importálni és szókártyákat készíteni belőle. Vagy lehetőség van a helyi kikérdező rendszer használatára is.

Működés részletesebb leírása:
-------------------------
A feldolgozandó szöveg megadható tallózással, vagy egyszerű bemásolással.

Ki lehet választani, hogy az eredmények megjelenítésénél azokat a szavakat, amik csak egyszer fordulnak elő ne jelenítse meg a táblázatban.

A feldolgozás előtt meg kell adni, hogy milyen nyelvű a szöveg, mert a különböző nyelveket különböző táblákba menti el.

A feldolgozás után táblázatban jeleníti meg a szöveget alkotó egyedi szavakat, az azokhoz tartozó példamondatot, és azt, hogy a szó hányszor fordult elő a teljes szövegben.

A táblázatbeli szavak elmenthetők 3 kategóriába: ismert szó, tanulandó szó, figyelmen kívül hagyott szó (ignorált). Ha később másik
szöveggel újra futtatjuk a feldolgozást, akkor a korábban elmentett szavak már nem fognak szerepelni a táblázatban. Így a szövegek feldolgozásával és a szavak elmentésével saját nyelvi adatbázis építhető.

A tanulandó szavak elmentésénél tárolja az adott szót, a hozzá tartozó példamondatot, és a szó általunk megadott fordítását. A fordításhoz segítséget nyújt a gombbal megnyitható Google Translate (az adott nyelvről fordítja le a szót magyarra) és az angol nyelvnél elérhető Cambridge Dictionary.

Az opcióknál lehet ANKI importot készíteni. A nyelv kiválasztásával az adott nyelvhez tartozó tanulandó szavakból anki-import fájlt készít. A korábban elkészített importot nem írja felül, hanem az új szavakat hozzáadja az import-fájl végéhez. Az elkészített import a szón kívül tartalmazza a példamondatot, a szó fordítását, és a lyukas példamondatot (a szó helye ki van pontozva).

Az opcióknál elérhető a helyi kikérdező felület is. Itt a nyelv kiválasztása után megjeleníti az aktuálisan 
kikérdezendő tanulandó szavakat a példamondatukkal együtt. A gyakorlás során egy válasz gombbal megjeleníthető
a szó fordítása és az alsó gombokkal értékelni lehet a szó nehézségét. A választól függően kérdezi ki újra a szót
bizonyos idő után.

A Statisztika menüpontnál meg lehet nézni az adott nyelvre vonatkozóan az adatbázisban tárolt különböző állapotú (ismert,figyelmen kívül hagyott, tanulandó) szavak számát.

Részletesebb leírás a mellékelt Felhasználói dokumentációban olvasható