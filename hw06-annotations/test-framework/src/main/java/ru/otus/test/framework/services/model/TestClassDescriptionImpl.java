package ru.otus.test.framework.services.model;

import lombok.Getter;
import lombok.Setter;
import ru.otus.test.framework.services.api.TestClassDescription;

import java.lang.reflect.Method;
import java.util.List;

@Getter
@Setter
public class TestClassDescriptionImpl implements TestClassDescription {

    public TestClassDescriptionImpl(Class<?> testClass) {
        this.testClass = testClass;
    }

    Class<?> testClass;

    Method beforeMethod;

    List<Method> testMethods;

    Method afterMethod;

}
