package ru.otus.homework.atm.api.exceptions;

public class RemoveBanknotesException extends RuntimeException{

    public RemoveBanknotesException(String message) {
        super(message);
    }
}
