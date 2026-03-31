package service;

import model.Role;
import model.User;
import repository.UserRepository;

public class AuthService {
    private UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String register(String id, String fullName, String email, String password) {
        if (id == null || id.trim().isEmpty()) {
            return "User ID cannot be empty.";
        }

        if (fullName == null || fullName.trim().isEmpty()) {
            return "Full name cannot be empty.";
        }

        if (email == null || email.trim().isEmpty()) {
            return "Email cannot be empty.";
        }

        if (!email.contains("@") || !email.contains(".")) {
            return "Email format is invalid.";
        }

        if (password == null || password.trim().length() < 4) {
            return "Password must be at least 4 characters.";
        }

        if (userRepository.findById(id.trim()) != null) {
            return "This user ID already exists.";
        }

        if (userRepository.findByEmail(email.trim()) != null) {
            return "This email is already registered.";
        }

        User newUser = new User(
                id.trim(),
                fullName.trim(),
                email.trim(),
                password.trim(),
                Role.MEMBER
        );

        userRepository.addUser(newUser);
        return "SUCCESS";
    }

    public User login(String email, String password) {
        if (email == null || password == null) {
            return null;
        }

        User user = userRepository.findByEmail(email.trim());

        if (user != null && user.getPassword().equals(password.trim())) {
            return user;
        }

        return null;
    }
}