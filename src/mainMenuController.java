import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class mainMenuController {
    @FXML private VBox cardLayoutVBox;
    @FXML private HBox cardLayoutHBox;
    @FXML private Button createButton;
    @FXML private Button deleteButton;
    @FXML private Button updateButton;
    @FXML private TextField searchBar;

    // HashMap to store book data (key: book ID, value: book object)
    private HashMap<String, Book> books = new HashMap<>();
    private HBox selectedBox = null;
    private BookSearch bookSearch = new BookSearch();

    /**
     * Handles the creation of a new book by opening the popup in "Add Mode".
     */
    @FXML
    void createButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BookPopup.fxml"));
            Parent popupRoot = loader.load();

            // Get reference to the BookPopupController
            BookPopupController popupController = loader.getController();
            // Set it up in "Add Mode" (null book object)
            popupController.setupPopup(null);

            // Open the popup
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

    /**
     * Handles the updating of an existing book by opening the popup in "Update Mode".
     */
    @FXML
    void updateButtonOnAction(ActionEvent event) {
        if (selectedBox != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("BookPopup.fxml"));
                Parent popupRoot = loader.load();

                // Get reference to the BookPopupController
                BookPopupController popupController = loader.getController();

                // Fetch controller of the selected box
                cardController controller = getCardController(selectedBox);
                if (controller != null) {
                    // Set up the popup with the selected book (Update Mode)
                    Book selectedBook = controller.getBook();
                    System.out.println(selectedBook);
                    popupController.setupPopup(selectedBook);
                }

                // Open the popup
                Stage popupStage = new Stage();
                popupStage.setTitle("Update Book");
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
        } else {
            System.out.println("No book selected to update.");
        }
    }

    /**
     * Refreshes the book list displayed in the UI.
     */
    private void refreshBookList() {
        cardLayoutVBox.getChildren().clear();
        populateBooks(null);; // Re-run the initialization logic
    }

    /**
     * Handles the deletion of a selected book.
     */
    @FXML
    void deleteButtonOnAction(ActionEvent event) {
        if (selectedBox != null) {
            // Fetch controller of the selected box
            cardController controller = getCardController(selectedBox);
            if (controller != null) {
                Book selectedBook = controller.getBook();
                String bookTitle = selectedBook.getTitle();
    
                // Remove the book from the HashMap
                books.remove(bookTitle);
    
                // Remove the card from the UI
                cardLayoutVBox.getChildren().remove(selectedBox);
    
                // Construct the folder path for the book images to be deleted
                String folderPath = "src/Books/" + selectedBook.getId(); // Assuming getFolderName() returns the folder name
                File folder = new File(folderPath);
    
                // Delete the folder and its contents
                if (deleteFolder(folder)) {
                    System.out.println("Deleted folder: " + folderPath);
                } else {
                    System.err.println("Failed to delete folder: " + folderPath);
                }
    
                // Clear the selection
                selectedBox = null;
            }
        } else {
            System.out.println("No book selected to delete.");
        }
    }
    
    /**
     * Deletes a folder and its contents.
     */
    private boolean deleteFolder(File folder) {
        if (folder.isDirectory()) {
            // List all files and directories in the folder
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    // Recursively delete each file or directory
                    deleteFolder(file);
                }
            }
        }
        // Finally delete the empty folder or file
        return folder.delete();
    }

    /**
     * Gets the card controller associated with an HBox.
     */
    public cardController getCardController(HBox cardBox) {
        // Get the controller associated with the HBox
        return (cardController) cardBox.getUserData();
    }

    /**
     * Initializes the main view and loads the books.
     */
    public void initialize() {
        populateBooks(null);
    }

    /**
     * Populates the book list in the UI by loading each book into a card.
     */
    private void populateBooks(List<Book> books) {
        try {
            if (books == null) {
                for (Book book : recentlyAdded()) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("bookCardVBox.fxml"));
                    HBox card = loader.load();
                    cardController controller = loader.getController();
                    controller.setData(book);
                    card.setUserData(controller); // Attach the controller to the card for later reference
                    
                    // Add click event to select the card
                    card.setOnMouseClicked(this::selectBook);
    
                    cardLayoutVBox.getChildren().add(card);
                }
            } else {
                for (Book book : books) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("bookCardVBox.fxml"));
                    HBox card = loader.load();
                    cardController controller = loader.getController();
                    controller.setData(book);
                    card.setUserData(controller); // Attach the controller to the card for later reference
    
                    // Add click event to select the card
                    card.setOnMouseClicked(this::selectBook);
    
                    cardLayoutVBox.getChildren().add(card);
                }
            }

        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    /**
     * Simulates retrieving recently added books. In a real program, this should load books from storage (e.g., files).
     */
    private ArrayList<Book> recentlyAdded() {
        ArrayList<Book> bookList = new Book().getBooks(); // Simulated method from `Book` class
        System.out.println("recentlyAdded called");
        Collections.reverse(bookList);
        return bookList;
    }

    /**
     * Logic to select and highlight a specific book card when clicked.
     */
    private void selectBook(MouseEvent event) {
        HBox clickedBox = (HBox) event.getSource();

        // Unselect previously selected box
        if (selectedBox != null) {
            selectedBox.setStyle("-fx-background-color: none;");
        }

        // Select new box
        selectedBox = clickedBox;
        selectedBox.setStyle("-fx-background-color: lightblue;");
    }

    public void searchBooks(KeyEvent event) {
        String query = searchBar.getText(); // Get the updated text in the search bar
        if (query == null || query.trim().isEmpty()) {
            cardLayoutVBox.getChildren().clear();
            populateBooks(null); // Re-run the initialization logic
            return;
        }
        
        List<Book> searchResults = bookSearch.search(query); // Perform the search
        cardLayoutVBox.getChildren().clear();
        populateBooks(searchResults); // Dynamically update the UI with search results
    }
}