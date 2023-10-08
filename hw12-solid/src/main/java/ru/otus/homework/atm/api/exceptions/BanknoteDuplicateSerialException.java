package ru.otus.homework.atm.api.exceptions;

public class BanknoteDuplicateSerialException extends RuntimeException{
    public BanknoteDuplicateSerialException(String message) {
        super(message);
    }
}
