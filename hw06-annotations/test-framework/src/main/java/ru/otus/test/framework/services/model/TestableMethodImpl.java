package ru.otus.test.framework.services.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.otus.test.framework.services.api.TestableMethod;

import java.lang.reflect.Method;

@Getter
@Setter
@RequiredArgsConstructor
public class TestableMethodImpl implements TestableMethod {

    private final Class<?> testClass;

    private final Method methodBefore;

    private final Method testMethod;

    private final Method methodAfter;

    boolean isSuccessful;

    Exception exception;

}
