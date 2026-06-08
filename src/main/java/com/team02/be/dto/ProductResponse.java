package com.team02.be.dto;

import com.team02.be.entity.Product;
import com.team02.be.entity.Product.RoastingLevel;

public record ProductResponse(
        Long id,
        String productName,
        boolean isDecaf,
        RoastingLevel roastingLevel,
        boolean acidity,
        int productPrice,
        int stock,
        String description,
        String thumbnailImageUrl,
        String detailPageImageUrl
) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(
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