package com.inventory.sys.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceExistsException extends Exception{
    private static final long serialVersionUID = 1L;

    public ResourceExistsException(String message){
        super(message);
    }
}
