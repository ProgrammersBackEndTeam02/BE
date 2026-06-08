package com.team02.be.controller;
import com.team02.be.entity.Product;
import com.team02.be.dto.UserProductResponse;
import com.team02.be.service.UserProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Product", description = "상품 API")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class UserProductController {

    private final UserProductService userProductService;

    @Operation(
            summary = "상품 목록 조회",
            description = "전체 조회 및 옵셔널 필터링. 파라미터 미입력 시 전체 반환"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping
    public ResponseEntity<List<UserProductResponse>> getProducts(

            @Parameter(description = "디카페인 여부 필터")
            @RequestParam(required = false) Boolean isDecaf,

            @Parameter(description = "로스팅 레벨 필터", example = "LIGHT")
            @RequestParam(required = false) Product.RoastingLevel roastingLevel,

            @Parameter(description = "산미 여부 필터")
            @RequestParam(required = false) Boolean acidity

    ) {
        List<UserProductResponse> result = userProductService.getProducts(isDecaf, roastingLevel, acidity);
        return ResponseEntity.ok(result);
    }
}
