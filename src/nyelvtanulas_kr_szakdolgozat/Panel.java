package nyelvtanulas_kr_szakdolgozat;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import static nyelvtanulas_kr_szakdolgozat.FoablakController.uzenetek;

public class Panel {
    public static void tajekoztat(String cim, String uzenet) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, uzenet, new ButtonType[0]);
        alert.setTitle(cim);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public static void figyelmeztet(String cim, String uzenet) {
        Alert alert = new Alert(Alert.AlertType.WARNING, uzenet, new ButtonType[0]);
        alert.setTitle(cim);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public static void hiba(String cim, String uzenet) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(cim);
        alert.setHeaderText(null);
        alert.setContentText(uzenet);
        alert.showAndWait();
    }

    public static boolean igennem(String cim, String uzenet) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(cim);
        alert.setHeaderText(null);
        alert.setContentText(uzenet);
        ButtonType btIgen = new ButtonType(uzenetek.get("igen"));
        ButtonType btNem = new ButtonType(uzenetek.get("nem"));
        alert.getButtonTypes().setAll(btIgen, btNem);
        Optional result = alert.showAndWait();
        return result.get() == btIgen;
    }
}

