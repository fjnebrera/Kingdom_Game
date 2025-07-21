package com.tecnocampus.kingdom_game.utilities;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends DomainException {
    public NotFoundException(String msg) {
        super(msg);
    }
}