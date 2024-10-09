package com.ecommerce.orderservice.service;

import ch.qos.logback.classic.spi.IThrowableProxy;
import com.ecommerce.orderservice.controller.OrderController;
import com.ecommerce.orderservice.exception.ResourceNotFoundException;
import com.ecommerce.orderservice.model.Item;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.modeldto.OrderDTO;
import com.ecommerce.orderservice.repository.ItemRepository;
import com.ecommerce.orderservice.repository.OrderRepository;
import com.ecommerce.orderservice.util.ConfigSetting;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class OrderServiceImp implements OrderService {

    ConfigSetting utilSetting=new ConfigSetting();

@Autowired
private OrderRepository orderRepository;

@Autowired
private ItemRepository itemRepository;



    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    /**
     * Get all orders
     */
    @Cacheable(value = "ordersCache")
    public List<Order> getAllOrders(){
        try {
            List<Order> orders = orderRepository.findAll();
            logger.info("Successfully retrieved all orders.");
            return orders;
        } catch (Exception e) {
            logger.error("Error retrieving orders: ", e);
            throw new ResourceNotFoundException("Error retrieving orders");
        }
    }

    /**
     * Get order by Id
     */
    @Cacheable(value = "ordersCache", key = "#id")
    public Order getOrderById(Long id){
        try {
            return orderRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        } catch (Exception e) {
            logger.error("Error retrieving order with id {}: ", id, e);
            throw new RuntimeException("Error retrieving order with id: " + id, e);
        }
    }

    /**
     * Create order
     */
    @CachePut(value = "ordersCache", key = "#result.id")
    public Order createOrder(OrderDTO orderDTO) throws ExecutionException, InterruptedException {

        try {
            // Validate OrderDTO
            if (orderDTO == null || orderDTO.getItemId() == null) {
                throw new ResourceNotFoundException("Invalid order data");
            }

            // Create new Order
            Order newOrder = new Order();
            newOrder.setUserId(orderDTO.getUserId());
            newOrder.setOrderCode(orderDTO.getOrderCode());
            newOrder.setStatus(orderDTO.getStatus());
            newOrder.setTotalAmount(orderDTO.getTotalAmount());

            // Retrieve Items and set to Order
            List<Item> items = itemRepository.findAllById(orderDTO.getItemId());
            newOrder.setItems(items);

            // Save Order
            Order savedOrder = orderRepository.save(newOrder);

            // Logging
            String message = String.format("Your order has been placed. The order code is: %s. The total amount is: %s",
                    savedOrder.getOrderCode(), savedOrder.getTotalAmount());
            logger.info(message);

            return savedOrder;
        } catch (Exception e) {
            logger.error("Error creating order: ", e);
            throw new RuntimeException("Error creating order", e);
        }
    }

    public String generateTransactionKey(){
        return UUID.randomUUID().toString();
    }
    /**
     * Update order
     */
    @Transactional
    public Order updateOrder(Long id, Order order) {
        try {
            // Validate OrderDTO
            if (order == null || id == null) {
                throw new IllegalArgumentException("Invalid order data");
            }

            // Retrieve existing order
            Order existingOrder = orderRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

            // Update existing order with new values
            existingOrder.setUserId(order.getUserId());
            existingOrder.setItems(order.getItems());
            existingOrder.setTotalAmount(order.getTotalAmount());
            existingOrder.setUpdatedBy(order.getUpdatedBy());
            existingOrder.setUpdatedAt(order.getUpdatedAt());
            existingOrder.setStatus(order.getStatus());

            // Save updated order
            Order updatedOrder = orderRepository.save(existingOrder);

            // Logging
            String message = String.format("Order with id %d has been updated.", id);
            logger.info(message);

            return updatedOrder;
        } catch (Exception e) {
            logger.error("Error updating order with id {}: ", id, e);
            throw new RuntimeException("Error updating order with id: " + id, e);
        }
    }

    /**
     * Delete order
     */
    @CacheEvict(value = "ordersCache", key = "#orderId")
    @Transactional
    public boolean deleteOrder(Long id) {
        return orderRepository.findById(id)
                .map(order -> {
                    orderRepository.delete(order);
                    return true;
                })
                .orElse(false); // Return false if order is not found
    }
    /**
     * Get order by order code
     *
     */
    public Order getOrderByOrdercode(String orderCode){
        return orderRepository.findByOrderCode(orderCode);
    }
}
