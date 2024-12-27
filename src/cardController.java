import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import javafx.application.Platform;
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
        updateBookImage();
        updateBookInfo();
    }

    public void updateBookImage() {
        try {
            File imageFile = new File("/src/" + book.getImagePath());
            
            if (imageFile.exists()) {
                Image image = new Image(imageFile.toURI().toString());
                bookImage.setImage(image);
            } else {
                // Fallback to resource stream if file doesn't exist
                InputStream is = getClass().getResourceAsStream("/" + book.getImagePath());
                if (is != null) {
                    Image image = new Image(is);
                    bookImage.setImage(image);
                    is.close();
                } else {
                    System.err.println("Image not found: " + book.getImagePath());
                    // Set a default image
                    setDefaultImage();
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + book.getImagePath());
            e.printStackTrace();
            setDefaultImage();
        }
    }

    private void setDefaultImage() {
        try {
            InputStream defaultIs = getClass().getResourceAsStream("/images/default-book.jpg");
            if (defaultIs != null) {
                Image defaultImage = new Image(defaultIs);
                bookImage.setImage(defaultImage);
                defaultIs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateBookInfo() {
        bookTitle.setText(book.getTitle());
        bookGenre.setText(book.getGenre());
        if (bookDescription != null) {
            bookDescription.setText(book.getDescription());
        }
    }

    // Method to manually refresh the image
    public void refreshImage() {
        // Clear the ImageView cache
        if (bookImage.getImage() != null) {
            bookImage.getImage().cancel();
        }
        updateBookImage();
    }

    // Alternative method using file watching
    public void setupImageWatcher() {
        try {
            Path imagePath = Paths.get(book.getImagePath());
            WatchService watchService = imagePath.getParent().getFileSystem().newWatchService();
            imagePath.getParent().register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

            // Start a background thread to watch for changes
            Thread watchThread = new Thread(() -> {
                try {
                    while (true) {
                        WatchKey key = watchService.take();
                        for (WatchEvent<?> event : key.pollEvents()) {
                            if (event.context().toString().equals(imagePath.getFileName().toString())) {
                                // Update the image on JavaFX thread
                                Platform.runLater(this::refreshImage);
                            }
                        }
                        key.reset();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            watchThread.setDaemon(true);
            watchThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HBox getBox() {
        return box;
    }

    public Book getBook() {
        return book;
    }
}