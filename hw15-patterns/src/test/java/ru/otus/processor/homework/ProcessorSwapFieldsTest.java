package ru.otus.processor.homework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.Processor;

class ProcessorSwapFieldsTest {

    @Test
    @DisplayName("Процессор меняет поля 10 и 11 местами и не бросает исключения")
    void processMessageSwapFieldsDoesNotTrowException() {
        //given
        String expectedF11value = "field11value";
        String expectedF12value = "field12value";
        var message = new Message.Builder(1L).field11(expectedF12value).field12(expectedF11value).field13(new ObjectForMessage()).build();
        Processor processor = new ProcessorSwapFields();

        //when
        Message actual = Assertions.assertDoesNotThrow(() -> processor.process(message));

        //assert
        Assertions.assertEquals(actual.getField11(), expectedF11value);
        Assertions.assertEquals(actual.getField12(), expectedF12value);
    }

}