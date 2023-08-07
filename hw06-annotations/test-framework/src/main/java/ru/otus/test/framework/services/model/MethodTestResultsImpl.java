package ru.otus.test.framework.services.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.otus.test.framework.services.api.MethodTestResults;

import java.lang.reflect.Method;

@Getter
@Setter
@Builder
public class MethodTestResultsImpl implements MethodTestResults {

    private Method method;

    private boolean isSuccessful;

    private Exception exception;

}
