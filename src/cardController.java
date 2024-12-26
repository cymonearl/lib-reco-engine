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

        Image image = new Image(getClass().getResourceAsStream(book.getImagePath()));
        bookImage.setImage(image);

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