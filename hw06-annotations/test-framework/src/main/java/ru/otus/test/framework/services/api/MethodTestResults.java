package ru.otus.test.framework.services.api;

import java.lang.reflect.Method;

public interface MethodTestResults {

    Method getMethod();

    boolean isSuccessful();

    Exception getException();

}
