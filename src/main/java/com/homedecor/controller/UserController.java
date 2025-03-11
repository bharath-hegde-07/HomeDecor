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

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @PostMapping("/login")
    public Map<String, String> userLogin(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        String response = userService.loginUser(username, password);
        Map<String, String> result = new HashMap<>();
        result.put("message", response);

        return result;
    }

    @PostMapping("/add-to-cart")
    public Map<String, String> addToCart(@RequestParam Long userId, @RequestParam Long itemId, @RequestParam int quantity) {
        cartService.addToCart(userId, itemId, quantity);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Item added to cart.");
        return response;
    }

    @GetMapping("/view-cart/{userId}")
    public Map<String, Object> viewCart(@PathVariable Long userId) {
        return cartService.viewCart(userId);
    }

    @PostMapping("/purchase/{userId}")
    public Map<String, String> purchase(@PathVariable Long userId) {
        cartService.purchaseItems(userId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Purchase successful.");
        return response;
    }
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("User already exists with this email!");
        }

        user.setRole("ROLE_USER");
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }
}
