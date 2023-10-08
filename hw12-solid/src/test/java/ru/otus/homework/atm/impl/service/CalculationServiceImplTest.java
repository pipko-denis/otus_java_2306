package ru.otus.homework.atm.impl.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework.atm.api.exceptions.NotEnoughBanknotesException;

import java.util.Map;

class CalculationServiceImplTest {

    private final static CalculationService calculationService = new CalculationServiceImpl();

    @Test
    @DisplayName("calculate amount of money -> doesn't throw exception and return proper data")
    void calcAmountDoesNotThrowExceptionAndReturnProperData() {
        Map<Integer, Integer> balance = Map.of(100, 3, 50, 2, 20, 4, 10, 1);
        int expectedAmount = balance.entrySet().stream()
                .map(elem -> elem.getKey() * (elem.getValue() - 1))
                .reduce(0, (sub, elem) -> sub = sub + elem);

        //Assertions
        Map<Integer, Integer> actualAmount = Assertions.assertDoesNotThrow(() -> calculationService.calcAmount(balance, expectedAmount));
        Assertions.assertTrue(actualAmount.entrySet().size() > 0);
        actualAmount.forEach((key, value) -> Assertions.assertTrue(value <= balance.get(key)));
    }

    @Test
    @DisplayName("calculate amount when low balance -> throw exception")
    void calcAmountWhenFewMoneyThrowException() {
        //Given
        Map<Integer, Integer> balance = Map.of(100, 3, 50, 2, 20, 4, 10, 1);
        int amountNeeded = balance.entrySet().stream()
                .map(elem -> elem.getKey() * (elem.getValue() + 1))
                .reduce(0, (sub, elem) -> sub = sub + elem);

        //Assertions
        Assertions.assertThrows(NotEnoughBanknotesException.class,() -> calculationService.calcAmount(balance, amountNeeded));
    }

}