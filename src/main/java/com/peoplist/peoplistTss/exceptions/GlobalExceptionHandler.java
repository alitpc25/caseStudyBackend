package com.peoplist.peoplistTss.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value = { CandidateNotFoundException.class })
    protected ResponseEntity<Object> handleCandidateNotFoundException(CandidateNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(value = { EmailAlreadyInUseException.class })
    protected ResponseEntity<Object> handleEmailAlreadyInUseException(EmailAlreadyInUseException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(value = { PhoneAlreadyInUseException.class })
    protected ResponseEntity<Object> handlePhoneAlreadyInUseException(PhoneAlreadyInUseException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(value = { InteractionNotFoundException.class })
    protected ResponseEntity<Object> handleInteractionNotFoundException(InteractionNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
