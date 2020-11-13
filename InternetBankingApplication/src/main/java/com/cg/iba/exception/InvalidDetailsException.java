package com.cg.iba.exception;

public class InvalidDetailsException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidDetailsException(String message) {
        super(message);
    }
}
