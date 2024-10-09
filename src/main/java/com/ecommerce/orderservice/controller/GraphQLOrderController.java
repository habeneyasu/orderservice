package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.integration.UserServiceIntegration;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.modeldto.OrderDTO;
import com.ecommerce.orderservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
public class GraphQLOrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserServiceIntegration userServiceIntegration;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    private static final String TOPIC = "order-event";
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @QueryMapping
    public String getMessage() {
        logger.info("Hello World test message.");
        return "Welcome to the incorporation of GraphQL into the Spring Boot microservice application.";
    }

    @QueryMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @QueryMapping
    public Order getOrderById(@Argument Long id) {
        return orderService.getOrderById(id);
    }


    @MutationMapping
    public String createOrder(@Argument OrderDTO orderDTO) throws ExecutionException, InterruptedException {
        // Check if user is found
        Mono<Boolean> isUserFound = getUser(orderDTO.getUserId());

        logger.info("Is user found: " + isUserFound);

        if (!isUserFound.block()) {
            return "User not found for ID: " + orderDTO.getUserId();
        }

        // Proceed with order creation
        Order createOrder = orderService.createOrder(orderDTO);
        logger.info("Is user order created: " + createOrder);

        if (createOrder != null) {
            // Asynchronous Kafka message sending
            String orderCode = createOrder.getOrderCode();
            kafkaTemplate.send(TOPIC, orderService.generateTransactionKey(), orderCode)
                    .whenComplete((sendResult, throwable) -> {
                        if (throwable != null) {
                            onFailure(throwable);
                        } else {
                            onSuccess(sendResult);
                        }
                    });

            return "Order placed successfully with ID: " + createOrder.getId();
        } else {
            return "Failed to create order.";
        }
    }

    @QueryMapping
    public Mono<Boolean> getUser(@Argument Long id) {
        return userServiceIntegration.fetchUserById(id);
    }

    @QueryMapping
    public Order findOrderByCode(@Argument String orderCode) {
        return orderService.getOrderByOrdercode(orderCode);
    }

    private void onSuccess(SendResult<String, String> sendResult) {
        logger.info("Received new meta data.\n"+ "Topic:{},Partition:{}",
                sendResult.getRecordMetadata().topic(),
                sendResult.getRecordMetadata().partition());
    }

    private void onFailure(Throwable throwable) {
        logger.info("There was an error sending the message.\n");
    }
}
