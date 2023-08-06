package ru.otus.test.framework.services.exceptions;

public class TestInstanceCreateException extends RuntimeException{

    public TestInstanceCreateException(String message, Throwable cause) {
        super(message, cause);
    }
}
