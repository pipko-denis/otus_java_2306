package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;
import ru.otus.processor.homework.exceptions.EvenSecondException;

import java.time.LocalDateTime;

public class ProcessorProducingException implements Processor {

    private final DateTimeSupplier dateTimeSupplier;

    public ProcessorProducingException(DateTimeSupplier dateTimeSupplier) {
        this.dateTimeSupplier = dateTimeSupplier;
    }

    @Override
    public Message process(Message message) {
        LocalDateTime now = dateTimeSupplier.getDateTime();
        if ((now.getSecond() % 2) == 0) {
            throw new EvenSecondException("Exception thrown because of even second ("+now+") ");
        }
        return message;
    }
}
