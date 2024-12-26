import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class Book {
    String imagePath;  // Absolute path to the cover image
    String id;     // Book id
    String title;      // Book title
    String genre;      // Book genre
    String description; // Book description

    public Book() {}

    public Book(String imagePath, String title, String genre, String description) {
        this.imagePath = imagePath;
        this.title = title;
        this.genre = genre;
        this.description = description;
    }

    public String getImagePath() { return imagePath; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public String getDescription() { return description; }
    public String getId() { return id; }

    public void setId(String id) { this.id = id; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public void setTitle(String title) { this.title = title; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                "imagePath='" + imagePath + '\'' +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public ArrayList<Book> getBooks() {
        ArrayList<Book> books = new ArrayList<>();
        File booksFolder = new File("src/Books"); // Main folder containing book subfolders
    
        if (!booksFolder.exists() || !booksFolder.isDirectory()) {
            System.out.println("Books folder not found or is not a directory.");
            return books;
        }
    
        // Loop through all subfolders in the Books directory
        for (File subfolder : booksFolder.listFiles()) {
            if (subfolder.isDirectory() && subfolder.getName().matches("\\d+")) {
                // Look for a CSV file and cover.jpg in the subfolder
                File[] csvFiles = subfolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));
                File coverImage = new File(subfolder, "cover.jpg"); // Assume the cover image is named "cover.jpg"
                Path coverImagePath = coverImage.toPath();
    
                if (csvFiles == null || csvFiles.length == 0) {
                    System.out.println("No CSV file found in folder: " + subfolder.getName());
                    continue; // Skip if no CSV file found
                }
    
                if (!coverImage.exists()) {
                    System.out.println("Cover image not found in folder: " + subfolder.getName());
                    continue; // Skip if no cover image found
                }
    
                // Read the first CSV file in the subfolder
                File csvFile = csvFiles[0];
                try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                    String line;
                    String title = null, genre = null, description = "";
                    int lineCount = 0;
    
                    while ((line = br.readLine()) != null) {
                        line = line.trim();
                        lineCount++;
    
                        if (lineCount == 1) {
                            // First line is the title
                            title = line;
                        } else if (lineCount == 2) {
                            // Second line is the genre
                            genre = line;
                        } else {
                            // Remaining lines are part of the description
                            if (!description.isEmpty()) {
                                description += " "; // Add a space between lines
                            }
                            description += line;
                        }
                    }
    
                    // Ensure all required fields are present
                    if (title != null && genre != null && !description.isEmpty()) {
                        // Create a new Book object and set its id as the folder name (subfolder name)
                        Book book = new Book(coverImagePath.subpath(1, coverImagePath.getNameCount()).toString(), title, genre, description.trim());
                        book.setId(subfolder.getName()); // Set the id as the folder name
                        books.add(book);
                    } else {
                        System.out.println("Incomplete book data in folder: " + subfolder.getName());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    
        return books;
    }
}