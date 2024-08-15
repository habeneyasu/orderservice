package com.ecommerce.orderservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.nio.DoubleBuffer;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false )
    private Long quantity;

    @Column(name="unit_price",nullable = false)
    private Double unitPrice;

    @Column(name = "total_price",nullable = false)
    private Double totalPrice;

    private String description;

}
