package ru.otus.tests;

import ru.otus.test.framework.annotations.After;
import ru.otus.test.framework.annotations.Before;
import ru.otus.test.framework.annotations.Test;

public class TestClassThePerfectOne {

    private int testValue = 0;

    @Before
    public void beforeTests() {
        testValue++;
        System.out.println("Before, testValue: "+testValue);
    }

    @Test
    public void test1() {
        testValue++;
        System.out.println("Test1, testValue: "+testValue);
    }

    @Test
    public void test2() {
        testValue++;
        System.out.println("Test2, testValue: "+testValue);
    }

    @Test
    public void test3() {
        testValue++;
        System.out.println("Test3, testValue: "+testValue);
    }

    @After
    public void afterTests() {
        testValue++;
        System.out.println("After, testValue: "+testValue);
    }

}
