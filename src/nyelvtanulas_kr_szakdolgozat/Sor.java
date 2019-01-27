package nyelvtanulas_kr_szakdolgozat;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Sor {
    
    private final SimpleStringProperty szo;
    private final SimpleStringProperty mondat;
    private final IntegerProperty gyak = new SimpleIntegerProperty();
    
    public Sor(String szo2, String mondat2,int gyak) {
        this.szo = new SimpleStringProperty(szo2);
        this.mondat = new SimpleStringProperty(mondat2);
        this.setGyak(gyak);
    }
    
    public String getSzo() {
        return szo.get();
    }

    public void setSzo(String szo2) {
        szo.set(szo2);
    }

    public String getMondat() {
        return mondat.get();
    }

    public void setMondat(String mondat2) {
        mondat.set(mondat2);
    }
    
    public final IntegerProperty valueProperty() {
            return this.gyak;
    }
    
    public int getGyak() {
        return this.valueProperty().get();
    }
    
    public void setGyak(Integer gyak) {
        this.valueProperty().set(gyak);
    }

}
