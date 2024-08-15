package com.ecommerce.orderservice.integration;

import com.ecommerce.orderservice.modeldto.OrderDTO;
import com.ecommerce.orderservice.modeldto.Login;
import reactor.core.publisher.Mono;

public interface UserServiceIntegration {

    /**
     * Consume the login API
     */
    public Mono<String> login(Login login);

    /**
     * Check if user is found
     */
    public Mono<OrderDTO> isUserFound(Long userId, String authToken);
}
