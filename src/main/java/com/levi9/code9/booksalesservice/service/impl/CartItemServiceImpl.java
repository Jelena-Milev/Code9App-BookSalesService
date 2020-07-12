package com.levi9.code9.booksalesservice.service.impl;

import com.levi9.code9.booksalesservice.dto.bookService.BookDto;
import com.levi9.code9.booksalesservice.dto.cart.AddedCartItemDto;
import com.levi9.code9.booksalesservice.dto.cart.CartItemInfoDto;
import com.levi9.code9.booksalesservice.dto.cart.CartItemQuantityDto;
import com.levi9.code9.booksalesservice.mapper.BookMapper;
import com.levi9.code9.booksalesservice.mapper.CartItemMapper;
import com.levi9.code9.booksalesservice.model.book.BookEntity;
import com.levi9.code9.booksalesservice.model.CartItemEntity;
import com.levi9.code9.booksalesservice.repository.CartItemRepository;
import com.levi9.code9.booksalesservice.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public AddedCartItemDto add(BookDto bookDto, Long quantity, Long userId) {
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
        cartItemEntity.userId(userId)
                .book(bookEntity)
                .quantity(quantity);
        final CartItemEntity cartItemToSave = cartItemEntity.build();
        final CartItemEntity savedCartItem = cartItemRepository.save(cartItemToSave);
        return cartItemMapper.mapToDto(savedCartItem);
    }

    @Override
    public List<CartItemInfoDto> getAll(Long userId) {
        List<CartItemEntity> cartItems = cartItemRepository.findByUserId(userId);
        List<CartItemInfoDto> cartItemsDtos = new ArrayList<>(cartItems.size());
        cartItems.forEach(item -> cartItemsDtos.add(cartItemMapper.mapToInfoDto(item)));
        return cartItemsDtos;
    }

    @Override
    public List<CartItemInfoDto> deleteAll(Long userId) {
        List<CartItemEntity> cartItems = cartItemRepository.findByUserId(userId);
        List<CartItemInfoDto> cartItemsDtos = new ArrayList<>(cartItems.size());
        for (CartItemEntity cartItem : cartItems) {
            cartItemsDtos.add(cartItemMapper.mapToInfoDto(cartItem));
            cartItemRepository.delete(cartItem);
        }
        return cartItemsDtos;
    }

    @Override
    public CartItemInfoDto delete(Long bookId, Long userId) {
        final CartItemEntity cartItem = cartItemRepository.findByBookIdAndUserId(bookId, userId);
        final CartItemInfoDto cartItemInfoDto = cartItemMapper.mapToInfoDto(cartItem);
        cartItemRepository.delete(cartItem);
        return cartItemInfoDto;
    }

    @Override
    public CartItemInfoDto updateQuantity(BookDto book, CartItemQuantityDto newQuantityDto, Long userId) {
        if(book.getQuantityOnStock() < newQuantityDto.getNewQuantity()){
            //throw exception
            return null;
        }
        final CartItemEntity cartItem = cartItemRepository.findByBookIdAndUserId(book.getId(), userId);
        cartItem.setQuantity(newQuantityDto.getNewQuantity());
        final CartItemEntity updatedCartItem = cartItemRepository.save(cartItem);
        final CartItemInfoDto updatedCartItemInfo = cartItemMapper.mapToInfoDto(updatedCartItem);
        return updatedCartItemInfo;
    }
}
