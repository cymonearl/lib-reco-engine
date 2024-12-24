

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class TrieNode {
    HashMap<Character, TrieNode> children; // Stores child nodes mapped by characters
    boolean isEndOfWord; // Marks the end of a word
    List<Book> books; // List of books with titles that end at this node

    // Constructor
    public TrieNode() {
        children = new HashMap<>();
        isEndOfWord = false;
        books = new ArrayList<>();
    }
}

class BookTrie {

    private TrieNode root;

    public BookTrie() {
        root = new TrieNode();
    }

    // Insert a string (title, genre, or description) into the Trie with its associated book
    public void insert(String key, Book book) {
        TrieNode node = root;

        // Insert all substrings (use for substring matching)
        key = key.toLowerCase(); // Case-insensitive
        for (int i = 0; i < key.length(); i++) {
            TrieNode currentNode = node;
            for (int j = i; j < key.length(); j++) {
                char c = key.charAt(j);
                currentNode.children.putIfAbsent(c, new TrieNode());
                currentNode = currentNode.children.get(c);
            }
            currentNode.isEndOfWord = true;
            if (!currentNode.books.contains(book)) {
                currentNode.books.add(book);
            }
        }
    }

    // Search for books containing a specific substring and return matches
    public List<Book> search(String query) {
        TrieNode node = root;

        query = query.toLowerCase();
        for (char c : query.toCharArray()) {
            if (!node.children.containsKey(c)) {
                return new ArrayList<>(); // No matches
            }
            node = node.children.get(c);
        }

        // Collect all matching books from this node
        return collectBooks(node);
    }

    // Helper: Collect all books from a TrieNode and its descendants
    private List<Book> collectBooks(TrieNode node) {
        List<Book> books = new ArrayList<>();

        if (node.isEndOfWord) {
            books.addAll(node.books);
        }

        for (TrieNode child : node.children.values()) {
            books.addAll(collectBooks(child));
        }

        return books;
    }
}