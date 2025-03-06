package com.homedecor.controller;

import com.homedecor.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

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
}
