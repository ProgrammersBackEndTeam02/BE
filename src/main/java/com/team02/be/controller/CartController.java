package com.team02.be.controller;

import com.team02.be.dto.CartItemAddRequest;
import com.team02.be.dto.CartResponse;
import com.team02.be.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.UUID;

@Tag(name = "Cart", description = "장바구니 API")
// 장바구니 관련 API를 처리하는 Controller
// 쿠키 기반 cart_token을 사용해서 비회원 장바구니를 식별함
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    // 쿠키에 저장할 장바구니 식별 토큰 이름
    private static final String CART_TOKEN_COOKIE_NAME = "cart_token";

    // cart_token 쿠키 유지 시간
    // 60초 * 60분 * 24시간 * 3일 = 3일
    private static final int CART_TOKEN_MAX_AGE = 60 * 60 * 24 * 3;

    // 장바구니 관련 비즈니스 로직을 처리하는 Service
    private final CartService cartService;

    @Operation(summary = "장바구니 상품 등록", description = "쿠키 기반 cart_token을 사용하여 비회원 장바구니에 상품을 등록합니다.")
    // 장바구니에 상품을 등록함
    @PostMapping("/items")
    public ResponseEntity<Void> addCartItem(
            // 장바구니에 담을 상품 ID와 수량을 요청 body로 받음
            @Valid @RequestBody CartItemAddRequest request,

            // 요청에 포함된 쿠키를 읽기 위해 사용함
            HttpServletRequest httpServletRequest,

            // 응답에 새 쿠키를 담기 위해 사용함
            HttpServletResponse httpServletResponse
    ) {
        // 요청 쿠키에서 cart_token 값을 가져옴
        String cartToken = getCartTokenFromCookie(httpServletRequest);

        // cart_token 쿠키가 없으면 새 토큰을 생성함
        if (cartToken == null) {
            cartToken = UUID.randomUUID().toString();

            // 새로 생성한 cart_token을 브라우저 쿠키에 저장함
            addCartTokenCookie(httpServletResponse, cartToken);
        }

        // Service에 cartToken과 요청 DTO를 전달해 장바구니 등록 로직을 처리함
        cartService.addCartItem(cartToken, request);

        // 등록 성공 시 응답 body 없이 201 Created 반환
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "장바구니 전체 목록 조회", description = "쿠키 기반 cart_token을 사용하여 비회원 장바구니 전체 목록을 조회합니다.")
    // 장바구니 전체 목록을 조회함
    @GetMapping
    public ResponseEntity<CartResponse> getCart(
            // 요청에 포함된 쿠키를 읽기 위해 사용함
            HttpServletRequest httpServletRequest
    ) {
        // 요청 쿠키에서 cart_token 값을 가져옴
        String cartToken = getCartTokenFromCookie(httpServletRequest);

        // cart_token이 없으면 아직 장바구니가 없는 사용자로 보고 빈 장바구니를 반환함
        if (cartToken == null) {
            return ResponseEntity.ok(new CartResponse(
                    null,
                    java.util.List.of(),
                    0
            ));
        }

        // Service에 cartToken을 전달해 장바구니 전체 목록을 조회함
        CartResponse response = cartService.getCart(cartToken);

        // 조회 결과를 응답 body에 담아 반환함
        return ResponseEntity.ok(response);
    }

    // 요청 쿠키 목록에서 cart_token 값을 찾아 반환함
    private String getCartTokenFromCookie(HttpServletRequest request) {

        // 요청에 쿠키가 하나도 없으면 null 반환
        if (request.getCookies() == null) {
            return null;
        }

        // 쿠키 배열에서 이름이 cart_token인 쿠키를 찾음
        return Arrays.stream(request.getCookies())
                .filter(cookie -> CART_TOKEN_COOKIE_NAME.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }

    // 새 cart_token 값을 응답 쿠키에 추가함
    private void addCartTokenCookie(HttpServletResponse response, String cartToken) {

        // cart_token 이름으로 새 쿠키 생성
        Cookie cookie = new Cookie(CART_TOKEN_COOKIE_NAME, cartToken);

        // 전체 경로에서 쿠키를 사용할 수 있도록 설정
        cookie.setPath("/");

        // 쿠키 유지 시간을 3일로 설정
        cookie.setMaxAge(CART_TOKEN_MAX_AGE);

        // JavaScript에서 쿠키를 직접 읽지 못하게 설정
        // 보안상 기본적으로 true 권장
        cookie.setHttpOnly(true);

        // 응답에 쿠키 추가
        response.addCookie(cookie);
    }
}