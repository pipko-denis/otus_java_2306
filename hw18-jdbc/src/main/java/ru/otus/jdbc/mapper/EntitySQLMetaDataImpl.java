package ru.otus.jdbc.mapper;

import lombok.RequiredArgsConstructor;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {

    private final EntityClassMetaData<T> entityClassMetaData;

    private String getSelectSql() {
        return "SELECT " +
                entityClassMetaData.getAllFields().stream()
                        .map(Field::getName)
                        .collect(Collectors.joining(",")) +
                " FROM " + entityClassMetaData.getName();
    }

    @Override
    public String getSelectAllSql() {
        return getSelectSql();
    }

    @Override
    public String getSelectByIdSql() {
        return getSelectSql() + " WHERE " + entityClassMetaData.getIdField().getName() + " = ?";
    }

    @Override
    public String getInsertSql() {
        List<Field> fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
        return new StringBuilder("INSERT INTO ")
                .append(entityClassMetaData.getName())
                .append(" (")
                .append(fieldsWithoutId.stream()
                        .map(Field::getName)
                        .collect(Collectors.joining(",")))
                .append(") values (")
                .append(fieldsWithoutId.stream()
                        .map((el) -> "?")
                        .collect(Collectors.joining(",")))
                .append(");")
                .toString();
    }

    @Override
    public String getUpdateSql() {
        return new StringBuilder("UPDATE ")
                .append(entityClassMetaData.getName())
                .append(" SET ")
                .append(entityClassMetaData.getFieldsWithoutId().stream()
                        .map((el) -> el.getName() + " = ?")
                        .collect(Collectors.joining(",")))
                .append(" WHERE ")
                .append(entityClassMetaData.getIdField().getName())
                .append(" = ?;")
                .toString();
    }
}
