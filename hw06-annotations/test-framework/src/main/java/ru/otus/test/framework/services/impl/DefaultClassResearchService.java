package ru.otus.test.framework.services.impl;

import ru.otus.test.framework.annotations.After;
import ru.otus.test.framework.annotations.Before;
import ru.otus.test.framework.annotations.Test;
import ru.otus.test.framework.services.api.ClassResearchService;
import ru.otus.test.framework.services.exceptions.InvalidTestClassException;
import ru.otus.test.framework.services.api.TestableClass;
import ru.otus.test.framework.services.model.TestableClassImpl;
import ru.otus.test.framework.services.api.TestableMethod;
import ru.otus.test.framework.services.model.TestableMethodImpl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultClassResearchService implements ClassResearchService {

    @Override
    public List<TestableClass> getTests(List<Class<?>> testClasses){
        List<TestableClass> result = new ArrayList<>();

        for (Class<?> clazz : testClasses) {

            Method beforeMethod = getSingleMethodByAnnotation(clazz, Before.class);

            Method afterMethod = getSingleMethodByAnnotation(clazz, After.class);

            List<Method> testMethods = findClassMethodsByAnnotation(clazz, Test.class);
            if (testMethods.size() == 0) {
                throw new InvalidTestClassException(
                        String.format("Test class \"%s\" doesn't contain any methods with Test annotation!", clazz.getSimpleName())
                );
            }

            List<TestableMethod> testableMethods = testMethods.stream()
                    .map(method -> new TestableMethodImpl(clazz,beforeMethod,method,afterMethod))
                    .collect(Collectors.toList());

            result.add(new TestableClassImpl(clazz, testableMethods));


        }

        return result;
    }

    private <T extends Annotation> Method getSingleMethodByAnnotation(Class<?> methodsOwner, Class<T> annotationClass) {
        List<Method> methods = findClassMethodsByAnnotation(methodsOwner, annotationClass);
        return switch (methods.size()) {
            case 0 -> null;
            case 1 -> methods.get(0);
            default -> {
                String methodNames = methods.stream().map(Method::getName).collect(Collectors.joining(","));
                throw new InvalidTestClassException(
                        String.format("Test class \"%s\" contain more than one test method! Methods with \"%s\" annotation: \"%s\"",
                                methodsOwner.getSimpleName(), annotationClass.getSimpleName(), methodNames)
                );
            }
        };

    }

    private <T extends Annotation> List<Method> findClassMethodsByAnnotation(Class<?> classWithMethods, Class<T> annotationClass) {
        List<Method> result = new ArrayList<>();
        for (Method method : classWithMethods.getDeclaredMethods()) {
            if (method.getAnnotation(annotationClass) != null) {
                result.add(method);
            }
        }
        return result;
    }

}
