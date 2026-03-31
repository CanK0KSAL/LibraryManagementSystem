package service;

import model.Book;
import model.BorrowRecord;
import model.User;
import repository.BookRepository;
import repository.BorrowRecordRepository;
import repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;

public class LibraryService {
    private BookRepository bookRepository;
    private UserRepository userRepository;
    private BorrowRecordRepository borrowRecordRepository;

    public LibraryService(BookRepository bookRepository, UserRepository userRepository, BorrowRecordRepository borrowRecordRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.borrowRecordRepository = borrowRecordRepository;
    }

    public boolean addBook(String id, String title, String author, String category, String coverPathOrUrl) {
        if (bookRepository.findById(id) != null) {
            return false;
        }

        Book book = new Book(id, title, author, category, true, coverPathOrUrl);
        bookRepository.addBook(book);
        return true;
    }

    public ArrayList<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    public ArrayList<Book> searchBooksByTitle(String keyword) {
        return bookRepository.searchByTitle(keyword);
    }

    public ArrayList<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public boolean borrowBook(String userId, String bookId) {
        User user = userRepository.findById(userId);
        Book book = bookRepository.findById(bookId);

        if (user == null || book == null) {
            return false;
        }

        if (!book.isAvailable()) {
            return false;
        }

        String recordId = "BR" + (borrowRecordRepository.getAllRecords().size() + 1);
        LocalDate borrowDate = LocalDate.now();
        LocalDate dueDate = borrowDate.plusDays(14);

        BorrowRecord record = new BorrowRecord(recordId, userId, bookId, borrowDate, dueDate, null);
        borrowRecordRepository.addRecord(record);
        book.setAvailable(false);

        return true;
    }

    public boolean returnBook(String bookId) {
        Book book = bookRepository.findById(bookId);
        BorrowRecord activeRecord = borrowRecordRepository.findActiveByBookId(bookId);

        if (book == null || activeRecord == null) {
            return false;
        }

        activeRecord.setReturnDate(LocalDate.now());
        book.setAvailable(true);
        return true;
    }

    public ArrayList<BorrowRecord> getAllBorrowRecords() {
        return borrowRecordRepository.getAllRecords();
    }

    public ArrayList<BorrowRecord> getBorrowRecordsByUserId(String userId) {
        return borrowRecordRepository.findByUserId(userId);
    }
}