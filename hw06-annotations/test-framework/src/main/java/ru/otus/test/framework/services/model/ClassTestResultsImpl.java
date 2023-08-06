package ru.otus.test.framework.services.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.otus.test.framework.services.api.ClassTestResults;
import ru.otus.test.framework.services.api.MethodTestResults;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class ClassTestResultsImpl implements ClassTestResults {

    private final Class<?> testClass;

    List<MethodTestResults> methodTestResults;

}
