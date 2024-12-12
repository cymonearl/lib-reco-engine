import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.*;

public class mainMenuController {
    @FXML private VBox cardLayout;
    
    public void initialize() {
        try {
            for (Book book : recentlyAdded()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("bookCard.fxml"));
                HBox card = loader.load();
                cardController controller = loader.getController();
                controller.setData(book);
                cardLayout.getChildren().add(card);
                System.out.println(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private List<Book> recentlyAdded() {
        ArrayList<Book> books = new ArrayList<Book>();
        Book book = new Book();
        book.setTitle("Ascendance of a Bookworm");
        book.setGenre("Comedy, Drama, Fantasy, Shoujo, Slice of Life.");
        book.setDescription("What");
        book.setImage("Books/1/ascendance-of-a-bookworm-part-5-volume-12.jpg");
        books.add(book);

        return books;
    }
}