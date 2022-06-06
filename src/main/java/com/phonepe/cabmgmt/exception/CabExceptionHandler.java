package com.phonepe.cabmgmt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CabExceptionHandler {

	@ExceptionHandler(value = CabNotAvailableException.class)
	 public ResponseEntity<Object> exception(CabNotAvailableException exception) {
		 return new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_FOUND);
	 }
	
	@ExceptionHandler(value = OperationNotAllwoedException.class)
	public ResponseEntity<Object> exception(OperationNotAllwoedException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = CityNotExistsException.class)
	public ResponseEntity<Object> exception(CityNotExistsException exception)
	{
		return new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_FOUND);
	}
}
