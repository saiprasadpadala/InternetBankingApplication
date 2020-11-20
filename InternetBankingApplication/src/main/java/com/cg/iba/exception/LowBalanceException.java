package com.cg.iba.exception;

public class LowBalanceException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public LowBalanceException() {
        super();
    }

    public LowBalanceException(String message) {
        super(message);
    }
    

}
