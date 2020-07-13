package com.levi9.code9.booksalesservice.mapper;

import com.levi9.code9.booksalesservice.dto.cart.AddedCartItemDto;
import com.levi9.code9.booksalesservice.dto.cart.CartItemInfoDto;
import com.levi9.code9.booksalesservice.model.CartItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "title")
    @Mapping(source = "book.price", target = "price")
    @Mapping(source = "book.author", target = "author")
    AddedCartItemDto mapToDto(CartItemEntity cartItemEntity);


    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "title")
    @Mapping(source = "book.price", target = "price")
    @Mapping(source = "book.author", target = "author")
    CartItemInfoDto mapToInfoDto(CartItemEntity cartItemEntity);
}
