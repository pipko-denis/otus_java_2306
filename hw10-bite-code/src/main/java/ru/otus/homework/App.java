package ru.otus.homework;

import ru.otus.homework.logging.Ioc;
import ru.otus.homework.logging.Calculable;

import java.util.Random;

public class App {

    public static void main(String[] args) {
        System.out.println("Application launched!");
        Calculable calculable = Ioc.createInstanceWithLogging();
        calculable.calculate(new Random().nextInt(0,100));
    }

}
