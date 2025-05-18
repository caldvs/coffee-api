package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring Boot REST API Application
 */
@SpringBootApplication
@RestController
public class App 
{
    /**
     * Main method to start the Spring Boot application
     */
    public static void main(String[] args)
    {
        SpringApplication.run(App.class, args);
        System.out.println("Spring Boot API started successfully!");
    }
}
