package ru.otus.homework.atm.impl.dao.banknote;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import ru.otus.homework.atm.impl.dao.banknote.BanknoteDataDTO;

@Getter
@AllArgsConstructor
public class BanknoteData implements BanknoteDataDTO {

    @NonNull
    Integer denomination;

    @NonNull
    Integer serial;

    @Override
    public String toString() {
        return "BanknoteData{" +
                "denomination=" + denomination +
                ", serial=" + serial +
                '}';
    }
}
