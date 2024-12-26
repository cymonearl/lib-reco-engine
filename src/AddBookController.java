import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.*;
import java.util.UUID;

public class AddBookController {
    @FXML
    private TextField bookNameTextField;

    @FXML
    private TextField bookGenreTextField;

    @FXML
    private TextArea bookDescriptionTextField;

    private final String BOOKS_FOLDER_PATH = "src/Books"; // Main folder for book subfolders

    @FXML
    void addBookButtonOnAction() {
        String title = bookNameTextField.getText().trim();
        String genre = bookGenreTextField.getText().trim();
        String description = bookDescriptionTextField.getText().trim();

        if (title.isEmpty() || genre.isEmpty() || description.isEmpty()) {
            System.out.println("Please fill out all fields!");
            return;
        }

        try {
            // Create the main books folder if it doesn't exist
            Files.createDirectories(Paths.get(BOOKS_FOLDER_PATH));

            // Generate a unique subfolder for the new book
            String uniqueFolderName = UUID.randomUUID().toString(); // Use UUID for unique folder names
            Path bookFolderPath = Paths.get(BOOKS_FOLDER_PATH, uniqueFolderName);

            // Create the subfolder for this book
            Files.createDirectories(bookFolderPath);

            // Create and write the book's CSV file
            Path csvFilePath = bookFolderPath.resolve("book.csv");
            try (BufferedWriter writer = Files.newBufferedWriter(csvFilePath)) {
                writer.write(title); // First line: title
                writer.newLine();
                writer.write(genre); // Second line: genre
                writer.newLine();
                writer.write(description); // Third line onwards: description
                writer.newLine();
            }

            // Create a placeholder for the cover image
            Path coverImagePath = bookFolderPath.resolve("cover.jpg");
            Files.createFile(coverImagePath); // This creates an empty file; you can later replace it with an actual image

            System.out.println("Book saved successfully in folder: " + uniqueFolderName);

            // Close the popup
            Stage stage = (Stage) bookNameTextField.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
