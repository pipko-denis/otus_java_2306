package ru.otus.homework.atm.api.exceptions;

public class NotEnoughBanknotesException extends RuntimeException{
    public NotEnoughBanknotesException(String message) {
        super(message);
    }
}
