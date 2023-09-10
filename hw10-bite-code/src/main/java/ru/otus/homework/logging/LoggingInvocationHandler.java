package ru.otus.homework.logging;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LoggingInvocationHandler<I> implements InvocationHandler {

    private final I calculableClass;

    private List<Method> logAnnotatedMethods;

    public LoggingInvocationHandler(I calculableClass) {
        this.calculableClass = calculableClass;
    }

    private List<Method> extractLogAnnotatedMethods(){
        return Arrays.stream(calculableClass.getClass().getDeclaredMethods())
                .filter(declaredMethod -> declaredMethod.getDeclaredAnnotation(Log.class) != null)
                .collect(Collectors.toList());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        boolean isShouldBeLogged = isShouldBeLogged(method);

        if (isShouldBeLogged) {
            String argsInline = Stream.of(args)
                    .map(Object::toString)
                    .collect(Collectors.joining(","));
            System.out.println("Executed method: " + method.getName() + ", params: " + argsInline);
        }

        return method.invoke(calculableClass, args);
    }

    private boolean isShouldBeLogged(Method invokenMethod) {
        if (logAnnotatedMethods == null) {
            logAnnotatedMethods = extractLogAnnotatedMethods();
        }

        return logAnnotatedMethods.stream()
                .anyMatch(declaredMethod -> declaredMethod.getName().equals(invokenMethod.getName()));
    }
}
