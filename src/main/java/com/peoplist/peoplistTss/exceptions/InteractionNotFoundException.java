package com.peoplist.peoplistTss.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class InteractionNotFoundException extends RuntimeException {
	public InteractionNotFoundException() {
        super();
    }
    public InteractionNotFoundException(String message) {
        super(message);
    }
}
