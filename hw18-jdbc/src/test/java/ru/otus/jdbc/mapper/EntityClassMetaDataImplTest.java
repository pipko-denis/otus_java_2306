package ru.otus.jdbc.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Manager;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class EntityClassMetaDataImplTest {

    @Test
    void getNameDoesNotThrowException() {
        //assertions
        Assertions.assertDoesNotThrow(() -> new EntityClassMetaDataImpl<>(Client.class).getName());
        Assertions.assertDoesNotThrow(() -> new EntityClassMetaDataImpl<>(Manager.class).getName());
    }

    @Test
    void getConstructorDoesNotThrowExceptionAndReturnConstructor() {
        //assertions
        Constructor<Client> clientConstructor = assertDoesNotThrow(() -> new EntityClassMetaDataImpl<>(Client.class).getConstructor());
        Assertions.assertNotNull(clientConstructor);
    }

    @Test
    void getAllFieldsDoesNotTrowExceptionAndReturnCorrectFieldsCount() {
        //assertions
        int actualFieldsCount = assertDoesNotThrow(() -> new EntityClassMetaDataImpl<>(Client.class).getAllFields()).size();
        Assertions.assertTrue(actualFieldsCount > 0);
    }

}