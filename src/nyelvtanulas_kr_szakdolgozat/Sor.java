package nyelvtanulas_kr_szakdolgozat;

/**
 * A szavak és a hozzájuk tartozó adatok könnyebb kezelését elősegítő osztály.
 * Felhasználástól függően kétféle konstruktorral rendelkezik.
 * @author Kremmer Róbert
 */
public class Sor {

    private String nevelo;
    private String szo;
    private String mondat;
    private int gyak;
    private boolean tilt = false;
    private String tabla;
    private String forditas;
  
    /**
     * A Főablakban a lista feltöltésekor használt konstruktor.
     * Meghívásakor beállítja a szót, a mondatot,
     * és a szó gyakoriságát.
     * @param szo    A szó
     * @param mondat A szóhoz tartozó példamondat
     * @param gyak   A szó gyakorisága
     */
    public Sor(String szo, String mondat, int gyak) {
        this.szo = szo;
        this.mondat = mondat;
        this.gyak = gyak;
    }

    /**
     * A szavak kikérdezésekor a lekérdezett rekordok feldolgozása során
     * használt konstruktor. Beállítja a szót, a mondatot és a hozzá tartozó 
     * fordítást.
     * @param nevelo   A szó névelője
     * @param szo      A szó
     * @param mondat   A szóhoz tartozó példamondat
     * @param forditas A szó fordítása
     */
    public Sor(String nevelo, String szo, String mondat, String forditas) {
        this.nevelo = nevelo;
        this.szo = szo;
        this.mondat = mondat;
        this.forditas = forditas;
    }
    
    public String getSzo() {
        return szo;
    }

    public void setSzo(String szo) {
        this.szo = szo;
    }

    public String getMondat() {
        return mondat;
    }

    public void setMondat(String mondat) {
        this.mondat = mondat;
    }

    public int getGyak() {
        return gyak;
    }

    public void setGyak(int gyak) {
        this.gyak = gyak;
    }

    public boolean isTilt() {
        return tilt;
    }

    public void setTilt(boolean tilt) {
        this.tilt = tilt;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getForditas() {
        return forditas;
    }

    public void setForditas(String forditas) {
        this.forditas = forditas;
    }

    public String getNevelo() {
        return nevelo;
    }

    public void setNevelo(String nevelo) {
        this.nevelo = nevelo;
    }

}
