package com.example.verifier;

public class MenuItemValidationResponse {
    private boolean valid;
    private String message;

    // Getters and setters
    public boolean isValid() { return valid; }
    public void setValid(boolean valid) { this.valid = valid; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}