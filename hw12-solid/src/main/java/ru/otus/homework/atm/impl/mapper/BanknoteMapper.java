package ru.otus.homework.atm.impl.mapper;

import org.mapstruct.Mapper;
import ru.otus.homework.atm.impl.dao.banknote.BanknoteDataDTO;
import ru.otus.homework.atm.impl.dao.banknote.BanknoteData;
import ru.otus.homework.atm.api.BanknoteDTO;
import ru.otus.homework.atm.impl.Banknote;

import java.util.List;


@Mapper
public interface BanknoteMapper {

    Banknote banknoteFromBanknoteDataDTO(BanknoteDataDTO source);

    BanknoteData banknoteDataFromBanknoteDTO(BanknoteDTO source);

}
