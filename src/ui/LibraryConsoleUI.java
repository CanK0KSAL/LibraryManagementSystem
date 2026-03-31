package ui;

import model.Book;
import model.BorrowRecord;
import model.Role;
import model.User;
import service.AuthService;
import service.LibraryService;

import java.util.ArrayList;
import java.util.Scanner;

public class LibraryConsoleUI {
    private AuthService authService;
    private LibraryService libraryService;
    private Scanner scanner;

    public LibraryConsoleUI(AuthService authService, LibraryService libraryService) {
        this.authService = authService;
        this.libraryService = libraryService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\n=== LIBRARY MANAGEMENT SYSTEM ===");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    loginFlow();
                    break;
                case "2":
                    registerFlow();
                    break;
                case "3":
                    System.out.println("Goodbye.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void registerFlow() {
        System.out.println("\n=== REGISTER ===");
        System.out.print("Enter user ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        String result = authService.register(id, fullName, email, password);

        if (result.equals("SUCCESS")) {
            System.out.println("Registration successful.");
        } else {
            System.out.println(result);
        }
    }

    private void loginFlow() {
        System.out.println("\n=== LOGIN ===");
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = authService.login(email, password);

        if (user == null) {
            System.out.println("Login failed. Check your email or password.");
            return;
        }

        System.out.println("Welcome, " + user.getFullName() + ".");

        if (user.getRole() == Role.ADMIN) {
            adminMenu(user);
        } else {
            memberMenu(user);
        }
    }

    private void adminMenu(User admin) {
        while (true) {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. List Books");
            System.out.println("4. Search Book");
            System.out.println("5. List Users");
            System.out.println("6. Show Borrow Records");
            System.out.println("7. My Profile");
            System.out.println("8. Logout");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addBookFlow();
                    break;
                case "2":
                    removeBookFlow();
                    break;
                case "3":
                    listBooksFlow();
                    break;
                case "4":
                    searchBooksFlow();
                    break;
                case "5":
                    listUsersFlow();
                    break;
                case "6":
                    showBorrowRecordsFlow();
                    break;
                case "7":
                    showProfileFlow(admin);
                    break;
                case "8":
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void memberMenu(User user) {
        while (true) {
            System.out.println("\n=== MEMBER MENU ===");
            System.out.println("1. List Books");
            System.out.println("2. Search Book");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. My Active Borrowed Books");
            System.out.println("6. My Borrow History");
            System.out.println("7. My Profile");
            System.out.println("8. Logout");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    listBooksFlow();
                    break;
                case "2":
                    searchBooksFlow();
                    break;
                case "3":
                    borrowBookFlow(user);
                    break;
                case "4":
                    returnBookFlow(user);
                    break;
                case "5":
                    showActiveBorrowedBooks(user);
                    break;
                case "6":
                    showUserBorrowHistory(user);
                    break;
                case "7":
                    showProfileFlow(user);
                    break;
                case "8":
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addBookFlow() {
        System.out.println("\n=== ADD BOOK ===");
        System.out.print("Enter book ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter title: ");
        String title = scanner.nextLine();

        System.out.print("Enter author: ");
        String author = scanner.nextLine();

        System.out.print("Enter category: ");
        String category = scanner.nextLine();

        System.out.print("Enter cover path or URL: ");
        String cover = scanner.nextLine();

        String result = libraryService.addBook(id, title, author, category, cover);

        if (result.equals("SUCCESS")) {
            System.out.println("Book added successfully.");
        } else {
            System.out.println(result);
        }
    }

    private void removeBookFlow() {
        System.out.println("\n=== REMOVE BOOK ===");
        System.out.print("Enter book ID: ");
        String bookId = scanner.nextLine();

        String result = libraryService.removeBook(bookId);

        if (result.equals("SUCCESS")) {
            System.out.println("Book removed successfully.");
        } else {
            System.out.println(result);
        }
    }

    private void listBooksFlow() {
        System.out.println("\n=== BOOK LIST ===");
        ArrayList<Book> books = libraryService.getAllBooks();

        if (books.isEmpty()) {
            System.out.println("No books found.");
            return;
        }

        for (Book book : books) {
            System.out.println(book);
        }
    }

    private void searchBooksFlow() {
        System.out.println("\n=== SEARCH BOOK ===");
        System.out.print("Enter title keyword: ");
        String keyword = scanner.nextLine();

        ArrayList<Book> results = libraryService.searchBooksByTitle(keyword);

        if (results.isEmpty()) {
            System.out.println("No matching books found.");
            return;
        }

        for (Book book : results) {
            System.out.println(book);
        }
    }

    private void listUsersFlow() {
        System.out.println("\n=== USER LIST ===");
        ArrayList<User> users = libraryService.getAllUsers();

        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        for (User user : users) {
            System.out.println(user);
        }
    }

    private void borrowBookFlow(User user) {
        System.out.println("\n=== BORROW BOOK ===");
        System.out.print("Enter book ID: ");
        String bookId = scanner.nextLine();

        String result = libraryService.borrowBook(user.getId(), bookId);

        if (result.equals("SUCCESS")) {
            System.out.println("Book borrowed successfully.");
        } else {
            System.out.println(result);
        }
    }

    private void returnBookFlow(User user) {
        System.out.println("\n=== RETURN BOOK ===");
        System.out.print("Enter book ID: ");
        String bookId = scanner.nextLine();

        String result = libraryService.returnBook(user.getId(), bookId);

        if (result.equals("SUCCESS")) {
            System.out.println("Book returned successfully.");
        } else {
            System.out.println(result);
        }
    }

    private void showBorrowRecordsFlow() {
        System.out.println("\n=== ALL BORROW RECORDS ===");
        ArrayList<BorrowRecord> records = libraryService.getAllBorrowRecords();

        if (records.isEmpty()) {
            System.out.println("No borrow records found.");
            return;
        }

        for (BorrowRecord record : records) {
            System.out.println(record);
        }
    }

    private void showUserBorrowHistory(User user) {
        System.out.println("\n=== MY BORROW HISTORY ===");
        ArrayList<BorrowRecord> records = libraryService.getBorrowRecordsByUserId(user.getId());

        if (records.isEmpty()) {
            System.out.println("No borrow history found.");
            return;
        }

        for (BorrowRecord record : records) {
            System.out.println(record);
        }
    }

    private void showActiveBorrowedBooks(User user) {
        System.out.println("\n=== MY ACTIVE BORROWED BOOKS ===");
        ArrayList<BorrowRecord> records = libraryService.getActiveBorrowRecordsByUserId(user.getId());

        if (records.isEmpty()) {
            System.out.println("You have no active borrowed books.");
            return;
        }

        for (BorrowRecord record : records) {
            Book book = libraryService.findBookById(record.getBookId());
            if (book != null) {
                System.out.println(book);
                System.out.println("Borrow Date: " + record.getBorrowDate() + " | Due Date: " + record.getDueDate());
                System.out.println("--------------------------------------------------");
            }
        }
    }

    private void showProfileFlow(User user) {
        System.out.println("\n=== MY PROFILE ===");
        System.out.println("ID: " + user.getId());
        System.out.println("Name: " + user.getFullName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Role: " + user.getRole());

        ArrayList<BorrowRecord> activeRecords = libraryService.getActiveBorrowRecordsByUserId(user.getId());
        ArrayList<BorrowRecord> allRecords = libraryService.getBorrowRecordsByUserId(user.getId());

        System.out.println("Active Borrowed Books: " + activeRecords.size());
        System.out.println("Total Borrow History: " + allRecords.size());
    }
}