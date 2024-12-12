import java.io.IOException;

import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.*;

public class App extends Application{
    
    public static void main(String[] args) {
        launch(args);        
    }

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(new Group());
        stage.setScene(scene);
        stage.setTitle("Lib Reco Engine");
        stage.setWidth(600);
        stage.setHeight(800);
        stage.setResizable(false);
        stage.show();
    }
}
