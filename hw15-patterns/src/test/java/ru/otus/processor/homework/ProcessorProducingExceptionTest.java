package ru.otus.processor.homework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.processor.homework.exceptions.EvenSecondException;

import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProcessorProducingExceptionTest {

    @Test
    void processThrowsExceptionOnEvenSecond() {
        //given
        var dateTimeSupplier = mock(DateTimeSupplier.class);
        var processor = new ProcessorProducingException(dateTimeSupplier);
        var message = new Message.Builder(1L).build();

        //when
        when(dateTimeSupplier.getDateTime()).thenReturn(LocalDateTime.now().withSecond(2));

        //assert
        Assertions.assertThrows(EvenSecondException.class, () -> processor.process(message));
    }

    @Test
    void processDoesNotThrowsExceptionOnOddSecond() {
        //given
        var dateTimeSupplier = mock(DateTimeSupplier.class);
        var processor = new ProcessorProducingException(dateTimeSupplier);
        var message = new Message.Builder(1L).build();

        //when
        when(dateTimeSupplier.getDateTime()).thenReturn(LocalDateTime.now().withSecond(1));

        //assert
        Assertions.assertDoesNotThrow(() -> processor.process(message));
    }

}