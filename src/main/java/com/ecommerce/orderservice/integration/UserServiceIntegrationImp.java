package com.ecommerce.orderservice.integration;

import com.ecommerce.orderservice.modeldto.LoginDTO;
import com.ecommerce.orderservice.modeldto.OrderDTO;
import com.ecommerce.orderservice.modeldto.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserServiceIntegrationImp implements UserServiceIntegration {

    @Autowired
    private WebClient webClient;

    @Override
    public Mono<String> login(LoginDTO login) {

        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/login")
                        .build())
                .bodyValue(login)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(throwable -> {
                    System.err.println("Original error: " + throwable.getMessage());
                    throwable.printStackTrace();
                })
                .onErrorMap(throwable -> new RuntimeException("Failed to login: " + throwable.getMessage(), throwable));
    }

    /**
     * Check if user is found
     */
   // public Mono<OrderDTO> isUserFound(Long userId, String authToken){
    public Mono<Boolean> fetchUserById(Long id){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/getUserById")
                        .queryParam("id", id)
                        .build())
                .retrieve()
                .bodyToMono(Boolean.class); // Expect a boolean response from the endpoint
    }
}
