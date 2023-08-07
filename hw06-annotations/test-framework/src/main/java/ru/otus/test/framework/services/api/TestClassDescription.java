package ru.otus.test.framework.services.api;

import java.lang.reflect.Method;
import java.util.List;

public interface TestClassDescription {

    Class<?> getTestClass();

    Method getBeforeMethod();

    List<Method> getTestMethods();

    Method getAfterMethod();

}
