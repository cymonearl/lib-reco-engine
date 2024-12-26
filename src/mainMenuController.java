import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class mainMenuController {
    @FXML private VBox cardLayoutVBox;
    @FXML private HBox cardLayoutHBox;
    @FXML private Button createButton;
    @FXML private Button deleteButton;
    @FXML private Button updateButton;
    private Parent root;
    private Scene scene;
    private Stage stage;

    // HashMap to store book data (key: book ID, value: book name)
    private HashMap<String, String> books = new HashMap<>();

    @FXML
    void createButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addBook.fxml"));
            Parent popupRoot = loader.load();
            Stage popupStage = new Stage();
            popupStage.setTitle("Add Book");
            Scene popupScene = new Scene(popupRoot);
            popupStage.setScene(popupScene);
            popupStage.initOwner(((Node) event.getSource()).getScene().getWindow());
            popupStage.initModality(javafx.stage.Modality.APPLICATION_MODAL);

            // Wait for the popup to close
            popupStage.showAndWait();

            // Refresh the book list after popup closes
            refreshBookList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refreshBookList() {
        cardLayoutVBox.getChildren().clear();
        cardLayoutHBox.getChildren().clear();
        initialize(); // Re-run the initialization logic
    }


    @FXML
    void deleteButtonOnAction(ActionEvent event) {

    }

    @FXML
    void updateButtonOnAction(ActionEvent event) {

    }


    public void initialize() {
        try {
            ArrayList<Book> books = recentlyAdded();
            final int bookCount = 1;
            ArrayList<Integer> randomNumbers = getRandomBook(bookCount);

            for (int i = 0; i < bookCount; i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("bookCardHBox.fxml"));
                HBox card = loader.load();
                cardController controller = loader.getController();
                controller.setData(books.get(randomNumbers.get(i)));
                cardLayoutHBox.getChildren().add(card);
            }
            
            for (Book book : recentlyAdded()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("bookCardVBox.fxml"));
                HBox card = loader.load();
                cardController controller = loader.getController();
                controller.setData(book);
                cardLayoutVBox.getChildren().add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private ArrayList<Book> recentlyAdded() {
        ArrayList<Book> books;
        books = new Book().getBooks();
        Collections.reverse(books);
        return books;
    }
    
    private ArrayList<Integer> getRandomBook(int count) {
        ArrayList<Book> books = recentlyAdded();
        count = Math.min(count, books.size());
        
        Set<Integer> uniqueNumbers = new HashSet<>();
        Random random = new Random();
        
        while (uniqueNumbers.size() < count) {
            uniqueNumbers.add(random.nextInt(books.size()));
        }
        
        return new ArrayList<>(uniqueNumbers);
    }
}