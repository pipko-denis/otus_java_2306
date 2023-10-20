package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.jdbc.exceptions.CreateNewInstanceException;
import ru.otus.jdbc.exceptions.HandleDatabaseResponceException;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
@SuppressWarnings("java:S1068")
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    private T getNewDataInstance() {
        try {
            return entityClassMetaData.getConstructor().newInstance();
        } catch (Exception e) {
            throw new CreateNewInstanceException(e);
        }
    }

    private T singleResultHandler(ResultSet rs, List<Field> allFields) {
        T result = getNewDataInstance();
        for (Field field : allFields) {
            try {
                String fieldName = field.getName();
                field.setAccessible(true);
                field.set(result, rs.getObject(fieldName));
            } catch (Exception e) {
                throw new HandleDatabaseResponceException(e);
            }
        }
        return result;
    }

    private List<Object> getParamsList(T entity, List<Field> fields) {
        return fields.stream().map(field -> {
                    try {
                        field.setAccessible(true);
                        return field.get(entity);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), resultSet -> {
            try {
                if (resultSet.next()) {
                    return singleResultHandler(resultSet, entityClassMetaData.getAllFields());
                }
                return null;
            } catch (Exception e) {
                throw new HandleDatabaseResponceException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), List.of(), resultSet -> {
                    try {
                        var clientList = new ArrayList<T>();
                        while (resultSet.next()) {
                            clientList.add(singleResultHandler(resultSet, entityClassMetaData.getAllFields()));
                        }
                        return clientList;
                    } catch (Exception e) {
                        throw new HandleDatabaseResponceException(e);
                    }
                })
                .orElseThrow(
                        () -> new HandleDatabaseResponceException("Unexpected exception while getting all from \"" + entityClassMetaData.getName()+"\"")
                );
    }

    @Override
    public long insert(Connection connection, T entity) {
        return dbExecutor.executeStatement(connection,
                entitySQLMetaData.getInsertSql(),
                getParamsList(entity, entityClassMetaData.getFieldsWithoutId()));
    }

    @Override
    public void update(Connection connection, T entity) {
        List<Field> fields = Stream
                .concat(entityClassMetaData.getFieldsWithoutId().stream(), Stream.of(entityClassMetaData.getIdField()))
                .collect(Collectors.toList());
        dbExecutor.executeStatement(connection,
                entitySQLMetaData.getUpdateSql(),
                getParamsList(entity, fields));
    }
}
