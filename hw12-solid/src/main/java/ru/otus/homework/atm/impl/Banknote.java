package ru.otus.homework.atm.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.otus.homework.atm.api.BanknoteDTO;
import ru.otus.homework.atm.api.Denomination;

@Getter
@AllArgsConstructor
public class Banknote implements BanknoteDTO {

    Denomination denomination;

    Integer serial;

}
