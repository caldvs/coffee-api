package com.example.verifier;

import com.example.service.AIService;
import com.example.model.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CoffeeMenuItemVerifier {
    private static final Logger logger = LoggerFactory.getLogger(CoffeeMenuItemVerifier.class);
    private final AIService aiService;

    @Autowired
    public CoffeeMenuItemVerifier(AIService aiService) {
        this.aiService = aiService;
    }

    public Mono<Boolean> verifyMenuItem(String name) {
        if (name == null || name.trim().isEmpty()) {
            logger.warn("Attempted validation with null or empty coffee name");
            return Mono.just(false);
        }
        
        logger.info("Verifying coffee menu item: '{}'", name);
        return aiService.validateProduct(name.trim())
                .doOnNext(result -> {
                    if (result) {
                        logger.info("Coffee drink '{}' validated successfully", name);
                    } else {
                        logger.info("Coffee drink '{}' failed validation", name);
                    }
                })
                .onErrorResume(e -> {
                    logger.error("Error in AI validation: {}", e.getMessage(), e);
                    return Mono.just(false);
                });
    }
}
