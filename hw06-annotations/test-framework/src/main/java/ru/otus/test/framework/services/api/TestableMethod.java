package ru.otus.test.framework.services.api;

import java.lang.reflect.Method;

public interface TestableMethod {

    Class<?> getTestClass();

    Method getMethodBefore();

    Method getTestMethod();

    Method getMethodAfter();

    boolean isSuccessful();

    Exception getException();

}
