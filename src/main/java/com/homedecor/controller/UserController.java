package com.homedecor.controller;

import com.homedecor.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Map<String, String> userLogin(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        String response = userService.loginUser(username, password);
        Map<String, String> result = new HashMap<>();
        result.put("message", response);

        return result;
    }
}
