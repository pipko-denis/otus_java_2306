package ru.otus.jdbc.mapper;

import lombok.RequiredArgsConstructor;
import ru.otus.jdbc.api.Column;
import ru.otus.jdbc.api.Id;
import ru.otus.jdbc.api.Table;
import ru.otus.jdbc.exceptions.RetriveNoArgConstructorException;
import ru.otus.jdbc.exceptions.FieldNotFoundException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final String tableName;

    private final Constructor<T> constructor;

    private final Field idField;

    private final List<Field> allFields;

    private final List<Field> fieldsWithoutId;


    public EntityClassMetaDataImpl(Class<T> classType) {
        this.tableName = Optional.ofNullable(classType.getDeclaredAnnotation(Table.class))
                .map(Table::name)
                .orElse(classType.getSimpleName().toLowerCase());

        try {
            this.constructor = classType.getDeclaredConstructor();
        } catch (Exception e) {
            throw new RetriveNoArgConstructorException(e);
        }

        this.allFields = getAllFieldsLocal(classType);
        this.idField = getIdFieldLocal(classType);
        this.fieldsWithoutId = getAllFieldsWithoutIdLocal(classType);
    }

    @Override
    public String getName() {
        return this.tableName;
    }

    @Override
    public Constructor<T> getConstructor() {
        return this.constructor;
    }

    @Override
    public Field getIdField() {
        return this.idField;
    }

    @Override
    public List<Field> getAllFields() {
        return this.allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return this.fieldsWithoutId;
    }

    private List<Field> getAllFieldsLocal(Class<T> classType) {
        return Arrays.stream(classType.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class) || field.isAnnotationPresent(Id.class))
                .toList();
    }

    private Field getIdFieldLocal(Class<T> classType) {
        return Arrays.stream(classType.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new FieldNotFoundException("Field with marked by \"@Id\" annotation wasn't found for table \""+this.tableName+"\""));
    }

    private List<Field> getAllFieldsWithoutIdLocal(Class<T> classType) {
        return Arrays.stream(classType.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .filter(field -> field.isAnnotationPresent(Column.class))
                .toList();
    }
}
