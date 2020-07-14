package com.levi9.code9.booksalesservice.service;

import com.levi9.code9.booksalesservice.dto.cart.CartItemDto;
import com.levi9.code9.booksalesservice.dto.cart.CartItemInfoDto;
import com.levi9.code9.booksalesservice.dto.cart.CartItemQuantityDto;

import java.util.List;

public interface CartItemService {

    List<CartItemInfoDto> getAll(Long userId);

    CartItemInfoDto add(CartItemDto cartItemDto, Long userId);

    List<CartItemInfoDto> deleteAll(Long userId);

    CartItemInfoDto delete(Long bookId, Long userId);

    CartItemInfoDto updateQuantity(Long bookId, CartItemQuantityDto newQuantityDto, Long userId);
}
