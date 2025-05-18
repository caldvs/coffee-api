package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Schema(description = "Coffee product information")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the product", example = "1")
    private Long id;
    
    @Schema(description = "Name of the coffee drink", example = "Cappuccino")
    private String name;
    
    @Schema(description = "Detailed description of the coffee drink", example = "Classic Italian coffee with steamed milk foam")
    private String description;
    
    @Schema(description = "Price of the coffee drink", example = "3.99")
    private Double price;
    
    @Schema(description = "Whether the coffee drink name has been verified", example = "true")
    private Boolean verified;

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public Boolean getVerified() {
        return verified;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }
}
