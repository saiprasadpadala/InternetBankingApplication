package com.cg.iba.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(InvalidDetailsException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> resouceNotFoundException(InvalidDetailsException exception, WebRequest request) {
    	ExceptionDetails exceptionDetails = new ExceptionDetails(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(DetailsNotFoundException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> resouceNotFoundException(DetailsNotFoundException exception, WebRequest request) {
    	ExceptionDetails exceptionDetails = new ExceptionDetails(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(EmptyListException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> resouceNotFoundException(EmptyListException exception, WebRequest request) {
    	ExceptionDetails exceptionDetails = new ExceptionDetails(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(Exception.class)
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> globleExcpetionHandler(Exception ex, WebRequest request) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
