# Nyelvtanulas-KR
Szakdolgozat - Kremmer Róbert

Program működésének rövid leírása:
------------------------------
Nyelvtanulási céllal egy adott szöveget feldolgoz, megjenelíti a szöveget felépítő szavakat példamondatokkal együtt, és az ismeretlen
(tanulandó) szavakból .txt állományt készít, amit az ANKI szókártya program be tud importálni és szókártyákat készíteni belőle. 

Működés részletesebb leírása:
-------------------------
A feldolgozandó szöveg megadható tallózással, vagy egyszerű bemásolással.

Ki lehet választani, hogy az eredmények megjelenítésénél azokat a szavakat, amik csak egyszer fordulnak elő globálisan (a szövegben egyszer fordul elő és az adatbázisban nincs bent mint görgetett szó), azokat mentse el az adatbázisba mint görgetett szó. Ha legközelebb ez a szó előfordul egy szövegben, akkor már globálisan 2-szer fordul elő, így meg fog jelenni a táblázatban. Vagyis az egyszer előforduló szavakat addig görgeti tovább amíg nem adunk meg neki olyan szöveget amiben újra szerepel.

A feldolgozás előtt meg kell adni, hogy milyen nyelvű a szöveg, mert a különböző nyelveket különböző táblákba menti el.

A feldolgozás után táblázatban jeleníti meg a szöveget alkotó egyedi szavakat, az azokhoz tartozó példamondatot, és azt, hogy a szó hányszor fordult elő a teljes szövegben.

A táblázatbeli szavak elmenthetők 3 kategóriába: ismert szó, tanulandó szó, figyelmen kívül hagyott szó (ignorált). Ha később másik
szöveggel újra futtatjuk a feldolgozást, akkor a korábban elmentett szavak már nem fognak szerepelni a táblázatban. Így a szövegek feldolgozásával és a szavak elmentésével saját nyelvi adatbázis építhető.

A tanulandó szavak elmentésénél tárolja az adott szót, a hozzá tartozó példamondatot, és a szó általunk megadott fordítását. A fordításhoz segítséget nyújt a gombbal megnyitható Google Translate (az adott nyelvről fordítja le a szót magyarra).

Az opcióknál lehet ANKI importot készíteni. A nyelv kiválasztásával az adott nyelvhez tartozó tanulandó szavakból anki-import fájlt készít. A korábban elkészített importot nem írja felül, hanem az új szavakat hozzáadja az import-fájl végéhez. Az elkészített import a szón kívül tartalmazza a példamondatot, a szó fordítását, és a lyukas példamondatot (a szó helye ki van pontozva).
