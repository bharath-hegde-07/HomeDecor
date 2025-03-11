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

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @PostMapping("/login")
    public Map<String, String> adminLogin(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        String response = userService.loginUser(username, password);
        Map<String, String> result = new HashMap<>();
        result.put("message", response);

        return result;
    }

    @PostMapping("/unblock")
    public Map<String, String> unblockUser(@RequestParam String username) {
        userService.unblockUser(username);
        Map<String, String> result = new HashMap<>();
        result.put("message", "User unblocked successfully.");
        return result;
    }

    @PostMapping("/add-item")
    public Item addItem(@RequestBody Item item) {
        return itemService.addItem(item);
    }

    @PutMapping("/update-item/{id}")
    public Item updateItem(@PathVariable Long id, @RequestBody Item item) {
        return itemService.updateItem(id, item);
    }

    @DeleteMapping("/delete-item/{id}")
    public Map<String, String> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        Map<String, String> result = new HashMap<>();
        result.put("message", "Item deleted successfully.");
        return result;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
