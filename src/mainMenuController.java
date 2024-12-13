import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.*;

public class mainMenuController {
    @FXML private VBox cardLayoutVBox;
    @FXML private HBox cardLayoutHBox;
    
    public void initialize() {
        try {
            for (Book book : recentlyAdded()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("bookCardHBox.fxml"));
                HBox card = loader.load();
                cardController controller = loader.getController();
                controller.setData(book);
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
        System.out.println(books);
        return books;
    }
}