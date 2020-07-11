package com.levi9.code9.booksalesservice.service;

import com.levi9.code9.booksalesservice.dto.AddedCartItemDto;
import com.levi9.code9.booksalesservice.dto.BookDto;
import com.levi9.code9.booksalesservice.dto.CartItemInfoDto;
import com.levi9.code9.booksalesservice.dto.CartItemQuantityDto;

import java.util.List;

public interface CartItemService {

    AddedCartItemDto add(BookDto cartItemDto, Long bookDto, Long userId);

    List<CartItemInfoDto> getAll(Long userId);

    List<CartItemInfoDto> deleteAll(Long userId);

    CartItemInfoDto delete(Long bookId, Long userId);

    CartItemInfoDto updateQuantity(BookDto book, CartItemQuantityDto newQuantityDto, Long userId);
}
