package ru.otus.test.framework.services.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.test.framework.services.api.ClassRunService;
import ru.otus.test.framework.services.api.MethodRunService;
import ru.otus.test.framework.services.api.ClassTestResults;
import ru.otus.test.framework.services.model.ClassTestResultsImpl;
import ru.otus.test.framework.services.api.TestableClass;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DefaultClassRunService implements ClassRunService {

    private final MethodRunService methodRunService;

    public DefaultClassRunService() {
        this.methodRunService = new DefaultMethodRunServiceImpl();
    }

    @Override
    public ClassTestResults runClassTestMethods(TestableClass classTests) {
        ClassTestResultsImpl testResults  = new ClassTestResultsImpl(classTests.getTestClass());

        testResults.setMethodTestResults(
                classTests.getTestableMethods().stream()
                    .map(methodRunService::runTestMethod)
                    .collect(Collectors.toList())
        );

        return testResults;
    }

}
