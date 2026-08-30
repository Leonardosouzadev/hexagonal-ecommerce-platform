package br.com.leonardo.product_service.adapters.inbound.rest;

import java.math.BigDecimal;

public record CreateProductRequest(
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        String category,
        String sku
) {
}
