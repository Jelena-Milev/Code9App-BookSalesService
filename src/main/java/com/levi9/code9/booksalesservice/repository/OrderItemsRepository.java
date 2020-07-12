package com.levi9.code9.booksalesservice.repository;

import com.levi9.code9.booksalesservice.model.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItemEntity, Long> {
}
