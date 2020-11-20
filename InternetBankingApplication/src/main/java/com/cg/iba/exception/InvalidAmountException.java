package com.cg.iba.exception;

public class InvalidAmountException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public InvalidAmountException() {
        super();
    }

    public InvalidAmountException(String message) {
        super(message);
    }
    

}
