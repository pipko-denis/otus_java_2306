package ru.otus.homework.atm.impl.dao.banknote;

import ru.otus.homework.atm.api.Denomination;

import java.util.List;
import java.util.Map;

public interface BanknoteDAO<T extends BanknoteDataDTO> {
    void addBanknotes(List<T> banknotes);

    List<T> getBanknotesByDenomination(Denomination denomination);

    Map<Denomination, List<T>> getAll();

    void removeBanknotes(List<T> banknotes);

}
