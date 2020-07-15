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
@EqualsAndHashCode
@Entity(name = "Order")
@Table(name = "order_form")
public class OrderEntity {

    @Id
    @SequenceGenerator(name="seq_order_ids", sequenceName = "seq_order_ids", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_order_ids")
    private Long id;

    private String orderIdentifier;

    private Long userId;

    @Column(columnDefinition = "DATE")
    private LocalDate date;

    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    List<OrderItemEntity> orderItems = new ArrayList<>();

    public OrderEntity(String orderIdentifier, Long userId, LocalDate date, BigDecimal totalPrice) {
        this.orderIdentifier = orderIdentifier;
        this.userId = userId;
        this.date = date;
        this.totalPrice = totalPrice;
    }

    public void addOrderItem(OrderItemEntity orderItemEntity){
        this.orderItems.add(orderItemEntity);
        orderItemEntity.setOrder(this);
    }
}
