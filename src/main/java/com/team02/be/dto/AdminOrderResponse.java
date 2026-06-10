package com.team02.be.dto;

import com.team02.be.entity.Product;

public record AdminOrderResponse(
        Long id,
        String productName,
        boolean isDecaf,
        Product.RoastingLevel roastingLevel,
        boolean acidity,
        int productPrice,
        int stock,
        String description,
        String thumbnailImageUrl,
        String detailPageImageUrl
) {
    public static AdminOrderResponse from(Product product) {
        return new AdminOrderResponse(
                product.getId(),
                product.getProductName(),
                product.isDecaf(),
                product.getRoastingLevel(),
                product.isAcidity(),
                product.getProductPrice(),
                product.getStock(),
                product.getDescription(),
                product.getThumbnailImageUrl(),
                product.getDetailPageImageUrl()
        );
    }
}