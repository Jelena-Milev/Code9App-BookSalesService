package com.levi9.code9.booksalesservice.dto.order;

import com.levi9.code9.booksalesservice.dto.cart.CartItemInfoDto;
import com.levi9.code9.booksalesservice.model.OrderItemEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class OrderDto {
    private Long id;
    private String identifier;
    private Long userId;
    private LocalDate date;
    private BigDecimal totalPrice;
    List<OrderItemDto> orderItems;
}
