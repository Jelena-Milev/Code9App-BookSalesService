package com.levi9.code9.booksalesservice.service;

import com.levi9.code9.booksalesservice.dto.AddedCartItemDto;
import com.levi9.code9.booksalesservice.dto.BookDto;
import com.levi9.code9.booksalesservice.dto.CartItemInfoDto;

import java.util.List;

public interface CartItemService {

    AddedCartItemDto add(BookDto cartItemDto, Long bookDto, Long userId);

    List<CartItemInfoDto> getAll(Long userId);
}
