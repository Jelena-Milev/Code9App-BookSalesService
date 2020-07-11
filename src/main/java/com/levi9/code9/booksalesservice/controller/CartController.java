package com.levi9.code9.booksalesservice.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.levi9.code9.booksalesservice.dto.AddedCartItemDto;
import com.levi9.code9.booksalesservice.dto.BookCopiesSoldDto;
import com.levi9.code9.booksalesservice.dto.BookDto;
import com.levi9.code9.booksalesservice.dto.CartItemDto;
import com.levi9.code9.booksalesservice.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


        return null;
    }

    @PostMapping(path = "/items", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AddedCartItemDto> add(@RequestBody final CartItemDto cartItemDto) {
        final BookDto bookDto = bookServiceApi.getBook(cartItemDto.getBookId());
        AddedCartItemDto savedCartItem = cartItemService.add(bookDto, cartItemDto.getQuantity());
//        final BookDto updatedQuantityBook = reduceQuantityOnStock(savedCartItem);
        return new ResponseEntity<>(savedCartItem, HttpStatus.OK);
    }

    @DeleteMapping(path = "/items", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity deleteAll() {
        return null;
    }

    @DeleteMapping(path = "/items/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable final Long id) {
        return null;
    }

    @PatchMapping(path = "/items/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity updateQuantity(@PathVariable final Long id, @RequestBody final Long newQuantity) {
        return null;
    }
}
