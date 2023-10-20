package ru.otus.jdbc.exceptions;

public class ConstructorNotFoundException extends RuntimeException {
    public ConstructorNotFoundException(Throwable cause) {
        super(cause);
    }
}
