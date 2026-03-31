package repository;

import model.Book;

import java.util.ArrayList;

public class BookRepository {
    private ArrayList<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public ArrayList<Book> getAllBooks() {
        return books;
    }

    public Book findById(String id) {
        for (Book book : books) {
            if (book.getId().equalsIgnoreCase(id)) {
                return book;
            }
        }
        return null;
    }

    public ArrayList<Book> searchByTitle(String keyword) {
        ArrayList<Book> results = new ArrayList<>();

        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(book);
            }
        }

        return results;
    }

    public boolean removeById(String id) {
        Book book = findById(id);
        if (book == null) {
            return false;
        }
        return books.remove(book);
    }
}