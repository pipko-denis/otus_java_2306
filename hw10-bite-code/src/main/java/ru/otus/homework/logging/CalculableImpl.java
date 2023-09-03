package ru.otus.homework.logging;

public class CalculableImpl implements Calculable {

    @Log
    @Override
    public void calculate(int param) {
        System.out.println("Calling \"calculate\" from Impl with param: "+param);
    }

}
