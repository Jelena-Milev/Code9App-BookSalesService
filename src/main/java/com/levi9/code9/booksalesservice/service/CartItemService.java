package com.levi9.code9.booksalesservice.service;

import com.levi9.code9.booksalesservice.dto.AddedCartItemDto;
import com.levi9.code9.booksalesservice.dto.BookDto;

public interface CartItemService {

    AddedCartItemDto add(BookDto cartItemDto, Long bookDto);
}
