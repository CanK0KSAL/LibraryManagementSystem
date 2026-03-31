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

    public String addBook(String id, String title, String author, String category, String coverPathOrUrl) {
        if (id == null || id.trim().isEmpty()) {
            return "Book ID cannot be empty.";
        }

        if (title == null || title.trim().isEmpty()) {
            return "Title cannot be empty.";
        }

        if (author == null || author.trim().isEmpty()) {
            return "Author cannot be empty.";
        }

        if (category == null || category.trim().isEmpty()) {
            return "Category cannot be empty.";
        }

        if (bookRepository.findById(id.trim()) != null) {
            return "A book with this ID already exists.";
        }

        Book book = new Book(
                id.trim(),
                title.trim(),
                author.trim(),
                category.trim(),
                true,
                coverPathOrUrl == null ? "" : coverPathOrUrl.trim()
        );

        bookRepository.addBook(book);
        return "SUCCESS";
    }

    public String removeBook(String bookId) {
        if (bookId == null || bookId.trim().isEmpty()) {
            return "Book ID cannot be empty.";
        }

        Book book = bookRepository.findById(bookId.trim());

        if (book == null) {
            return "Book not found.";
        }

        if (!book.isAvailable()) {
            return "You cannot remove a borrowed book.";
        }

        boolean removed = bookRepository.removeById(bookId.trim());
        return removed ? "SUCCESS" : "Book could not be removed.";
    }

    public ArrayList<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    public ArrayList<Book> searchBooksByTitle(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return bookRepository.searchByTitle(keyword.trim());
    }

    public ArrayList<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public User findUserById(String userId) {
        return userRepository.findById(userId);
    }

    public Book findBookById(String bookId) {
        return bookRepository.findById(bookId);
    }

    public String borrowBook(String userId, String bookId) {
        if (userId == null || userId.trim().isEmpty()) {
            return "User ID cannot be empty.";
        }

        if (bookId == null || bookId.trim().isEmpty()) {
            return "Book ID cannot be empty.";
        }

        User user = userRepository.findById(userId.trim());
        Book book = bookRepository.findById(bookId.trim());

        if (user == null) {
            return "User not found.";
        }

        if (book == null) {
            return "Book not found.";
        }

        if (!book.isAvailable()) {
            return "This book is currently not available.";
        }

        String recordId = "BR" + (borrowRecordRepository.getAllRecords().size() + 1);
        LocalDate borrowDate = LocalDate.now();
        LocalDate dueDate = borrowDate.plusDays(14);

        BorrowRecord record = new BorrowRecord(recordId, userId.trim(), bookId.trim(), borrowDate, dueDate, null);
        borrowRecordRepository.addRecord(record);
        book.setAvailable(false);

        return "SUCCESS";
    }

    public String returnBook(String userId, String bookId) {
        if (userId == null || userId.trim().isEmpty()) {
            return "User ID cannot be empty.";
        }

        if (bookId == null || bookId.trim().isEmpty()) {
            return "Book ID cannot be empty.";
        }

        Book book = bookRepository.findById(bookId.trim());

        if (book == null) {
            return "Book not found.";
        }

        BorrowRecord activeRecord = borrowRecordRepository.findActiveByBookIdAndUserId(bookId.trim(), userId.trim());

        if (activeRecord == null) {
            return "This user does not have this book.";
        }

        activeRecord.setReturnDate(LocalDate.now());
        book.setAvailable(true);
        return "SUCCESS";
    }

    public ArrayList<BorrowRecord> getAllBorrowRecords() {
        return borrowRecordRepository.getAllRecords();
    }

    public ArrayList<BorrowRecord> getBorrowRecordsByUserId(String userId) {
        return borrowRecordRepository.findByUserId(userId);
    }

    public ArrayList<BorrowRecord> getActiveBorrowRecordsByUserId(String userId) {
        return borrowRecordRepository.findActiveByUserId(userId);
    }
}