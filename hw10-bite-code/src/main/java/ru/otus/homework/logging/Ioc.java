package ru.otus.homework.logging;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Ioc<I> {

    @SuppressWarnings("unchecked")
    public <T extends I> I createInstance(T instance, Class<I> interfaceType) {
        InvocationHandler handler = new LoggingInvocationHandler<I>(instance);

        return (I)
                Proxy.newProxyInstance(
                        Ioc.class.getClassLoader(),
                        new Class<?>[]{interfaceType},
                        handler
                );
    }

}