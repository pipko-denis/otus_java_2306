package ru.otus.homework.atm.impl.dao.banknote;

import lombok.NonNull;
import ru.otus.homework.atm.api.Denomination;
import ru.otus.homework.atm.api.exceptions.BanknoteDuplicateSerialException;
import ru.otus.homework.atm.api.exceptions.BanknotesNotFoundException;
import ru.otus.homework.atm.api.exceptions.RemoveBanknotesException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BanknoteDAOImpl implements BanknoteDAO<BanknoteData> {

    private final Map<Denomination, List<BanknoteData>> BANKNOTE_STORAGE = new HashMap<>();

    /**
     * Adds banknotes to the store     *
     * @param banknotes - list of BanknoteDataDTO
     * @throws BanknoteDuplicateSerialException if store contains banknote with specified serial and denomination
     */
    @Override
    public void addBanknotes(List<BanknoteData> banknotes) {
        banknotes.forEach(banknoteData -> {
            if (!BANKNOTE_STORAGE.containsKey(banknoteData.getDenomination())) {
                BANKNOTE_STORAGE.put(banknoteData.getDenomination(), new ArrayList<>());
            }
            List<BanknoteData> existingBanknotes = BANKNOTE_STORAGE.get(banknoteData.getDenomination());

            throwExceptionIfBanknoteExists(banknoteData, existingBanknotes);

            existingBanknotes.add(banknoteData);
        });
    }

    private void throwExceptionIfBanknoteExists(BanknoteDataDTO banknoteDTO, List<BanknoteData> existingBanknotes) {
        if (existingBanknotes.stream()
                .anyMatch(banknoteData -> banknoteData.getSerial().equals(banknoteDTO.getSerial()))
        ) {
            throw new BanknoteDuplicateSerialException(
                    String.format("Banknote with denomination '%d' and serial '%d' already exists in the store",
                            banknoteDTO.getDenomination().getValue(), banknoteDTO.getSerial())
            );
        }
    }

    @Override
    public List<BanknoteData> getBanknotesByDenomination(Denomination denomination) {
        return BANKNOTE_STORAGE.get(denomination);
    }

    @Override
    public Map<Denomination, List<BanknoteData>> getAll() {
        return Map.copyOf(BANKNOTE_STORAGE);
    }

    /**
     * Removes banknote from repository
     *
     * @param banknotes - list of banknotes
     * @throws RuntimeException's child if it was found more than one banknote with specified serial, or banknotes wasn't found or
     *                            if banknote with specified serial was removed earlier
     */
    @Override
    public void removeBanknotes(@NonNull List<BanknoteData> banknotes) {
        banknotes.forEach(banknoteDTO -> {
            BanknoteData banknoteDataToRemove = getBanknoteByDenominationSerial(banknoteDTO.getDenomination(), banknoteDTO.getSerial());
            if (!BANKNOTE_STORAGE.get(banknoteDTO.getDenomination()).remove(banknoteDataToRemove)) {
                throw new RemoveBanknotesException("Banknote with serial \"" + banknoteDTO.getSerial() + "\" wasn't removed! Probably element was already removed earlier.");
            }
        });
    }

    private BanknoteData getBanknoteByDenominationSerial(@NonNull Denomination denomination, @NonNull Integer serial) {
        List<BanknoteData> banknotesBySerial = getBanknotesBySerial(BANKNOTE_STORAGE.get(denomination), serial);

        if (banknotesBySerial.size() != 1){
            throw new BanknotesNotFoundException("Incorrect count(" + banknotesBySerial.size() + ") of banknotes with serial \"" + serial + "\"");
        }

        return banknotesBySerial.get(0);
    }

    private List<BanknoteData> getBanknotesBySerial(@NonNull List<BanknoteData> banknotesByDenomination, @NonNull Integer serial) {
        return banknotesByDenomination.stream()
                .filter(banknoteData -> banknoteData.getSerial().equals(serial))
                .collect(Collectors.toList());
    }


}
