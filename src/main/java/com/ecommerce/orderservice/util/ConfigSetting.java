package com.ecommerce.orderservice.util;

import com.ecommerce.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.ecommerce.orderservice.model.Order;

public class ConfigSetting {

    @Autowired
    private OrderRepository orderRepository;
    public String generateOrderCode() {
        String prefix = "ORD-";
        String maxOrderCode = orderRepository.findTopByOrderByOrderCodeDesc()
                .map(Order::getOrderCode)
                .orElse(prefix + "000000");

        int currentMax = Integer.parseInt(maxOrderCode.replace(prefix, ""));
        int newOrderCodeInt = currentMax + 1;
        String newOrderCodeStr = String.format("%06d", newOrderCodeInt);

        return prefix + newOrderCodeStr;
    }
}
