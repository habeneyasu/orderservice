package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.model.Item;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.modeldto.OrderDTO;
import com.ecommerce.orderservice.repository.ItemRepository;
import com.ecommerce.orderservice.repository.OrderRepository;
import com.ecommerce.orderservice.util.ConfigSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImp implements OrderService {

    ConfigSetting utilSetting=new ConfigSetting();

@Autowired
private OrderRepository orderRepository;

@Autowired
private ItemRepository itemRepository;

    /**
     * Get all orders
     */
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    /**
     * Get order by Id
     */
    public Order getOrderById(Long id){
        return orderRepository.findById(id).orElse(null);
    }

    /**
     * Create order
     */
    public Order createOrder(OrderDTO orderDTO){

        Order newOrder=new Order();

        newOrder.setUserId(orderDTO.getUserId());
        newOrder.setOrderCode(orderDTO.getOrderCode());
        newOrder.setStatus(orderDTO.getStatus());
        newOrder.setTotalAmount(orderDTO.getTotalAmount());

        List<Item> items = itemRepository.findAllById(orderDTO.getItemId());

        newOrder.setItems(items);


        return orderRepository.save(newOrder);
    }

    /**
     * Update order
     */
    public Order updateOrder(Long id, Order order){
        Order findOrder=getOrderById(id);
        if(findOrder!=null){
            findOrder.setUserId(order.getUserId());
            findOrder.setItems(order.getItems());
            findOrder.setTotalAmount(order.getTotalAmount());
            findOrder.setUpdatedBy(order.getUpdatedBy());
            findOrder.setUpdatedAt(order.getUpdatedAt());
            findOrder.setStatus(order.getStatus());
            return orderRepository.save(findOrder);
        }
        return null;
    }

    /**
     * Delete order
     */
    public void deleteOrder(Long id) {
        Order findOrder = getOrderById(id);
        if (findOrder != null) {
            orderRepository.delete(findOrder);
        }
    }
}
