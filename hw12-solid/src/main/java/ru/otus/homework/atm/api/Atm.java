package ru.otus.homework.atm.api;

import java.util.List;
import java.util.Map;

public interface Atm<T extends BanknoteDTO> {

    List<T> giveOutMoney(Integer amount);

    void acceptMoney(List<T> banknotes);

    Map<Denomination, Integer> getBalance();

}
