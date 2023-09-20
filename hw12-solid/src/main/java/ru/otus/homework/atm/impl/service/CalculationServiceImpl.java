package ru.otus.homework.atm.impl.service;

import ru.otus.homework.atm.api.exceptions.NotEnoughBanknotesException;
import ru.otus.homework.atm.impl.service.CalculationService;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CalculationServiceImpl implements CalculationService {
    @Override
    public Map<Integer, Integer> calcAmount(Map<Integer, Integer> availableAmount, Integer amountNeeded) {
        Map<Integer, Integer> result = new HashMap<>();

        Integer amountLeft = amountNeeded;
        LinkedHashMap<Integer, Integer> sortedMap = availableAmount.entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByKey().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        for (Map.Entry<Integer, Integer> entry : sortedMap.entrySet()) {
            Integer denomination = entry.getKey();
            Integer availableCount = entry.getValue();

            Integer countNeeded = amountLeft / denomination;

             if (availableCount >= countNeeded) {
                 amountLeft -= countNeeded * denomination;

                 result.put(denomination, countNeeded);
            }
        }

        if (amountLeft > 0) {
            result.clear();
            throw new NotEnoughBanknotesException(String.format("Not enough banknotes (requested: %d, absent: %d",amountNeeded,amountLeft));
        }

        return result;
    }
}
