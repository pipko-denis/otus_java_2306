package ru.otus.test.framework.services.exceptions;

public class InvalidTestClassException extends RuntimeException{

    public InvalidTestClassException(String message) {
        super(message);
    }
}
