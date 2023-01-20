package com.peoplist.peoplistTss.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CandidateNotFoundException extends RuntimeException {
	
	public CandidateNotFoundException() {
        super();
    }
    public CandidateNotFoundException(String message) {
        super(message);
    }
}
