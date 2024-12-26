import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.*;

public class BookPopupController {

    @FXML
    private TextField bookNameTextField;

    @FXML
    private TextField bookGenreTextField;

    @FXML
    private TextArea bookDescriptionTextField;

    @FXML
    private Button actionButton; // The button used for both adding and updating

    private String BOOKS_FOLDER_PATH = "src/Books"; // Main folder for book subfolders
    private Book currentBook = null; // This will be `null` for adding, set to existing book for updating 

    // Flag to indicate whether the popup is for "Add" or "Update"
    private boolean isUpdate = false;

    /**
     * Sets up the popup for either "Add" or "Update" functionality.
     *
     * @param book The book to update, or null for adding a new book.
     */
    public void setupPopup(Book book) {
        if (book != null) {
            // Update mode
            isUpdate = true;
            currentBook = book;

            // Populate fields with existing book data
            bookNameTextField.setText(book.getTitle());
            bookGenreTextField.setText(book.getGenre());
            bookDescriptionTextField.setText(book.getDescription());

            // Set button text to "UPDATE BOOK"
            actionButton.setText("UPDATE BOOK");
        } else {
            // Add mode
            isUpdate = false;
            actionButton.setText("ADD BOOK");
        }
    }

    @FXML
    void handleActionButton() {
        String title = bookNameTextField.getText().trim();
        String genre = bookGenreTextField.getText().trim();
        String description = bookDescriptionTextField.getText().trim();

        if (title.isEmpty() || genre.isEmpty() || description.isEmpty()) {
            System.out.println("Please fill out all fields!");
            return;
        }

        try {
            if (isUpdate) {
                // Update an existing book
                updateBookFiles(title, genre, description);
                System.out.println("Book updated successfully!");
            } else {
                // Add a new book
                addNewBookFiles(title, genre, description);
                System.out.println("Book added successfully!");
            }

            // Close the popup after the operation
            Stage stage = (Stage) actionButton.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateBookFiles(String title, String genre, String description) throws IOException {
        if (currentBook == null) {
            throw new IllegalStateException("No book selected for update");
        }

        // Find the book folder by its ID
        Path bookFolderPath = Paths.get(BOOKS_FOLDER_PATH, currentBook.getId());
        Path csvFilePath = bookFolderPath.resolve("book.csv");

        // Write updated contents to the CSV
        try (BufferedWriter writer = Files.newBufferedWriter(csvFilePath)) {
            writer.write(title); // First line: title
            writer.newLine();
            writer.write(genre); // Second line: genre
            writer.newLine();
            writer.write(description); // Third line: description
            writer.newLine();
        }

        // Update the currentBook object
        currentBook.setTitle(title);
        currentBook.setGenre(genre);
        currentBook.setDescription(description);
    }

    private void addNewBookFiles(String title, String genre, String description) throws IOException {
        Path booksFolderPath = Paths.get(BOOKS_FOLDER_PATH);
        Files.createDirectories(booksFolderPath);

        // Determine the next folder name
        String nextFolderName = String.valueOf(Files.list(booksFolderPath).count() + 1);

        Path bookFolderPath = booksFolderPath.resolve(nextFolderName);
        Files.createDirectories(bookFolderPath);

        // Create and write the book's CSV file
        Path csvFilePath = bookFolderPath.resolve("book.csv");
        try (BufferedWriter writer = Files.newBufferedWriter(csvFilePath)) {
            writer.write(title); // First line: title
            writer.newLine();
            writer.write(genre); // Second line: genre
            writer.newLine();
            writer.write(description); // Third line: description
            writer.newLine();
        }

        // Create a placeholder for the cover image
        Path coverImagePath = bookFolderPath.resolve("cover.jpg");
        Files.createFile(coverImagePath);
    }
}