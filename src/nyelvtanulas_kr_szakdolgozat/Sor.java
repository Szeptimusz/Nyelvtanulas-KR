package nyelvtanulas_kr_szakdolgozat;

/**
 *
 * @author Kremmer Róbert
 */
public class Sor {

    private String szo;
    private String mondat;
    private int gyak;
    private boolean tilt = false;
    private String tabla;
    private String forditas;
    private long kikerdezes_ideje;
    
    /**
     * A Főablakban használt konstruktor.
     * Meghívásakor beállítja a szót, a mondatot,
     * és a szó gyakoriságát.
     * @param szo:    A kapott szó
     * @param mondat: A szóhoz tartozó példamondat
     * @param gyak:   A szó gyakorisága
     */
    public Sor(String szo, String mondat, int gyak) {
        this.szo = szo;
        this.mondat = mondat;
        this.gyak = gyak;
    }

    // A szavak kikérdezéséhez használt konstruktor
    public Sor(String szo, String mondat, String forditas, long kikerdezes_ideje) {
        this.szo = szo;
        this.mondat = mondat;
        this.forditas = forditas;
        this.kikerdezes_ideje = kikerdezes_ideje;
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

    public long getKikerdezes_ideje() {
        return kikerdezes_ideje;
    }

    public void setKikerdezes_ideje(long kikerdezes_ideje) {
        this.kikerdezes_ideje = kikerdezes_ideje;
    }
    
    
}
