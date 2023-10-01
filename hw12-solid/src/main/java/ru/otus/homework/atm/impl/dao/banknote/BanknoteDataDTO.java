package ru.otus.homework.atm.impl.dao.banknote;

import ru.otus.homework.atm.api.Denomination;

public interface BanknoteDataDTO {

    Denomination getDenomination();

    Integer getSerial();

}
