package ru.otus.homework.atm.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.otus.homework.atm.api.BanknoteDTO;

@Getter
@AllArgsConstructor
public class Banknote implements BanknoteDTO {

    Integer denomination;

    Integer serial;

}
