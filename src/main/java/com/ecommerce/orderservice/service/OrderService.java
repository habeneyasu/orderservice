package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.modeldto.OrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;


public interface OrderService {

    /**
     * Get all orders
     */
     public List<Order> getAllOrders();

    /**
     * Get order by Id
     */
    public Order getOrderById(Long id);

    /**
     * Create order
     */
    public Order createOrder(OrderDTO orderDTO) throws ExecutionException, InterruptedException;

    /**
     * Update order
     */
    public Order updateOrder(Long id, Order order);

    /**
     * Delete order
     */
    public boolean deleteOrder(Long id);

    /**
     * Get order by order code
     *
     */
    public Order getOrderByOrdercode(String orderCode);

    public String generateTransactionKey();
}
