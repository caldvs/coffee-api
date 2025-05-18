package com.example.verifier;

import com.example.service.AIService;
import com.example.model.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CoffeeMenuItemVerifier {
    private final AIService aiService;

    @Autowired
    public CoffeeMenuItemVerifier(AIService aiService) {
        this.aiService = aiService;
    }

    public Mono<Boolean> verifyMenuItem(String name) {
        return aiService.validateProduct(name)
                .map(result -> {
                    // Log the response for debugging
                    System.out.println("AI Service response: " + result);
                    return result;
                })
                .onErrorResume(e -> {
                    // Log any errors
                    System.err.println("Error in AI validation: " + e.getMessage());
                    return Mono.just(false);
                });
    }
}
