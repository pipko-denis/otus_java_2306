package ru.otus.homework.atm.api;

import lombok.Getter;
import ru.otus.homework.atm.api.exceptions.DenominationNotFoundException;

import java.util.Set;

@Getter
public enum Denomination {
    TEN(10),TWENTY(20),FIFTY(50),HUNDRED(100),FIVE_HUNDRED(500);

    final int value;

    static final  Set<Denomination> DENOMINATION_SET = Set.of(TEN,TWENTY,FIFTY,HUNDRED,FIVE_HUNDRED);

    Denomination(int value) {
        this.value = value;
    }

    public static Denomination getByValue(int aValue){
        return DENOMINATION_SET.stream()
                .filter(el -> el.getValue() == aValue)
                .findFirst()
                .orElseThrow(() -> new DenominationNotFoundException("Denomination "+aValue+" not found"));
    }

    @Override
    public String toString() {
        return "Denomination{" +
                "value=" + value +
                '}';
    }
}
