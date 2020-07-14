package com.levi9.code9.booksalesservice.service.impl;

import com.levi9.code9.booksalesservice.controller.BookServiceApi;
import com.levi9.code9.booksalesservice.dto.bookService.BookDto;
import com.levi9.code9.booksalesservice.dto.cart.CartItemDto;
import com.levi9.code9.booksalesservice.dto.cart.CartItemInfoDto;
import com.levi9.code9.booksalesservice.dto.cart.CartItemQuantityDto;
import com.levi9.code9.booksalesservice.mapper.CartItemMapper;
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
    private final BookServiceApi bookServiceApi;

    @Autowired
    public CartItemServiceImpl(CartItemRepository cartItemRepository, CartItemMapper cartItemMapper, BookServiceApi bookServiceApi) {
        this.cartItemRepository = cartItemRepository;
        this.cartItemMapper = cartItemMapper;
        this.bookServiceApi = bookServiceApi;
    }

    @Override
    public CartItemInfoDto add(CartItemDto cartItemDto, Long userId) {
        final BookDto book = bookServiceApi.getBook(cartItemDto.getBookId());
        if (!book.isOnStock()) {
            //throw exception
            return null;
        }
        if (book.getQuantityOnStock() < cartItemDto.getQuantity()) {
            //throw exception
            return null;
        }
        final CartItemEntity cartItemToSave = cartItemMapper.map(cartItemDto);
        cartItemToSave.setUserId(userId);
        final CartItemEntity savedCartItem = cartItemRepository.save(cartItemToSave);
        return cartItemMapper.mapToDto(savedCartItem, book);
    }

    @Override
    public List<CartItemInfoDto> getAll(Long userId) {
        List<CartItemEntity> cartItems = cartItemRepository.findByUserId(userId);
        if(cartItems == null || cartItems.isEmpty()){
            //vrati da je prazna
            return null;
        }
        final List<BookDto> books = fetchBooks(cartItems);
        List<CartItemInfoDto> cartItemsDtos = new ArrayList<>(cartItems.size());
        for (BookDto book : books) {
            final CartItemEntity cartItem = cartItems.stream().filter(item -> item.getBookId() == book.getId()).findFirst().get();
            final CartItemInfoDto cartItemInfoDto = cartItemMapper.mapToDto(cartItem, book);
            cartItemsDtos.add(cartItemInfoDto);
        }
        return cartItemsDtos;
    }

    private List<BookDto> fetchBooks(List<CartItemEntity> cartItems) {
        final List<Long> ids = new ArrayList<>(cartItems.size());
        cartItems.forEach(item -> ids.add(item.getBookId()));
        final List<BookDto> books = bookServiceApi.getBulkBooks(ids);
        return books;
    }

    //
    @Override
    public List<CartItemInfoDto> deleteAll(Long userId) {
        List<CartItemEntity> cartItems = cartItemRepository.findByUserId(userId);
        if(cartItems == null || cartItems.isEmpty()){
            return null;
        }
        List<BookDto> books = fetchBooks(cartItems);
        List<CartItemInfoDto> deletedItemDtos = new ArrayList<>(cartItems.size());
        for (CartItemEntity cartItem : cartItems) {
            final BookDto book = books.stream().filter(b -> b.getId() == cartItem.getBookId()).findFirst().get();
            deletedItemDtos.add(cartItemMapper.mapToDto(cartItem, book));
            cartItemRepository.delete(cartItem);
        }
        return deletedItemDtos;
    }

    @Override
    public CartItemInfoDto delete(Long bookId, Long userId) {
        final CartItemEntity cartItem = cartItemRepository.findByBookIdAndUserId(bookId, userId);
        final BookDto book = bookServiceApi.getBook(cartItem.getBookId());
        final CartItemInfoDto cartItemInfoDto = cartItemMapper.mapToDto(cartItem, book);
        cartItemRepository.delete(cartItem);
        return cartItemInfoDto;
    }

    @Override
    public CartItemInfoDto updateQuantity(Long bookId, CartItemQuantityDto newQuantityDto, Long userId) {
        final BookDto book = bookServiceApi.getBook(bookId);
        if(book.getQuantityOnStock() < newQuantityDto.getNewQuantity()){
            //throw exception
            return null;
        }
        final CartItemEntity cartItem = cartItemRepository.findByBookIdAndUserId(book.getId(), userId);
        cartItem.setQuantity(newQuantityDto.getNewQuantity());
        final CartItemEntity updatedCartItem = cartItemRepository.save(cartItem);
        final CartItemInfoDto updatedCartItemInfo = cartItemMapper.mapToDto(updatedCartItem, book);
        return updatedCartItemInfo;
    }
}
