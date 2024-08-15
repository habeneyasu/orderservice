
package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.integration.UserServiceIntegration;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.modeldto.OrderDTO;
import com.ecommerce.orderservice.modeldto.Login;
import com.ecommerce.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/orders/")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);




    @Autowired
    private OrderService orderService;

    @Autowired
    private UserServiceIntegration userServiceIntegration;
    @GetMapping("/helloWorld")
    public String HelloWorld(){

        System.out.println("Hello World test message.");
        return "Hello world.";
    }

    /**
     * Call API's using rest template testing
     */
    @GetMapping("/callApi")
    public String getMessage(){
        String uri="http://localhost:8182/api/v1/helloWorld";
        RestTemplate restTemplate=new RestTemplate();
        String result=restTemplate.getForObject(uri,String.class);
        return result;
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
    @GetMapping("/getOrderById")
    public Order getOrderById(@RequestParam("id") Long id){
        return orderService.getOrderById(id);
    }

    /**
     * Create order
     */
    @PostMapping("/createOrder")
    public Order createOrder(@RequestBody OrderDTO orderDTO){
        return orderService.createOrder(orderDTO);
    }



    /**
     * Update order
     */
    @PutMapping("/updateOrder")
    public Order updateOrder(@RequestParam("id") Long id,@RequestBody Order order){
        return orderService.updateOrder(id,order);
    }

    /**
     * Delete order
     */
    @DeleteMapping("/deleteOrder")
    public void deleteOrder(@RequestParam("id") Long id){
         orderService.deleteOrder(id);
    }

    @PostMapping("/login")
    public Mono<String> loginUser(@RequestBody Login login) {



        log.info("This is an INFO log message");
      //  log.debug("This is a DEBUG log message");
      //  log.error("This is an ERROR log message");

        return userServiceIntegration.login(login);
    }

    @GetMapping("/getUser")
    public Mono<OrderDTO> getUser(@RequestParam("userId") Long userId ) {
        String authToken="eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6W10sInN1YiI6ImthbGVidGFrZWxlIiwiaWF0IjoxNzIxMjE1Mzc5LCJleHAiOjE3MjEyMTcxNzl9.riY0PIGA_qEx9q0i6iwd4uhu7zNTwaKH2JlmA9atNEg";
        return userServiceIntegration.isUserFound(userId,authToken);
    }
}