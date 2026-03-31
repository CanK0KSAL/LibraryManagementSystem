package service;

import model.Role;
import model.User;
import repository.UserRepository;

public class AuthService {
    private UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean register(String id, String fullName, String email, String password) {
        if (userRepository.findById(id) != null) {
            return false;
        }

        if (userRepository.findByEmail(email) != null) {
            return false;
        }

        User newUser = new User(id, fullName, email, password, Role.MEMBER);
        userRepository.addUser(newUser);
        return true;
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }
}