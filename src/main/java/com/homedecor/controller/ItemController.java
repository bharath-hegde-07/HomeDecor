package com.homedecor.controller;
import com.homedecor.models.Item;
import com.homedecor.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/items")

public class ItemController {
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public List<Item> getAllItems() {
        logger.info("Fetching all items.");
        List<Item> items = itemRepository.findAll();
        logger.info("Total items retrieved: {}", items.size());
        return itemRepository.findAll();
    }
}
