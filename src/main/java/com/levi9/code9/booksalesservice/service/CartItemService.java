package com.levi9.code9.booksalesservice.service;

import com.levi9.code9.booksalesservice.dto.cart.AddedCartItemDto;
import com.levi9.code9.booksalesservice.dto.bookService.BookDto;
import com.levi9.code9.booksalesservice.dto.cart.CartItemInfoDto;
import com.levi9.code9.booksalesservice.dto.cart.CartItemQuantityDto;

import java.util.List;

public interface CartItemService {

    AddedCartItemDto add(BookDto cartItemDto, Long bookDto, Long userId);

    List<CartItemInfoDto> getAll(Long userId);

    List<CartItemInfoDto> deleteAll(Long userId);

    CartItemInfoDto delete(Long bookId, Long userId);

    CartItemInfoDto updateQuantity(BookDto book, CartItemQuantityDto newQuantityDto, Long userId);
}
