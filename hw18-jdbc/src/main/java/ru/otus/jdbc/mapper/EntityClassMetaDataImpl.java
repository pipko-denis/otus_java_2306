package ru.otus.jdbc.mapper;

import lombok.RequiredArgsConstructor;
import ru.otus.jdbc.api.Column;
import ru.otus.jdbc.api.Id;
import ru.otus.jdbc.api.Table;
import ru.otus.jdbc.exceptions.ConstructorNotFoundException;
import ru.otus.jdbc.exceptions.FieldNotFoundException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> classType;

    @Override
    public String getName() {
        return Optional.ofNullable(classType.getDeclaredAnnotation(Table.class))
                .map(Table::name)
                .orElse(classType.getSimpleName().toLowerCase());
    }

    @Override
    public Constructor<T> getConstructor() {
        try {
            return classType.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new ConstructorNotFoundException(e);
        }
    }

    @Override
    public Field getIdField() {
        return Arrays.stream(classType.getDeclaredFields())
                .filter(field -> (field.getDeclaredAnnotation(Id.class) != null))
                .findFirst()
                .orElseThrow(() -> new FieldNotFoundException("Field with marked by \"@Id\" annotation wasn't found in class "+classType.getName()));
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.stream(classType.getDeclaredFields())
                .filter(field -> (field.getDeclaredAnnotation(Column.class) != null))
                .collect(Collectors.toList());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return Arrays.stream(classType.getDeclaredFields())
                .filter(field -> (field.getAnnotation(Id.class) == null))
                .filter(field -> (field.getAnnotation(Column.class) != null))
                .collect(Collectors.toList());
    }
}
