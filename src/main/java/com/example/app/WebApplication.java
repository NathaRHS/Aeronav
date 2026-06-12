package com.example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Classe de démarrage pour l'application Spring Boot
 * Démarre un serveur web sur http://localhost:8080
 */
@SpringBootApplication
@Configuration
@ComponentScan(basePackages = {"com.example.app"})
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
