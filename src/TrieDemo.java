
import java.util.*;


public class TrieDemo {
    public static void main(String[] args) {
        // Sample books
        Book book1 = new Book("path/cover1.jpg", "Ascendance of a Bookworm", "Fantasy", "A young girl loves books.");
        Book book2 = new Book("path/cover2.jpg", "The Great Gatsby", "Classic", "A tale of love and book ambition.");
        Book book3 = new Book("path/cover3.jpg", "The Book Thief", "Historical Fiction", "A girl steals books in Nazi Germany.");

        // Create Tries for title, genre, and description
        BookTrie titleTrie = new BookTrie();
        BookTrie genreTrie = new BookTrie();
        BookTrie descriptionTrie = new BookTrie();

        // Insert books into the respective tries
        titleTrie.insert(book1.getTitle(), book1);
        titleTrie.insert(book2.getTitle(), book2);
        titleTrie.insert(book3.getTitle(), book3);

        genreTrie.insert(book1.getGenre(), book1);
        genreTrie.insert(book2.getGenre(), book2);
        genreTrie.insert(book3.getGenre(), book3);

        descriptionTrie.insert(book1.getDescription(), book1);
        descriptionTrie.insert(book2.getDescription(), book2);
        descriptionTrie.insert(book3.getDescription(), book3);

        // Search query
        String query = "Book";

        // Perform ranked search
        List<Book> rankedResults = rankedSearch(query, titleTrie, genreTrie, descriptionTrie);

        // Print results
        System.out.println("Search results for \"" + query + "\":");
        for (Book book : rankedResults) {
            System.out.println(book);
        }
    }

    // Perform ranked search with title priority
    public static List<Book> rankedSearch(String query, BookTrie titleTrie, BookTrie genreTrie, BookTrie descriptionTrie) {
        Set<Book> resultSet = new LinkedHashSet<>(); // Preserve order and avoid duplicates

        // Step 1: Search Titles
        List<Book> titleMatches = titleTrie.search(query);
        resultSet.addAll(titleMatches);

        // Step 2: Search Genres
        List<Book> genreMatches = genreTrie.search(query);
        for (Book book : genreMatches) {
            resultSet.add(book); // Add genre matches after title matches
        }

        // Step 3: Search Descriptions
        List<Book> descriptionMatches = descriptionTrie.search(query);
        for (Book book : descriptionMatches) {
            resultSet.add(book); // Add description matches after title and genre matches
        }

        // Return the ranked list
        return new ArrayList<>(resultSet);
    }
}