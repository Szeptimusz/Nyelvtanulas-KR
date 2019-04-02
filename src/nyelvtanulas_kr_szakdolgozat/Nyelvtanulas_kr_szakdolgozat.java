package nyelvtanulas_kr_szakdolgozat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * A program elindulását és a főablak megjelenítését biztosító osztály.
 * @author Kremmer Róbert
 */
public class Nyelvtanulas_kr_szakdolgozat extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Foablak.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle("Nyelvtanulás program");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
