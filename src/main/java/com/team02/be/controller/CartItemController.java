package com.team02.be.controller;

import com.team02.be.dto.CartItemUpdateRequest;
import com.team02.be.service.CartItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart/items")
@Tag(name = "Cart", description = "장바구니 API")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PatchMapping("/{cartItemId}")
    @Operation(summary = "장바구니 상품 수량 수정", description = "장바구니 상품 ID로 담긴 상품의 수량을 수정합니다.")
    public ResponseEntity<Void> updateQuantity(
            @PathVariable Long cartItemId,
            @RequestBody CartItemUpdateRequest request
    ) {
        cartItemService.updateQuantity(cartItemId, request.quantity());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cartItemId}")
    @Operation(summary = "장바구니 상품 삭제", description = "장바구니 상품 ID로 장바구니에 담긴 상품을 삭제합니다.")
    public ResponseEntity<Void> delete(@PathVariable Long cartItemId) {
        cartItemService.delete(cartItemId);
        return ResponseEntity.noContent().build();
    }
}
