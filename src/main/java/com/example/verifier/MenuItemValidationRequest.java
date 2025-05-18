package com.example.verifier;

public class MenuItemValidationRequest {
    private String name;
    public MenuItemValidationRequest(String name) {
        this.name = name;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}