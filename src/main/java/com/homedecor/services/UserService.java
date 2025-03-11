package com.homedecor.services;

import com.homedecor.models.User;
import com.homedecor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Map<String, String> loginUser(String username, String password) {
        Map<String, String> response = new HashMap<>();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            response.put("message", "User not found!");
            return response;
        }

        if (user.isBlocked()) {
            response.put("message", "User is blocked. Contact Admin to unblock.");
            return response;
        }

        if (!user.getPassword().equals(password)) {
            int attempts = user.getLoginAttempts() + 1;
            user.setLoginAttempts(attempts);

            if (attempts >= 3) {
                user.setBlocked(true);
                userRepository.save(user);
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
        }
    }

    public User registerUser(User user) {
        user.setPassword(user.getPassword());
        user.setRole("ROLE_USER");
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
