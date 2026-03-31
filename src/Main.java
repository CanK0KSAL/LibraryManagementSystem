import model.Role;
import model.User;
import repository.BookRepository;
import repository.BorrowRecordRepository;
import repository.UserRepository;
import service.AuthService;
import service.LibraryService;
import ui.LibraryConsoleUI;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        BookRepository bookRepository = new BookRepository();
        BorrowRecordRepository borrowRecordRepository = new BorrowRecordRepository();

        userRepository.addUser(new User("A1", "Admin User", "admin@library.com", "1234", Role.ADMIN));
        userRepository.addUser(new User("U1", "Can Bey", "can@example.com", "1111", Role.MEMBER));

        bookRepository.addBook(new model.Book("B1", "1984", "George Orwell", "Dystopian", true, "covers/1984.jpg"));
        bookRepository.addBook(new model.Book("B2", "Crime and Punishment", "Fyodor Dostoevsky", "Classic", true, "covers/crime.jpg"));
        bookRepository.addBook(new model.Book("B3", "The Trial", "Franz Kafka", "Novel", true, "covers/trial.jpg"));

        AuthService authService = new AuthService(userRepository);
        LibraryService libraryService = new LibraryService(bookRepository, userRepository, borrowRecordRepository);

        LibraryConsoleUI ui = new LibraryConsoleUI(authService, libraryService);
        ui.start();
    }
}