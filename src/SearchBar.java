import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SearchBar extends Application {

    private Label resultsLabel;

    @Override
    public void start(Stage primaryStage) {
        // 1. Create the Search TextField
        TextField searchField = new TextField();
        searchField.setPromptText("Enter search term...");
    

        // 2. Create the Results Label
         resultsLabel= new Label("Results will appear here.");

        // 3. Set up Event Handler (optional)
       searchField.setOnKeyReleased(event -> {
           handleSearch(searchField.getText());
           
         });
        
        // 4. Layout the Search Bar with other Components
        HBox layout = new HBox(10); // 10 pixels spacing
        layout.setAlignment(Pos.CENTER); // Put things in center
        layout.setPadding(new Insets(15)); // Padding around elements
        layout.getChildren().addAll(searchField,resultsLabel);

        Scene scene = new Scene(layout, 400, 100); // Adjust size if needed

        primaryStage.setTitle("Search Bar Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

        
    private void handleSearch(String seachTerm) {
        if(seachTerm.isBlank()) {
            resultsLabel.setText("Results will appear here.");

        }else {
             // Replace this with your actual search logic
            String results = performSearch(seachTerm); 
            resultsLabel.setText("Results: " + results);
        }
      
    }
   

    private String performSearch(String searchTerm) {
        // Replace this with your search logic (e.g., filtering a list, DB lookups)
        // For this example return the last word or char entered
        if(searchTerm.split(" ").length>1) {
            return searchTerm.split(" ")[searchTerm.split(" ").length-1];
        }
        return searchTerm;
   }
    

    public static void main(String[] args) {
        launch(args);
    }
}