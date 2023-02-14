package com.bagrov.KameleoonRESTAPI.util;

public class UserNotCreatedException extends RuntimeException{
    public UserNotCreatedException(String message)  {
        super(message);
    }
}
