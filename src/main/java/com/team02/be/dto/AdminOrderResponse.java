package com.team02.be.dto;

import com.team02.be.entity.Product;
import lombok.Getter;

@Getter
public class AdminOrderResponse {

    private final Long id;
    private final String productName;
    private final boolean isDecaf;
    private final Product.RoastingLevel roastingLevel;
    private final boolean acidity;
    private final int productPrice;
    private final int stock;
    private final String description;
    private final String thumbnailImageUrl;
    private final String detailPageImageUrl;

    public AdminOrderResponse(Product product) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.isDecaf = product.isDecaf();
        this.roastingLevel = product.getRoastingLevel();
        this.acidity = product.isAcidity();
        this.productPrice = product.getProductPrice();
        this.stock = product.getStock();
        this.description = product.getDescription();
        this.thumbnailImageUrl = product.getThumbnailImageUrl();
        this.detailPageImageUrl = product.getDetailPageImageUrl();
    }
}
