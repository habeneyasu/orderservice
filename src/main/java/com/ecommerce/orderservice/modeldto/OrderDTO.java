package com.ecommerce.orderservice.modeldto;

import com.ecommerce.orderservice.config.OrderStatus;
import com.ecommerce.orderservice.model.Item;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {


    private Long userId;
    private List<Long> itemId;
    private String orderCode;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private Long createdBy;
    private Long updatedBy;

}