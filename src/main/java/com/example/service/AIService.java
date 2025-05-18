package com.example.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

@Service
public class AIService {
    private static final Logger logger = LoggerFactory.getLogger(AIService.class);
    private final WebClient webClient;
    
    public AIService(@Value("${openai.api.key}") String apiKey) {
        logger.info("Initializing AIService with API key length: {}", apiKey.length());
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
    
    public Mono<Boolean> validateProduct(String productName) {
        String prompt = String.format("Is '%s' a valid coffee drink name? " +
                "Respond with only 'true' or 'false'.", productName);
        
        logger.debug("Validating product name: {}", productName);
        logger.debug("Using prompt: {}", prompt);
        
        ChatRequest request = new ChatRequest(prompt);
                
        return webClient.post()
                .uri("/chat/completions")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ChatResponse.class)
                .doOnSubscribe(subscription -> 
                    logger.debug("Sending request to OpenAI API"))
                .doOnNext(response -> 
                    logger.debug("Received response from OpenAI API: {}", response))
                .map(response -> {
                    String answer = response.getChoices().get(0).getMessage().getContent().trim().toLowerCase();
                    logger.info("AI validation result for '{}': {}", productName, answer);
                    return "true".equals(answer);
                })
                .doOnError(error -> 
                    logger.error("Error during OpenAI API call: {}", error.getMessage()))
                .onErrorResume(e -> {
                    logger.error("Falling back to false due to error: {}", e.getMessage(), e);
                    return Mono.just(false);
                });
    }
    
    // Request/Response classes
    private static class ChatRequest {
        private String model = "gpt-3.5-turbo";
        private List<Message> messages;
        
        public ChatRequest(String prompt) {
            this.messages = List.of(new Message("user", prompt));
        }
        
        // Getters
        public String getModel() { return model; }
        public List<Message> getMessages() { return messages; }
    }
    
    private static class Message {
        private String role;
        private String content;
        
        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
        
        // Getters
        public String getRole() { return role; }
        public String getContent() { return content; }
    }
    
    private static class ChatResponse {
        private List<Choice> choices;
        
        public List<Choice> getChoices() { return choices; }
    }
    
    private static class Choice {
        @JsonProperty("message")
        private Message message;
        
        public Message getMessage() { return message; }
    }
}
