package com.levi9.code9.booksalesservice.controller;

import com.levi9.code9.booksalesservice.dto.bookService.BookDto;
import com.levi9.code9.booksalesservice.dto.cart.CartItemDto;
import com.levi9.code9.booksalesservice.exception.OperationNotAvailableException;

import java.util.List;

public class BookServiceApiFallback implements BookServiceApi {

    public static final String UNKNOWN = "Unknown";

    @Override
    public BookDto getBook(final Long id) {
        throw new OperationNotAvailableException();
    }

    @Override
    public List<BookDto> getBulkBooks(List<Long> ids) {
        return null;
    }

    @Override
    public List<BookDto> updateCopiesSold(List<CartItemDto> cartItemsSold) {
        return null;
    }


}
