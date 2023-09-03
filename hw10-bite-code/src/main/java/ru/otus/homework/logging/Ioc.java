package ru.otus.homework.logging;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Ioc {

    public static Calculable createInstanceWithLogging() {
        InvocationHandler handler = new LoggingInvocationHandler(new CalculableImpl());
        return (Calculable)
                Proxy.newProxyInstance(
                        Ioc.class.getClassLoader(),
                        new Class<?>[]{Calculable.class},
                        handler
                );
    }

    static class LoggingInvocationHandler implements InvocationHandler {

        private final Calculable calculableClass;

        public LoggingInvocationHandler(Calculable calculableClass) {
            this.calculableClass = calculableClass;
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

        private boolean isShouldBeLogged(Method method) {
            String invokeMethodName = method.getName();

            return Arrays.stream(calculableClass.getClass().getDeclaredMethods())
                    .anyMatch(declaredMethod -> declaredMethod.getName().equals(invokeMethodName) &&
                            (declaredMethod.getDeclaredAnnotation(Log.class) != null)
                    );
        }
    }

}
