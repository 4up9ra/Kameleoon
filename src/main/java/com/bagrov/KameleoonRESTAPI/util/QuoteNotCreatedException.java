package com.bagrov.KameleoonRESTAPI.util;

public class QuoteNotCreatedException extends RuntimeException{
    public QuoteNotCreatedException(String message)  {
        super(message);
    }
}
