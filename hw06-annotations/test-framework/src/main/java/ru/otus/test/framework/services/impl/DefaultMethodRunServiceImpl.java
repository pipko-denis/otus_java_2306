package ru.otus.test.framework.services.impl;

import ru.otus.test.framework.services.api.MethodRunService;
import ru.otus.test.framework.services.exceptions.InvokeMethodException;
import ru.otus.test.framework.services.exceptions.TestInstanceCreateException;
import ru.otus.test.framework.services.api.MethodTestResults;
import ru.otus.test.framework.services.model.MethodTestResultsImpl;
import ru.otus.test.framework.services.api.TestableMethod;

import java.lang.reflect.Method;

public class DefaultMethodRunServiceImpl implements MethodRunService {

    @Override
    public MethodTestResults runTestMethod(TestableMethod method) {

        MethodTestResultsImpl result = MethodTestResultsImpl.builder()
                .isSuccessful(false)
                .method(method.getTestMethod())
                .build();

        try {
            Object methodOwnerInstance = createMethodOwnerInstance(method.getTestClass());

            doRunTestMethod(method, methodOwnerInstance);

            result.setSuccessful(true);

        } catch (Exception ex) {
            result.setException(ex);
        }

        return result;
    }

    private void doRunTestMethod(TestableMethod method, Object methodOwnerInstance) {
        try {
            if (method.getMethodBefore() != null) {
                runMethod(method.getMethodBefore(), methodOwnerInstance);
            }

            runMethod(method.getTestMethod(), methodOwnerInstance);
        } finally {
            if (method.getMethodAfter() != null) {
                runMethod(method.getMethodAfter(), methodOwnerInstance);
            }
        }
    }

    private Object createMethodOwnerInstance(Class<?> clazz) {
        Object methodOwnerInstance;
        try {
            methodOwnerInstance = clazz.getConstructor().newInstance();
        } catch (Exception e) {
            throw new TestInstanceCreateException("Can't create instance of the \"" + clazz.getSimpleName() + "\" class", e);
        }
        return methodOwnerInstance;
    }

    private void runMethod(Method method, Object methodOwnerInstance) {

        if (method == null) {
            throw new InvokeMethodException("Method, that should be invoked, is null");
        }

        try {
            method.invoke(methodOwnerInstance);
        } catch (Exception e) {
            throw new InvokeMethodException("Can't invoke method \"" + method.getName() + "\"", e);
        }
    }
}
