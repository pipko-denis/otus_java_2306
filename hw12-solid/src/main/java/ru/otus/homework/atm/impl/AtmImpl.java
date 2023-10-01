package ru.otus.homework.atm.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.homework.atm.api.Atm;
import ru.otus.homework.atm.api.Denomination;
import ru.otus.homework.atm.api.exceptions.NotEnoughBanknotesException;
import ru.otus.homework.atm.impl.dao.banknote.BanknoteDAO;
import ru.otus.homework.atm.impl.dao.banknote.BanknoteData;
import ru.otus.homework.atm.impl.dao.banknote.BanknoteDataDTO;
import ru.otus.homework.atm.impl.mapper.BanknoteMapper;
import ru.otus.homework.atm.impl.service.CalculationService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AtmImpl implements Atm<Banknote> {

    private final CalculationService calculationService;

    private final BanknoteDAO<BanknoteData> banknoteDAO;

    private final BanknoteMapper banknoteMapper;

    private Map<Denomination, Integer> getBalanceIndeed() {
        return banknoteDAO.getAll().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, el -> el.getValue().size()));
    }

    @Override
    public List<Banknote> giveOutMoney(Integer amountNeeded) {

        List<BanknoteData> banknotesData = new ArrayList<>();

        Map<Integer, Integer> balance = getBalanceIndeed().entrySet().stream()
                .collect(Collectors.toMap(el -> el.getKey().getValue(), Map.Entry::getValue));

        calculationService.calcAmount(balance, amountNeeded)
                .forEach((key, value) -> {
                    banknotesData.addAll(
                            getBanknotesByDenomination(Denomination.getByValue(key), value).stream().toList()
                    );
                });
        banknoteDAO.removeBanknotes(banknotesData);

        return convertBanknoteDataToBanknote(banknotesData);
    }

    private List<Banknote> convertBanknoteDataToBanknote(List<? extends BanknoteDataDTO> banknoteDataList) {
        return banknoteDataList.stream()
                .map(banknoteMapper::banknoteFromBanknoteDataDTO)
                .collect(Collectors.toList());
    }

    private List<BanknoteData> getBanknotesByDenomination(Denomination denomination, Integer amount) {
        List<BanknoteData> banknoteDataList = banknoteDAO.getBanknotesByDenomination(denomination).stream()
                .limit(amount)
                .collect(Collectors.toList());
        if (banknoteDataList.size() != amount) {
            throw new NotEnoughBanknotesException(String.format("Not enough banknotes with denomination '%d' ({%d}/{%d})!",
                    denomination.getValue(), banknoteDataList.size(), amount));
        }

        return banknoteDataList;
    }

    @Override
    public void acceptMoney(List<Banknote> banknotes) {
        banknoteDAO.addBanknotes(banknotes.stream()
                .map(banknoteMapper::banknoteDataFromBanknoteDTO)
                .collect(Collectors.toList()));
    }

    @Override
    public Map<Denomination, Integer> getBalance() {
        return getBalanceIndeed();
    }
}
