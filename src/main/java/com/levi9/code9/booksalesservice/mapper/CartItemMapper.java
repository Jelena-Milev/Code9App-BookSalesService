package com.levi9.code9.booksalesservice.mapper;

import com.levi9.code9.booksalesservice.dto.bookService.BookDto;
import com.levi9.code9.booksalesservice.dto.cart.CartItemDto;
import com.levi9.code9.booksalesservice.dto.cart.CartItemInfoDto;
import com.levi9.code9.booksalesservice.model.CartItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    CartItemEntity map(CartItemDto cartItemDto);

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "title")
    @Mapping(source = "book.price", target = "price")
    @Mapping(source = "book.author", target = "author")
    @Mapping(source = "savedCartItem.id", target = "id")
    @Mapping(source = "savedCartItem.quantity", target = "quantity")
    CartItemInfoDto mapToDto(CartItemEntity savedCartItem, BookDto book);
}
