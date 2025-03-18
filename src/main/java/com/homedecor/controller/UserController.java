package com.homedecor.controller;

import com.homedecor.models.User;
import com.homedecor.repository.UserRepository;
import com.homedecor.services.CartService;
import com.homedecor.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> userLogin(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");
        logger.info("User login attempt: {}", username);
        Map<String, String> loginResponse = userService.loginUser(username, password);

        Map<String, Object> response = new HashMap<>();
        response.put("message", loginResponse.get("message"));

        if (loginResponse.containsKey("userId")) {  // Ensure userId is returned
            response.put("userId", Long.parseLong(loginResponse.get("userId")));
            response.put("role", loginResponse.get("role"));
            logger.info("User {} logged in successfully as {}", username, loginResponse.get("role"));
            return ResponseEntity.ok(response);
        } else {
            logger.warn("Failed login attempt for user: {}", username);
            return ResponseEntity.status(401).body(response);
        }
    }

    @PostMapping("/add-to-cart")
    public Map<String, String> addToCart(@RequestParam Long userId, @RequestParam Long itemId, @RequestParam int quantity) {
        logger.info("Adding item {} (Qty: {}) to cart for user {}", itemId, quantity, userId);
        cartService.addToCart(userId, itemId, quantity);
        logger.info("Item {} added to cart for user {}", itemId, userId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Item added to cart.");
        return response;
    }

    @GetMapping("/view-cart/{userId}")
    public Map<String, Object> viewCart(@PathVariable Long userId) {
        logger.info("Fetching cart details for user {}", userId);
        return cartService.viewCart(userId);
    }

    @PostMapping("/purchase/{userId}")
    public Map<String, String> purchase(@PathVariable Long userId) {
        logger.info("Processing purchase for user {}", userId);
        cartService.purchaseItems(userId);
        logger.info("Purchase successful for user {}", userId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Purchase successful.");
        return response;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        logger.info("User registration attempt: {}", user.getEmail());
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            logger.warn("Registration failed: User with email {} already exists!", user.getEmail());
            return ResponseEntity.badRequest().body("User already exists with this email!");
        }

        user.setRole("ROLE_USER");
        userRepository.save(user);
        logger.info("User registered successfully with email: {}", user.getEmail());

        return ResponseEntity.ok("User registered successfully!");
    }
}
