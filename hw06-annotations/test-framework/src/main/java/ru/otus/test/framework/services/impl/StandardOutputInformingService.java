package ru.otus.test.framework.services.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.otus.test.framework.services.api.InformingService;
import ru.otus.test.framework.services.api.ClassTestResults;
import ru.otus.test.framework.services.api.MethodTestResults;

public class StandardOutputInformingService implements InformingService {

    @Getter
    @Setter
    @AllArgsConstructor
    private static class ExceptionDescription {
        String methodName;
        Exception exception;
    }


    @Override
    public void informUser(ClassTestResults classTestResults) {
        String className = classTestResults.getTestClass().getSimpleName();
        long countSuccess = classTestResults.getMethodTestResults().stream().filter(MethodTestResults::isSuccessful).count();

        System.out.println(String.format("---------------------TEST RESULTS FOR \"%s\"---------------------", className));
        System.out.println(String.format("Test methods count: \"%s\":", classTestResults.getMethodTestResults().size()));
        System.out.println(String.format("Successful tests count: \"%s\":", countSuccess));

        if (classTestResults.getMethodTestResults().size() != countSuccess) {
            System.out.println("Exceptions:");
            classTestResults.getMethodTestResults().stream()
                    .filter(methodTestResults -> !methodTestResults.isSuccessful())
                    .forEach(this::informUserAboutException);
        }

    }

    private void informUserAboutException(MethodTestResults methodResults) {
        System.out.println(String.format("While executing method %s there was an exception with message: %s",
                (methodResults.getMethod() != null) ? methodResults.getMethod().getName() : "NO METHOD NAME"
                , (methodResults.getException() != null) ? methodResults.getException().getMessage() : "Empty message"));

        if (methodResults.getException() != null) {
            System.out.println("Stack trace: ");
            methodResults.getException().printStackTrace(System.out);
        }
    }

}
