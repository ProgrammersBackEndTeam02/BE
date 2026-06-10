package com.team02.be.service;

import com.team02.be.dto.AdminProductCreateRequest;
import com.team02.be.dto.AdminProductUpdateRequest;
import com.team02.be.dto.BestSellingProductResponse;
import com.team02.be.dto.ProductResponse;
import com.team02.be.entity.Product;
import com.team02.be.exception.NotFoundException;
import com.team02.be.exception.ProductNotFoundException;
import com.team02.be.repository.OrderItemRepository;
import com.team02.be.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    // 상품 데이터에 접근하기 위해 ProductRepository를 주입받음
    private final ProductRepository productRepository;
    // 주문 상품에 접근하기 위해 OrderItemRepository를 주입받음
    private final OrderItemRepository orderItemRepository;

    // 상품 단건 조회 메서드
    @Transactional(readOnly = true)
    public ProductResponse findProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 상품을 찾을 수 없습니다."));

        return ProductResponse.from(product);
    }

    // 상품 등록 메서드
    public void createProduct(AdminProductCreateRequest request) {

        // 같은 상품이 이미 DB에 있는지 확인
        if (productRepository.existsByProductName(request.productName())) {
            throw new IllegalArgumentException("이미 등록된 상품입니다.");
        }

        // DTO 값을 바탕으로 Product 엔티티를 생성함
        Product product = new Product(
                request.productName(),
                request.isDecaf(),
                request.roastingLevel(),
                request.acidity(),
                request.productPrice(),
                request.stock(),
                request.description(),
                request.thumbnailImageUrl(),
                request.detailPageImageUrl()
        );

        // 상품 등록
        productRepository.save(product);
    }

    // 상품 수정 메서드
    public void updateProduct(Long productId, AdminProductUpdateRequest request) {

        // 수정할 상품을 id로 조회함
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않았거나 없는 상품입니다."));

        // 상품명 수정 요청이 들어온 경우에만 중복 검사를 진행함
        if (request.productName() != null
                && !request.productName().equals(product.getProductName())
                && productRepository.existsByProductName(request.productName())) {
            throw new IllegalArgumentException("같은 이름의 상품이 있습니다.");
        }

        // null이 아닌 값만 상품 엔티티에 반영함
        product.updateProduct(
                request.productName(),
                request.isDecaf(),
                request.roastingLevel(),
                request.acidity(),
                request.productPrice(),
                request.stock(),
                request.description(),
                request.thumbnailImageUrl(),
                request.detailPageImageUrl()
        );
    }

    // 상품 삭제 메서드
    public void deleteProduct(Long id) {

        // 삭제하려는 상품이 존재하지 않으면 예외 처리함
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }

        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public BestSellingProductResponse findBestSellingProduct() {

        // 조회 결과를 result 리스트에 담음
        List<BestSellingProductResponse> result =
                // 많이 팔린 상품 중 하나만 가져오므로
                // 0 -> 첫 번째 페이지
                // 1 -> 한 페이지에 가져올 데이터 개수
                orderItemRepository.findBestSellingProducts(PageRequest.of(0, 1));

        // 판매 내역이 없으면 예외 발생
        if (result.isEmpty()) {
            throw new NotFoundException("판매 내역이 없습니다.");
        }

        // 가장 많이 팔린 상품 반환
        return result.get(0);
    }
}