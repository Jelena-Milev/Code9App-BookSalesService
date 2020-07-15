package com.levi9.code9.booksalesservice.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.levi9.code9.booksalesservice.dto.bookService.BookDto;
import com.levi9.code9.booksalesservice.dto.cart.CartItemDto;
import com.levi9.code9.booksalesservice.dto.cart.CartItemInfoDto;
import com.levi9.code9.booksalesservice.dto.cart.CartItemQuantityDto;
import com.levi9.code9.booksalesservice.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
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
        final Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final List<CartItemInfoDto> cartItems = cartItemService.getAll(userId);
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    @PostMapping(path = "/items", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CartItemInfoDto> add(@RequestBody final @Valid CartItemDto cartItemDto) {
        final Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final CartItemInfoDto addedItem = cartItemService.add(cartItemDto, userId);
        return new ResponseEntity<>(addedItem, HttpStatus.OK);
    }

    @DeleteMapping(path = "/items", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity deleteAll() {
        final Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final List<CartItemInfoDto> deletedItems = cartItemService.deleteAll(userId);
        return new ResponseEntity<>(deletedItems, HttpStatus.OK);
    }

    @DeleteMapping(path = "/items/{bookId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable final @Positive(message = "Book id must be valid") Long bookId) {
        final Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final CartItemInfoDto deletedItem = cartItemService.delete(bookId, userId);
        return new ResponseEntity<>(deletedItem, HttpStatus.OK);
    }

    @PatchMapping(path = "/items/{bookId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity updateQuantity(@PathVariable final @Positive(message = "Book id must be valid") Long bookId, @RequestBody final CartItemQuantityDto newQuantityDto) {
        final Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final CartItemInfoDto updatedItem = cartItemService.updateQuantity(bookId, newQuantityDto, userId);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }
}
