package com.clay.sh.identityserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ClientRegistrationException extends RuntimeException {

    public ClientRegistrationException(String message) {
        super(message);
    }

    public ClientRegistrationException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
    }
}