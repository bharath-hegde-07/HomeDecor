package com.homedecor.services;

import com.homedecor.models.User;
import com.homedecor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public Map<String, String> loginUser(String username, String password) {
        logger.info("Login attempt for username: {}", username);
        Map<String, String> response = new HashMap<>();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            logger.warn("User '{}' not found", username);
            response.put("message", "User not found!");
            return response;
        }

        if (user.isBlocked()) {
            logger.warn("Blocked user '{}' attempted to log in", username);
            response.put("message", "User is blocked. Contact Admin to unblock.");
            return response;
        }

        if (!user.getPassword().equals(password)) {
            int attempts = user.getLoginAttempts() + 1;
            user.setLoginAttempts(attempts);
            logger.warn("Invalid password attempt {} for user '{}'", attempts, username);


            if (attempts >= 3) {
                user.setBlocked(true);
                userRepository.save(user);
                logger.error("User '{}' has been blocked due to multiple failed attempts", username);
                response.put("message", "User is blocked due to multiple failed attempts!");
                return response;
            }

            userRepository.save(user);
            response.put("message", "Invalid password! Attempts left: " + (3 - attempts));
            return response;
        }

        // Reset login attempts on successful login
        user.setLoginAttempts(0);
        userRepository.save(user);
        logger.info("User '{}' logged in successfully", username);
        response.put("message", "Login successful");
        response.put("userId", String.valueOf(user.getId()));
        response.put("role", user.getRole());
        return response;

    }

    public void unblockUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setBlocked(false);
            user.setLoginAttempts(0);
            userRepository.save(user);
            logger.info("User '{}' has been unblocked by admin", username);
        } else {
            logger.warn("Attempt to unblock non-existing user '{}'", username);

        }
    }

    public User registerUser(User user) {
        user.setPassword(user.getPassword());
        user.setRole("ROLE_USER");
        logger.info("Registering new user: {}", user.getUsername());
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        logger.info("Fetching all users from database");
        return userRepository.findAll();
    }
}
