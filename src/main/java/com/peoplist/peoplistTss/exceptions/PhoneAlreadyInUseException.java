package com.peoplist.peoplistTss.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PhoneAlreadyInUseException extends RuntimeException  {
	public PhoneAlreadyInUseException() {
        super();
    }
    public PhoneAlreadyInUseException(String message) {
        super(message);
    }
}
