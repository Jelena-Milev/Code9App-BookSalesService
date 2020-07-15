package com.levi9.code9.booksalesservice.exception;

public class BookQuantityNotOnStockException extends RuntimeException{

    public BookQuantityNotOnStockException(Long id) {
        super("There is not enough of book with id "+id+" on stock");
    }
}
