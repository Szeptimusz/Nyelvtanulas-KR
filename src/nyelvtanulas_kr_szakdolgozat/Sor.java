package nyelvtanulas_kr_szakdolgozat;

public class Sor {

    private String szo;
    private String mondat;
    private int gyak;
    
    public Sor(String szo, String mondat, int gyak) {
        this.szo = szo;
        this.mondat = mondat;
        this.gyak = gyak;
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
    
}
