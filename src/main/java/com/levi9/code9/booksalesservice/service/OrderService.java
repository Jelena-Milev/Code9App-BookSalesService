package com.levi9.code9.booksalesservice.service;

import com.levi9.code9.booksalesservice.dto.order.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto save(List<Long> cartItemsIds, Long userId);
}
