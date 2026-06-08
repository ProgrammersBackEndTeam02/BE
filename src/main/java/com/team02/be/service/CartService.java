package com.team02.be.service;

import com.team02.be.dto.CartItemAddRequest;
import com.team02.be.dto.CartItemResponse;
import com.team02.be.dto.CartResponse;
import com.team02.be.entity.Cart;
import com.team02.be.entity.CartItem;
import com.team02.be.entity.Product;
import com.team02.be.exception.ProductNotFoundException;
import com.team02.be.repository.CartItemRepository;
import com.team02.be.repository.CartRepository;
import com.team02.be.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 장바구니 관련 비즈니스 로직을 처리하는 Service
// Controller에서 전달받은 cartToken과 요청 DTO를 이용해 장바구니 등록/조회 로직을 처리함
@Service
@RequiredArgsConstructor
public class CartService {

    // carts 테이블에 접근하기 위한 Repository
    // cartToken으로 Cart를 찾거나, Cart를 새로 저장할 때 사용함
    private final CartRepository cartRepository;

    // cart_items 테이블에 접근하기 위한 Repository
    // 장바구니에 담긴 상품을 조회하거나 저장할 때 사용함
    private final CartItemRepository cartItemRepository;

    // products 테이블에 접근하기 위한 Repository
    // 요청으로 받은 productId가 실제 존재하는 상품인지 확인할 때 사용함
    private final ProductRepository productRepository;

    // 장바구니에 상품을 등록함
    // cartToken은 쿠키에서 가져온 비회원 장바구니 식별값임
    // request에는 장바구니에 담을 productId와 quantity가 들어 있음
    @Transactional
    public void addCartItem(String cartToken, CartItemAddRequest request) {

        // 1. cartToken으로 기존 장바구니를 조회함
        // 쿠키에 담긴 cartToken과 같은 값을 가진 Cart가 DB에 있는지 확인함
        // 예: carts 테이블에서 cart_token = cartToken 인 데이터를 찾음
        Cart cart = cartRepository.findByCartToken(cartToken)

                // 2. 기존 장바구니가 없으면 새 Cart를 생성해서 저장함
                // 사용자가 처음 장바구니에 상품을 담는 경우 여기에 들어옴
                // new Cart(cartToken)으로 비회원 장바구니를 만들고 DB에 저장함
                .orElseGet(() -> cartRepository.save(new Cart(cartToken)));

        // 3. 요청 DTO에서 받은 productId로 상품을 조회함
        // request.productId()는 프론트가 보낸 상품 ID임
        // 이 ID는 products 테이블의 PK(id)를 의미함
        Product product = productRepository.findById(request.productId())

                // 4. 해당 productId의 상품이 DB에 없으면 예외를 발생시킴
                // 존재하지 않는 상품을 장바구니에 담을 수 없도록 막는 검증 로직임
                .orElseThrow(() -> new ProductNotFoundException(request.productId()));

        // 5. 현재 장바구니에 같은 상품이 이미 담겨 있는지 조회함
        // cart_items 테이블에서 cart_id와 product_id가 둘 다 같은 데이터가 있는지 확인함
        // 같은 상품이 이미 있으면 새 row를 만들지 않고 quantity만 증가시켜야 함
        cartItemRepository.findByCartAndProduct(cart, product)
                .ifPresentOrElse(

                        // 6-1. 같은 상품이 이미 장바구니에 있으면 기존 수량에 요청 수량을 더함
                        // 예: 기존 수량 2개 + 새로 담은 수량 1개 = 총 3개
                        cartItem -> cartItem.increaseQuantity(request.quantity()),

                        // 6-2. 같은 상품이 아직 장바구니에 없으면 새 CartItem을 생성해서 저장함
                        // CartItem은 "이 장바구니에 이 상품이 몇 개 담겼는지"를 나타냄
                        () -> cartItemRepository.save(new CartItem(cart, product, request.quantity()))
                );
    }

    // 장바구니 전체 목록을 조회함
    // cartToken을 기준으로 현재 사용자의 장바구니를 찾고, 담긴 상품 목록을 응답 DTO로 변환함
    @Transactional(readOnly = true)
    public CartResponse getCart(String cartToken) {

        // 1. cartToken으로 장바구니를 조회함
        // 쿠키에 담긴 cartToken과 같은 값을 가진 Cart가 DB에 있는지 확인함
        Cart cart = cartRepository.findByCartToken(cartToken)

                // 2. 해당 cartToken의 장바구니가 없으면 null로 처리함
                // 아직 아무 상품도 담지 않은 사용자일 수 있음
                .orElse(null);

        // 3. 장바구니가 없으면 빈 장바구니 응답을 반환함
        // 프론트 입장에서는 items가 빈 배열이고 totalPrice가 0인 장바구니를 받게 됨
        if (cart == null) {
            return new CartResponse(
                    // 아직 DB에 생성된 Cart가 없으므로 cartId는 null
                    null,
                    // 장바구니 상품 목록이 없으므로 빈 리스트 반환
                    List.of(),
                    // 담긴 상품이 없으므로 총 금액은 0원
                    0
            );
        }

        // 4. 해당 장바구니에 담긴 모든 CartItem을 조회함
        // cart_items 테이블에서 cart_id가 현재 Cart의 id와 같은 데이터들을 가져옴
        List<CartItem> cartItems = cartItemRepository.findAllByCart(cart);

        // 5. CartItem 엔티티 목록을 CartItemResponse DTO 목록으로 변환함
        // Entity를 그대로 프론트에 주지 않고, 화면에 필요한 값만 DTO에 담아서 내려줌
        List<CartItemResponse> items = cartItems.stream()
                .map(cartItem -> {

                    // 6. CartItem에 연결된 Product를 꺼냄
                    // CartItem은 Product와 ManyToOne 관계로 연결되어 있음
                    // 이 Product에서 상품명, 가격, 썸네일 이미지 등을 가져옴
                    Product product = cartItem.getProduct();

                    // 7. 상품별 총 금액을 계산함
                    // 상품 가격 * 장바구니에 담긴 수량
                    int itemTotalPrice = product.getProductPrice() * cartItem.getQuantity();

                    // 8. 장바구니 상품 하나를 응답 DTO로 변환함
                    // 이 DTO 하나가 장바구니 화면의 상품 한 줄에 해당함
                    return new CartItemResponse(
                            // 장바구니 상품 ID
                            cartItem.getId(),
                            // 상품 ID
                            product.getId(),
                            // 상품명
                            product.getProductName(),
                            // 상품 썸네일 이미지 URL
                            product.getThumbnailImageUrl(),
                            // 상품 가격
                            product.getProductPrice(),
                            // 장바구니에 담긴 수량
                            cartItem.getQuantity(),
                            // 상품별 총 금액
                            itemTotalPrice
                    );
                })
                .toList();

        // 9. 장바구니 전체 총 금액을 계산함
        // CartItemResponse 각각의 itemTotalPrice를 전부 더함
        int totalPrice = items.stream()
                .mapToInt(CartItemResponse::itemTotalPrice)
                .sum();

        // 10. 장바구니 전체 조회 응답 DTO를 생성해서 반환함
        // CartResponse는 장바구니 전체 박스 역할을 함
        // 그 안에 CartItemResponse 목록과 전체 총 금액이 들어감
        return new CartResponse(
                // 장바구니 ID
                cart.getId(),
                // 장바구니에 담긴 상품 목록
                items,
                // 장바구니 전체 총 금액
                totalPrice
        );
    }
}