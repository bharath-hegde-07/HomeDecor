package com.homedecor.controller;

import com.homedecor.models.Item;
import com.homedecor.models.User;
import com.homedecor.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.homedecor.services.ItemService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @PostMapping("/login")
    public Map<String, String> adminLogin(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");
        logger.info("Admin login attempt: {}", username);
        Map<String, String> response = userService.loginUser(username, password);
        logger.info("Admin login response: {}", response.get("message"));

        return userService.loginUser(username, password);
    }

    @PostMapping("/unblock")
    public Map<String, String> unblockUser(@RequestParam String username) {
        logger.info("Attempting to unblock user: {}", username);
        userService.unblockUser(username);
        logger.info("User {} unblocked successfully.", username);
        Map<String, String> result = new HashMap<>();
        result.put("message", "User unblocked successfully.");
        return result;
    }

    @PostMapping("/add-item")
    public Item addItem(@RequestBody Item item) {
        logger.info("Adding new item: {}", item.getName());
        Item addedItem = itemService.addItem(item);
        logger.info("Item added successfully with ID: {}", addedItem.getId());
        return itemService.addItem(item);
    }

    @PutMapping("/update-item/{id}")
    public Item updateItem(@PathVariable Long id, @RequestBody Item item) {
        logger.info("Updating item with ID: {}", id);
        Item updatedItem = itemService.updateItem(id, item);
        logger.info("Item with ID {} updated successfully.", id);
        return itemService.updateItem(id, item);
    }

    @DeleteMapping("/delete-item/{id}")
    public Map<String, String> deleteItem(@PathVariable Long id) {
        logger.warn("Deleting item with ID: {}", id);
        itemService.deleteItem(id);
        logger.info("Item with ID {} deleted successfully.", id);

        Map<String, String> result = new HashMap<>();
        result.put("message", "Item deleted successfully.");
        return result;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        logger.info("Fetching all users.");
        List<User> users = userService.getAllUsers();
        logger.info("Total users retrieved: {}", users.size());
        return userService.getAllUsers();

    }
}
