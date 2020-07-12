package com.levi9.code9.booksalesservice.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "Order")
@Table(name = "order_form")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String orderIdentifier;

    private Long userId;

    @Column(columnDefinition = "DATE")
    private LocalDate date;

    private BigDecimal totalPrice;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "order_id")
    List<OrderItemEntity> orderItems = new ArrayList<>();
}
