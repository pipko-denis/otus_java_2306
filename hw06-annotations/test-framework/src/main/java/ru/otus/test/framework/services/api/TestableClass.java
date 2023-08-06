package ru.otus.test.framework.services.api;

import java.util.List;

public interface TestableClass {

    Class<?> getTestClass();

    List<TestableMethod> getTestableMethods();

}
