package com.team02.be.controller;

import com.team02.be.dto.CartItemUpdateRequest;
import com.team02.be.service.CartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart/items")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PatchMapping("/{cartItemId}")
    public ResponseEntity<Void> updateQuantity(
            @PathVariable Long cartItemId,
            @RequestBody CartItemUpdateRequest request
    ) {
        cartItemService.updateQuantity(cartItemId, request.quantity());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> delete(@PathVariable Long cartItemId) {
        cartItemService.delete(cartItemId);
        return ResponseEntity.noContent().build();
    }
}