package com.levi9.code9.booksalesservice.exception;

public class OperationNotAvailableException extends RuntimeException{

    public OperationNotAvailableException() {
        super("Requested operation is not available at the moment. Please try again later.");
    }
}
