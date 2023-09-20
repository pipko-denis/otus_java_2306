package ru.otus.homework.atm.impl.service;

import java.util.Map;

public interface CalculationService {
    Map<Integer,Integer> calcAmount(Map<Integer,Integer> availableAmount,Integer amountNeeded);
}
