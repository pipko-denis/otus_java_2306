package ru.otus.test.framework.services.api;

import java.util.List;

public interface ClassResearchService {

    List<TestableClass> getTests(List<Class<?>> testClasses);

}
