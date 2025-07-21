package com.tecnocampus.kingdom_game.utilities;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InvalidValuesException extends DomainException{
    public InvalidValuesException(String msg){
        super(msg);
    }
}
