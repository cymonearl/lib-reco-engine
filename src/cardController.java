import java.io.InputStream;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;

public class cardController {
    @FXML private HBox box;
    @FXML private ImageView bookImage;
    @FXML private Label bookTitle;
    @FXML private Label bookGenre;
    @FXML private Label bookDescription;
    private Book book;

    public void setData(Book book) {
        this.book = book;


        try {
            // Load the image using getResourceAsStream
            InputStream is = getClass().getResourceAsStream(book.getImagePath());
            if (is != null) {
                Image image = new Image(is);
                bookImage.setImage(image);
            } else {
                System.err.println("Image not found: " + book.getImagePath());
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + book.getImagePath());
            e.printStackTrace();
        }

        bookTitle.setText(book.getTitle());
        bookGenre.setText(book.getGenre());
        if (bookDescription != null)
        bookDescription.setText(book.getDescription());
    }

    public HBox getBox() {
        return box;
    }

    public Book getBook() {
        return book;
    }
}