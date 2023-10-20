package ru.otus.jdbc.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.crm.model.Client;
import ru.otus.jdbc.api.Column;
import ru.otus.jdbc.api.Id;
import ru.otus.jdbc.api.Table;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class EntityClassMetaDataImplTest {

    @Test
    void getNameReturnCorrectClassName() {
        //given
        class TestClassNoAnnotation {
        }
        @Table(name = "test_table")
        class TabledTestClass {
        }

        //assertions
        Assertions.assertEquals("testclassnoannotation", new EntityClassMetaDataImpl<>(TestClassNoAnnotation.class).getName());
        Assertions.assertEquals("test_table", new EntityClassMetaDataImpl<>(TabledTestClass.class).getName());
    }

    @Test
    void getConstructorDoesNotThrowExceptionAndReturnConstructor() {
        //assertions
        Constructor<Client> clientConstructor = assertDoesNotThrow(() -> new EntityClassMetaDataImpl<>(Client.class).getConstructor());
        Assertions.assertNotNull(clientConstructor);
    }

    @Test
    void getAllFieldsDoesNotTrowExceptionAndReturnCorrectFieldsCount() {
        //given
        class TestClass {
            @Id
            @Column
            Integer Id;
            @Column
            String name;
            @Column
            String label;
        }

        //assertions
        int actualFieldsCount = assertDoesNotThrow(() -> new EntityClassMetaDataImpl<>(TestClass.class).getAllFields()).size();
        Assertions.assertEquals(3, actualFieldsCount);

    }

}