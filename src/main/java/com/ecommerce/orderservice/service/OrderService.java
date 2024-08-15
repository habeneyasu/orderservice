package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.modeldto.OrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;


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
    public Order createOrder(OrderDTO orderDTO);

    /**
     * Update order
     */
    public Order updateOrder(Long id, Order order);

    /**
     * Delete order
     */
    public void deleteOrder(Long id);
}
