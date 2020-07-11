package com.levi9.code9.booksalesservice.service.impl;

import com.levi9.code9.booksalesservice.dto.AddedCartItemDto;
import com.levi9.code9.booksalesservice.dto.BookDto;
import com.levi9.code9.booksalesservice.mapper.BookMapper;
import com.levi9.code9.booksalesservice.mapper.CartItemMapper;
import com.levi9.code9.booksalesservice.model.BookEntity;
import com.levi9.code9.booksalesservice.model.CartItemEntity;
import com.levi9.code9.booksalesservice.repository.CartItemRepository;
import com.levi9.code9.booksalesservice.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final BookMapper bookMapper;

    @Autowired
    public CartItemServiceImpl(CartItemRepository cartItemRepository, CartItemMapper cartItemMapper, BookMapper bookMapper) {
        this.cartItemRepository = cartItemRepository;
        this.cartItemMapper = cartItemMapper;
        this.bookMapper = bookMapper;
    }

    @Override
    public AddedCartItemDto add(BookDto bookDto, Long quantity) {
        //treba da proverim da li je na prodaju i da li je ima na stanju bar koliko je qunatity
        if(!bookDto.isOnStock()){
            //throw exception
            return null;
        }
        if(bookDto.getQuantityOnStock() < quantity){
            //throw exception
            return null;
        }
        BookEntity bookEntity = bookMapper.map(bookDto);
        final CartItemEntity.CartItemEntityBuilder cartItemEntity = CartItemEntity.builder();
        cartItemEntity.userId(3l)
                .book(bookEntity)
                .quantity(quantity);
        final CartItemEntity cartItemToSave = cartItemEntity.build();
        final CartItemEntity savedCartItem = cartItemRepository.save(cartItemToSave);
        return cartItemMapper.mapToDto(savedCartItem);
    }
}
