package ru.otus.test.framework.services.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.otus.test.framework.services.api.TestableClass;
import ru.otus.test.framework.services.api.TestableMethod;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class TestableClassImpl implements TestableClass {

    private final Class<?> testClass;
    private final List<TestableMethod> testableMethods;

}
