package com.levi9.code9.booksalesservice.repository;

import com.levi9.code9.booksalesservice.model.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {

    List<CartItemEntity> findByUserId(Long userId);

    Optional<CartItemEntity> findByBookIdAndUserId(Long bookId, Long userId);
}
