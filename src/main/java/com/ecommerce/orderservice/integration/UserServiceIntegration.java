package com.ecommerce.orderservice.integration;

import com.ecommerce.orderservice.modeldto.LoginDTO;
import com.ecommerce.orderservice.modeldto.OrderDTO;
import com.ecommerce.orderservice.modeldto.LoginDTO;
import reactor.core.publisher.Mono;

public interface UserServiceIntegration {

    /**
     * Consume the login API
     */
    public Mono<String> login(LoginDTO login);

    /**
     * Check if user is found
     */
    //public Mono<OrderDTO> isUserFound(Long userId, String authToken);
    public Mono<Boolean> fetchUserById(Long id);
}
