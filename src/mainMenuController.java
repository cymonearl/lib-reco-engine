import javafx.fxml.*;
import javafx.scene.layout.*;

import java.util.*;

public class mainMenuController {
    @FXML private VBox cardLayoutVBox;
    @FXML private HBox cardLayoutHBox;
    
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
        System.out.println(books);
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