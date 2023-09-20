package ru.otus.homework.atm.impl.dao.banknote;

import java.util.List;
import java.util.Map;

public interface BanknoteDAO<T extends BanknoteDataDTO> {
    void addBanknotes(List<T> banknotes);

    List<T> getBanknotesByDenomination(Integer denomination);

    Map<Integer, List<T>> getAll();

    void removeBanknotes(List<T> banknotes);

}
