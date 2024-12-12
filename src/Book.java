public class Book {
    String image;
    String title;
    String genre;
    String description;

    public Book() {}

    public Book(String book_id, String title, String genre, String description) {
        this.image = book_id;
        this.title = title;
        this.genre = genre;
        this.description = description;
    }

    public String getImage() { return image; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public String getDescription() { return description; }

    public void setImage(String book_id) { this.image = book_id; }
    public void setTitle(String title) { this.title = title; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "Book{" +
                "book_id='" + image + '\'' +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}