package com.homedecor.services;

import com.homedecor.models.User;
import com.homedecor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return "User not found!";
        }

        if (user.isBlocked()) {
            return "User is blocked. Contact Admin to unblock.";
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            int attempts = user.getLoginAttempts() + 1;
            user.setLoginAttempts(attempts);

            if (attempts >= 3) {
                user.setBlocked(true);
                userRepository.save(user);
                return "User is blocked due to multiple failed attempts!";
            }

            userRepository.save(user);
            return "Invalid password! Attempts left: " + (3 - attempts);
        }

        // Reset login attempts on successful login
        user.setLoginAttempts(0);
        userRepository.save(user);

        return "Login successful!";
    }

    public void unblockUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setBlocked(false);
            user.setLoginAttempts(0);
            userRepository.save(user);
        }
    }
}
