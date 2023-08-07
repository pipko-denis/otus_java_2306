package ru.otus;

import ru.otus.test.framework.TestsRunner;
import ru.otus.test.framework.TestsRunnerImpl;
import ru.otus.tests.TestClassThePerfectOne;
import ru.otus.tests.TestClassNoBefore;
import ru.otus.tests.TestClassWithRuntimeError;

import java.util.List;

public class TestLaunchApp {

    public static void main(String[] args) {
        TestsRunner runner = new TestsRunnerImpl();
        List<Class<?>> classes = List.of(TestClassThePerfectOne.class, TestClassWithRuntimeError.class, TestClassNoBefore.class);
        runner.runTests(classes);
    }

}
