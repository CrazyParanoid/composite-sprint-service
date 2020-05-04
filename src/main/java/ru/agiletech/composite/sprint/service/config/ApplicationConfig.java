package ru.agiletech.composite.sprint.service.config;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCircuitBreaker
@EnableDiscoveryClient
public class ApplicationConfig {
}
