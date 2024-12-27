import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.Objects;

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
    private Path imagePath;

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

    public void addImage(ActionEvent event) {
        FileChooser chooseFile = new FileChooser();
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg");
        chooseFile.getExtensionFilters().add(imageFilter);
        chooseFile.setTitle("Select Cover Image");
        File selectedImage = chooseFile.showOpenDialog(new Stage());
        if (selectedImage != null) {
            imagePath = selectedImage.toPath();
            System.out.println(imagePath);
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

        // Handle the cover image
        if (imagePath != null) {
            Path coverImagePath = bookFolderPath.resolve("cover.jpg");
            try {
                Files.copy(imagePath, coverImagePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.err.println("Error copying image: " + e.getMessage());
                // Handle error appropriately, perhaps re-throw or show an error message to the user
                throw e;
            }
        }
    }

    private void addNewBookFiles(String title, String genre, String description) throws IOException {
        Path booksFolderPath = Paths.get(BOOKS_FOLDER_PATH);
        Files.createDirectories(booksFolderPath);

        // Get the list of existing folder names
        List<Integer> existingFolderNumbers = Files.list(booksFolderPath)
                .filter(Files::isDirectory) // Only consider directories
                .map(path -> path.getFileName().toString()) // Get the folder names as strings
                .map(name -> {
                    try {
                        return Integer.parseInt(name); // Parse folder names to integers
                    } catch (NumberFormatException e) {
                        return null; // Ignore non-integer folder names
                    }
                })
                .filter(Objects::nonNull) // Remove nulls (non-integer folder names)
                .sorted() // Sort in ascending order
                .toList();

        // Determine the next available folder number
        int nextFolderNumber = 1;
        for (int folderNumber : existingFolderNumbers) {
            if (folderNumber == nextFolderNumber) {
                nextFolderNumber++; // Move to the next number in sequence
            } else {
                break; // Found a gap in the sequence
            }
        }

        // Create the folder for the new book
        String nextFolderName = String.valueOf(nextFolderNumber);
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
        if (imagePath != null) {
            try {
                Files.copy(imagePath, coverImagePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.err.println("Error copying image: " + e.getMessage());
                // Handle error appropriately
                throw e; // Or handle it in another way that's suitable for your application
            }
        } else {
            // If no image is provided, create an empty placeholder file
            Files.createFile(coverImagePath);
        }
    }
}