package ru.otus.test.framework.services.api;

import java.util.List;

public interface ClassTestResults {

    Class<?> getTestClass();

    List<MethodTestResults> getMethodTestResults();

}
