package ru.otus.processor.homework;

import java.time.LocalDateTime;

@FunctionalInterface
public interface DateTimeSupplier {

    LocalDateTime getDateTime();

}
