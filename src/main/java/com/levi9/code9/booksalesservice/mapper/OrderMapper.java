package com.levi9.code9.booksalesservice.mapper;

import com.levi9.code9.booksalesservice.dto.order.OrderDto;
import com.levi9.code9.booksalesservice.dto.order.OrderItemDto;
import com.levi9.code9.booksalesservice.model.OrderEntity;
import com.levi9.code9.booksalesservice.model.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto mapToDto(OrderEntity orderEntity);

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.author", target = "author")
    @Mapping(source = "book.title", target = "title")
    @Mapping(source = "book.price", target = "price")
    OrderItemDto map(OrderItemEntity orderItemEntity);
}
