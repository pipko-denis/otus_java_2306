package ru.otus.jdbc.exceptions;

import java.sql.SQLException;

public class HandleDatabaseResponceException extends RuntimeException {
    public HandleDatabaseResponceException(Throwable cause) {
        super(cause);
    }

    public HandleDatabaseResponceException(String message) {
        super(message);
    }
}
