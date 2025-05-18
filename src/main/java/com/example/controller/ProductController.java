package com.example.controller;

import com.example.model.Product;
import com.example.model.ProductRequest;
import com.example.model.ErrorResponse;
import com.example.model.ProductListResponse;
import com.example.verifier.CoffeeMenuItemVerifier;
import com.example.repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
@Validated
@Tag(name = "Products", description = "Coffee Products API")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CoffeeMenuItemVerifier verifier;

    @Operation(summary = "Get all products", description = "Retrieves a list of all coffee products")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved products",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ProductListResponse.class)))
    @GetMapping
    public ProductListResponse getAllProducts() {
        return new ProductListResponse(productRepository.findAll());
    }

    @Operation(summary = "Create a new product", description = "Creates a new coffee product with AI validation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product created successfully",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = Product.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input or failed validation",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public Mono<ResponseEntity<?>> createProduct(@Valid @RequestBody ProductRequest request) {

        return verifier.verifyMenuItem(request.getName())
                .map(isValid -> {
                    if (!isValid) {
                        Map<String, Object> errorResponse = new HashMap<>();
                        errorResponse.put("status", "error");
                        errorResponse.put("message", "Invalid coffee menu item");
                        errorResponse.put("details", String.format("'%s' is not recognized as a valid coffee drink", request.getName()));
                        return ResponseEntity
                            .badRequest()
                            .body(errorResponse);
                    }

                    Product product = new Product();
                    product.setName(request.getName());
                    product.setDescription(request.getDescription());
                    product.setPrice(request.getPrice());
                    product.setVerified(true);

                    Product savedProduct = productRepository.save(product);
                    return ResponseEntity.ok(savedProduct);
                })
                .onErrorResume(e -> {
                    Map<String, Object> errorResponse = new HashMap<>();
                    errorResponse.put("status", "error");
                    errorResponse.put("message", "Error processing request");
                    errorResponse.put("details", e.getMessage());
                    return Mono.just(ResponseEntity
                        .badRequest()
                        .body(errorResponse));
                });
    }


    @Operation(summary = "Get a product by ID", description = "Retrieves a specific coffee product by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved product",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = Product.class))),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update a product", description = "Updates an existing coffee product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product updated successfully",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = Product.class))),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        return productRepository.findById(id)
            .map(product -> {
                product.setName(productDetails.getName());
                product.setDescription(productDetails.getDescription());
                product.setPrice(productDetails.getPrice());
                Product updatedProduct = productRepository.save(product);
                return ResponseEntity.ok(updatedProduct);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a product", description = "Deletes a coffee product by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        return productRepository.findById(id)
            .map(product -> {
                productRepository.delete(product);
                return ResponseEntity.ok().build();
            })
            .orElse(ResponseEntity.notFound().build());
    }

    // Add global exception handler for validation errors
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}

