package com.team02.be.dto;

import java.time.LocalDate;
import java.util.List;

public record AdminOrderGroupResponse(
        LocalDate deliveryDate,
        String customerEmail,
        String address,
        String zipCode,
        int orderCount,
        int totalGroupPrice,
        List<AdminOrderSearchResponse> orders
) {
}