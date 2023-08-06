package ru.otus.test.framework.services.exceptions;

public class InvokeMethodException extends RuntimeException{

    public InvokeMethodException(String message) {
        super(message);
    }

    public InvokeMethodException(String message, Throwable cause) {
        super(message, cause);
    }
}
