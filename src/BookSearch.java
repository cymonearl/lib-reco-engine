import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BookSearch {
    private BookTrie titleTrie;
    private BookTrie genreTrie;
    private BookTrie descriptionTrie;
    private ArrayList<Book> allBooks;

    public BookSearch() {
        titleTrie = new BookTrie();
        genreTrie = new BookTrie();
        descriptionTrie = new BookTrie();
        
        loadBooks();
    }

    private void loadBooks() {
        Book bookLoader = new Book();
        allBooks = bookLoader.getBooks();

        for (Book book : allBooks) {
            titleTrie.insert(book.getTitle(), book);
            genreTrie.insert(book.getGenre(), book);
            descriptionTrie.insert(book.getDescription(), book);
        }
    }

    public List<Book> search(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>(allBooks); 
        }

        return performRankedSearch(query.trim());
    }

    private List<Book> performRankedSearch(String query) {
        Map<Book, Integer> scoreMap = new HashMap<>();
    
        Map<Book, Integer> tempMap = new HashMap<>();
        updateScores(tempMap, titleTrie.search(query), 3);
        updateScores(tempMap, genreTrie.search(query), 2);
        updateScores(tempMap, descriptionTrie.search(query), 1);
    
        return tempMap.entrySet().stream()
                .sorted(Map.Entry.<Book, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .toList();
    }
    
    private void updateScores(Map<Book, Integer> map, List<Book> matches, int weight) {
        for (Book book : matches) {
            map.put(book, map.getOrDefault(book, 0) + weight);
        }
    }
    

    // Optional lng- get formatted results for display- 
    public String getFormattedResults(List<Book> books) {
        if (books.isEmpty()) {
            return "No results found";
        }

        StringBuilder sb = new StringBuilder();
        for (Book book : books) {
            sb.append(String.format("%s (%s)\n", book.getTitle(), book.getGenre()));
        }
        return sb.toString();
    }
}