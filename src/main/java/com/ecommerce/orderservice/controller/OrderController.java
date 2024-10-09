
package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.exception.ResourceNotFoundException;
import com.ecommerce.orderservice.integration.UserServiceIntegration;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.modeldto.OrderDTO;
import com.ecommerce.orderservice.modeldto.LoginDTO;
import com.ecommerce.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.apache.kafka.common.security.auth.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserServiceIntegration userServiceIntegration;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    private static final String TOPIC = "order-event";

    @GetMapping("/helloWorld")
    public String getMessage(){

        System.out.println("Hello World test message.");
        return "Hello world.";
    }

    /**
     * Get all orders
     */
    @GetMapping("/getAllOrders")
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

    /**
     * Get order by Id
     */
    @GetMapping("/getOrderById/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") Long id){
        try {
            Order order = orderService.getOrderById(id);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Create order
     */
    @PostMapping("/createOrder")
    public Mono<ResponseEntity<String>> createOrder(@RequestBody OrderDTO orderDTO) {
        // Check if user is found asynchronously
        return fetchUserById(orderDTO.getUserId())
                .flatMap(responseEntity -> {
                    Boolean isUserFound = responseEntity.getBody(); // Extract Boolean value from ResponseEntity

                    if (Boolean.FALSE.equals(isUserFound)) {
                        // Return 404 NOT FOUND if user is not found
                        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("User not found for ID: " + orderDTO.getUserId()));
                    }
                    // Proceed with order creation if user is found
                    return Mono.fromCallable(() -> orderService.createOrder(orderDTO))
                            .flatMap(createOrder -> {
                                if (createOrder != null) {
                                    // Send message to Kafka asynchronously (non-blocking)
                                    String orderCode = createOrder.getOrderCode();
                                    kafkaTemplate.send(TOPIC, orderService.generateTransactionKey(), orderCode)
                                            .whenComplete((sendResult, throwable) -> {
                                                if (throwable != null) {
                                                    onFailure(throwable);
                                                } else {
                                                    onSuccess(sendResult);
                                                }
                                            });

                                    // Return 201 CREATED if order creation is successful
                                    return Mono.just(ResponseEntity.status(HttpStatus.CREATED)
                                            .body("Order placed successfully with ID: " + createOrder.getId()));
                                } else {
                                    // Return 500 INTERNAL SERVER ERROR if order creation fails
                                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                            .body("Failed to create order."));
                                }
                            });
                })
                .onErrorResume(e -> {
                    // Handle any error during the process
                    logger.error("Error occurred while creating order for user ID: {}", orderDTO.getUserId(), e);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Error occurred while processing the order."));
                });
    }

    /**
     * Update order
     */
    @PutMapping("/orders/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") Long id, @RequestBody Order order) {
        Optional<Order> updatedOrder = Optional.ofNullable(orderService.updateOrder(id, order));
        return updatedOrder
                .map(ResponseEntity::ok) // Return 200 OK with the updated order
                .orElseGet(() -> ResponseEntity.notFound().build()); // Return 404 if order not found
    }

    /**
     * Delete order
     */
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") Long id) {
        boolean isDeleted = orderService.deleteOrder(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if successfully deleted
        } else {
            return ResponseEntity.notFound().build(); // Return 404 Not Found if order not found
        }
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> loginUser(@RequestBody @Valid LoginDTO logindto) {

        logger.info("Login attempt for username: {}", logindto.getUsername());

        return userServiceIntegration.login(logindto)
                .map(token -> ResponseEntity.ok(token))  // Return 200 OK with JWT or auth token
                .onErrorResume(e -> {
                    logger.error("Login failed for username: {}", logindto.getUsername(), e);
                    return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body("Invalid credentials"));
                });
    }

    @GetMapping("/getUser/{id}")
    public Mono<ResponseEntity<Boolean>> fetchUserById(@PathVariable("id") Long id) {
        return userServiceIntegration.fetchUserById(id)
                .map(result -> result
                        ? ResponseEntity.ok(true) // 200 OK if the user exists
                        : ResponseEntity.status(404).body(false)) // 404 Not Found if not
                .onErrorResume(e -> {
                    // Log and handle errors
                    logger.error("Error fetching user with id: {}", id, e);
                    return Mono.just(ResponseEntity.status(500).body(false)); // 500 Internal Server Error
                });
    }

    /**
     * Get order by order code
     *
     * @param orderCode
     * @return
     */
    @GetMapping("/findOrderByCode/{order_code}")
    public ResponseEntity<?> findOrderByCode(@PathVariable("order_code") String orderCode) {
        Order order = orderService.getOrderByOrdercode(orderCode); // Assuming this returns an Order object

        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Order not found for code: " + orderCode);
        }
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