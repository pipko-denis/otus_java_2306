package ru.otus.homework.atm.api.exceptions;

public class DenominationNotFoundException extends RuntimeException{
    public DenominationNotFoundException(String message) {
        super(message);
    }
}
