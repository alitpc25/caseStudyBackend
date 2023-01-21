package com.peoplist.peoplistTss.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmailAlreadyInUseException extends RuntimeException {
	public EmailAlreadyInUseException() {
        super();
    }
    public EmailAlreadyInUseException(String message) {
        super(message);
    }
}
