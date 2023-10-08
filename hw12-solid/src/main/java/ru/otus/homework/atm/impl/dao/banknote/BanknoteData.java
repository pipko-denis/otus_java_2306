package ru.otus.homework.atm.impl.dao.banknote;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import ru.otus.homework.atm.api.Denomination;

@Getter
@AllArgsConstructor
public class BanknoteData implements BanknoteDataDTO {

    @NonNull
    Denomination denomination;

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
