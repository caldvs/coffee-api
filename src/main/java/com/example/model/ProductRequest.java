package com.example.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request object for creating or updating a coffee product")
public class ProductRequest {
    @NotBlank(message = "Name is required")
    @Schema(description = "Name of the coffee drink", example = "Cappuccino", required = true)
    private String name;

    @NotBlank(message = "Description is required")
    @Schema(description = "Detailed description of the coffee drink", example = "Classic Italian coffee with steamed milk foam", required = true)
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    @Schema(description = "Price of the coffee drink", example = "3.99", required = true, minimum = "0.01")
    private Double price;

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
