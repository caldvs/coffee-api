package com.example.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Response wrapper for list of products")
public class ProductListResponse {
    @Schema(description = "List of coffee products")
    private List<Product> products;

    @Schema(description = "Total number of products")
    private long total;

    public ProductListResponse(List<Product> products) {
        this.products = products;
        this.total = products.size();
    }

    // Getters and setters
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        this.total = products.size();
    }

    public long getTotal() {
        return total;
    }
}

