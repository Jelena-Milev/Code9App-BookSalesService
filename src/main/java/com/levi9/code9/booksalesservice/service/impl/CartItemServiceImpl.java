package com.levi9.code9.booksalesservice.service.impl;

import com.levi9.code9.booksalesservice.controller.BookServiceApi;
import com.levi9.code9.booksalesservice.dto.bookService.BookDto;
import com.levi9.code9.booksalesservice.dto.cart.CartItemDto;
import com.levi9.code9.booksalesservice.dto.cart.CartItemInfoDto;
import com.levi9.code9.booksalesservice.dto.cart.CartItemQuantityDto;
import com.levi9.code9.booksalesservice.exception.BookIsNotForSaleException;
import com.levi9.code9.booksalesservice.exception.BookQuantityNotOnStockException;
import com.levi9.code9.booksalesservice.exception.ObjectNotFoundException;
import com.levi9.code9.booksalesservice.mapper.CartItemMapper;
import com.levi9.code9.booksalesservice.model.CartItemEntity;
import com.levi9.code9.booksalesservice.repository.CartItemRepository;
import com.levi9.code9.booksalesservice.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        final CartItemEntity cartItemToSave = cartItemMapper.map(cartItemDto);

        final BookDto book = bookServiceApi.getBook(cartItemDto.getBookId());
        if (!book.isOnStock()) {
            throw new BookIsNotForSaleException(cartItemDto.getBookId());
        }
        if (book.getQuantityOnStock() < cartItemDto.getQuantity()) {
            throw new BookQuantityNotOnStockException(cartItemDto.getBookId());
        }
        final Optional<CartItemEntity> cartItem = cartItemRepository.findByBookIdAndUserId(cartItemDto.getBookId(), userId);
        if(cartItem.isPresent()){
            return updateExistingCartItemQuantity(book, cartItemDto.getQuantity(), cartItem.get());
        }
        cartItemToSave.setUserId(userId);
        final CartItemEntity savedCartItem = cartItemRepository.save(cartItemToSave);
        return cartItemMapper.mapToDto(savedCartItem, book);
    }

    @Override
    public List<CartItemInfoDto> getAll(Long userId) {
        List<CartItemEntity> cartItems = cartItemRepository.findByUserId(userId);
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

    @Override
    public List<CartItemInfoDto> deleteAll(Long userId) {
        List<CartItemEntity> cartItems = cartItemRepository.findByUserId(userId);
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
        final CartItemEntity cartItem = findCartItem(bookId, userId);
        final BookDto book = bookServiceApi.getBook(cartItem.getBookId());
        final CartItemInfoDto cartItemInfoDto = cartItemMapper.mapToDto(cartItem, book);
        cartItemRepository.delete(cartItem);
        return cartItemInfoDto;
    }

    private CartItemEntity findCartItem(Long bookId, Long userId){
        Optional<CartItemEntity> optionalCartItemEntity = cartItemRepository.findByBookIdAndUserId(bookId, userId);
        optionalCartItemEntity.orElseThrow(() -> new ObjectNotFoundException("CartItem with book id "+bookId+" not found"));
        return optionalCartItemEntity.get();
    }

    @Override
    public CartItemInfoDto updateQuantity(Long bookId, CartItemQuantityDto newQuantityDto, Long userId) {
        final BookDto book = bookServiceApi.getBook(bookId);
        final Long newQuantity = newQuantityDto.getNewQuantity();
        final CartItemEntity cartItem = findCartItem(book.getId(), userId);

        if (!book.isOnStock()) {
            throw new BookIsNotForSaleException(bookId);
        }
        if (book.getQuantityOnStock() < newQuantity) {
            throw new BookQuantityNotOnStockException(bookId);
        }
        cartItem.setQuantity(newQuantity);
        final CartItemEntity updatedCartItem = cartItemRepository.save(cartItem);
        return cartItemMapper.mapToDto(updatedCartItem, book);
    }

    private CartItemInfoDto updateExistingCartItemQuantity(BookDto book, Long newQuantity, CartItemEntity cartItem) {
        final Long newItemQuantity = newQuantity + cartItem.getQuantity();
        cartItem.setQuantity(newItemQuantity);
        final CartItemEntity updatedCartItem = cartItemRepository.save(cartItem);
        return cartItemMapper.mapToDto(updatedCartItem, book);
    }
}
