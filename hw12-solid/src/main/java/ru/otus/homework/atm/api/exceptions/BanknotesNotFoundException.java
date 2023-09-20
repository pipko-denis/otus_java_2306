package ru.otus.homework.atm.api.exceptions;

public class BanknotesNotFoundException extends RuntimeException{
    public BanknotesNotFoundException(String message) {
        super(message);
    }
}
