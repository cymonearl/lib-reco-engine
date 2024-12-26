import java.io.IOException;

import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.*;
import javafx.fxml.*;

public class App extends Application{
    
    public static void main(String[] args) {
        launch(args);        
    }

    @Override
    public void start(Stage stage) throws IOException {
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("mainMenu.fxml")));
            stage.setScene(scene);
            stage.setTitle("Library");
            stage.show();
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
