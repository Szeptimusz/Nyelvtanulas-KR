package nyelvtanulas_kr_szakdolgozat;

public class Sor {

    private String szo;
    private String mondat;
    private int gyak;
    private boolean tilt;
    
    public Sor(String szo, String mondat, int gyak, boolean tilt) {
        this.szo = szo;
        this.mondat = mondat;
        this.gyak = gyak;
        this.tilt = tilt;
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
}
