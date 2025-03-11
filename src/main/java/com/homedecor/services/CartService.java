package com.homedecor.services;

import com.homedecor.models.CartItem;
import com.homedecor.models.Item;
import com.homedecor.models.User;
import com.homedecor.repository.CartRepository;
import com.homedecor.repository.ItemRepository;
import com.homedecor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CartRepository cartRepository;

    private final Map<Long, Map<Long, Integer>> userCarts = new HashMap<>();

    public void addToCart(Long userId, Long itemId, int quantity) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Item> item = itemRepository.findById(itemId);

        if (user.isEmpty() || item.isEmpty()) {
            throw new RuntimeException("User or Item not found");
        }

        CartItem cartItem = cartRepository.findByUserIdAndItemId(userId, itemId)
                .orElse(new CartItem());

        cartItem.setUser(user.get());
        cartItem.setItem(item.get());
        cartItem.setQuantity(cartItem.getQuantity() + quantity);

        cartRepository.save(cartItem);
    }

    public Map<String, Object> viewCart(Long userId) {
        List<CartItem> cartItems = cartRepository.findByUserId(userId);

        List<Map<String, Object>> cartDetails = cartItems.stream().map(cartItem -> {
            Map<String, Object> itemDetails = new HashMap<>();
            itemDetails.put("item_id", cartItem.getItem().getId());
            itemDetails.put("description", cartItem.getItem().getDescription());
            itemDetails.put("quantity", cartItem.getQuantity());
            return itemDetails;
        }).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("cart", cartDetails);
        return response;
    }

    public void purchaseItems(Long userId) {
        Map<Long, Integer> cart = userCarts.getOrDefault(userId, new HashMap<>());
        cart.clear();
    }
}
