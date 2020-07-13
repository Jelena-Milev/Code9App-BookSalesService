package com.levi9.code9.booksalesservice.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.levi9.code9.booksalesservice.dto.bookService.BookDto;
import com.levi9.code9.booksalesservice.dto.cart.AddedCartItemDto;
import com.levi9.code9.booksalesservice.dto.cart.CartItemDto;
import com.levi9.code9.booksalesservice.dto.cart.CartItemInfoDto;
import com.levi9.code9.booksalesservice.dto.cart.CartItemQuantityDto;
import com.levi9.code9.booksalesservice.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "cart")
public class CartController {

    private final CartItemService cartItemService;
    private final BookServiceApi bookServiceApi;

    @Autowired
    public CartController(CartItemService cartItemService, BookServiceApi bookServiceApi) {
        this.cartItemService = cartItemService;
        this.bookServiceApi = bookServiceApi;
    }

    @GetMapping(path = "", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity checkout() {
        final List<CartItemInfoDto> cartItems = cartItemService.getAll(3l);
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    @PostMapping(path = "/items", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AddedCartItemDto> add(@RequestBody final CartItemDto cartItemDto) {
        final Long bookId = cartItemDto.getBookId();
        final Long quantity = cartItemDto.getQuantity();
        final BookDto bookDto = bookServiceApi.getBook(bookId);
        final AddedCartItemDto addedItem = cartItemService.add(bookDto, quantity, 3l);
        return new ResponseEntity<>(addedItem, HttpStatus.OK);
    }

    @DeleteMapping(path = "/items", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity deleteAll() {
        final List<CartItemInfoDto> deletedItems = cartItemService.deleteAll(3l);
        return new ResponseEntity<>(deletedItems, HttpStatus.OK);
    }

    @DeleteMapping(path = "/items/{bookId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable final Long bookId) {
        final CartItemInfoDto deletedItem = cartItemService.delete(bookId, 3l);
        return new ResponseEntity<>(deletedItem, HttpStatus.OK);
    }

    @PatchMapping(path = "/items/{bookId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity updateQuantity(@PathVariable final Long bookId, @RequestBody final CartItemQuantityDto newQuantityDto) {
        final BookDto bookDto = bookServiceApi.getBook(bookId);
        final CartItemInfoDto updatedItem = cartItemService.updateQuantity(bookDto, newQuantityDto, 3l);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }
}
