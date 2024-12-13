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

    public void setData(Book book) {
        Image image = new Image(getClass().getResourceAsStream(book.getImagePath()));
        bookImage.setImage(image);

        bookTitle.setText(book.getTitle());
        bookGenre.setText(book.getGenre());
        if (bookDescription != null)
        bookDescription.setText(book.getDescription());
    }
}


