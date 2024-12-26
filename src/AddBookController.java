import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.*;

public class AddBookController {
    @FXML private TextField titleField;
    @FXML private TextField genreField;
    @FXML private TextArea descriptionArea;

    private final String CSV_FOLDER_PATH = "data/books"; // Subfolder for CSV files

    @FXML
    void saveBookOnAction() {
        String title = titleField.getText().trim();
        String genre = genreField.getText().trim();
        String description = descriptionArea.getText().trim();

        if (title.isEmpty() || genre.isEmpty() || description.isEmpty()) {
            System.out.println("Please fill out all fields!");
            return;
        }

        try {
            // Ensure subfolders exist
            Files.createDirectories(Paths.get(CSV_FOLDER_PATH));

            // Save to CSV
            String csvFilePath = CSV_FOLDER_PATH + "/books.csv";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath, true))) {
                writer.write(String.join(",", title, genre, description));
                writer.newLine();
            }

            System.out.println("Book saved successfully!");

            // Close the popup
            Stage stage = (Stage) titleField.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
