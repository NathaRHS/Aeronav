package com.example.app.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.example.app.controller", "com.example.app.config"})
public class DispatcherServletConfig {
}
