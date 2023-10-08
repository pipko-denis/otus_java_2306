package ru.otus.homework.atm.dao.impl.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework.atm.api.Denomination;
import ru.otus.homework.atm.impl.dao.banknote.BanknoteDAO;
import ru.otus.homework.atm.api.exceptions.BanknoteDuplicateSerialException;
import ru.otus.homework.atm.impl.dao.banknote.BanknoteDAOImpl;
import ru.otus.homework.atm.impl.dao.banknote.BanknoteData;

import java.util.ArrayList;
import java.util.List;

class BanknoteDAOImplTest {

    private final static BanknoteDAO<BanknoteData> BANKNOTE_DAO = new BanknoteDAOImpl();

    private static final Denomination MAIN_DENOMINATION = Denomination.HUNDRED;

    private static final Denomination SECONDARY_DENOMINATION = Denomination.FIVE_HUNDRED;

    private final static BanknoteData BANKNOTE_TO_REMOVE = new BanknoteData(SECONDARY_DENOMINATION,9999);
    public static final int SERIAL_EXISTING_AFTER_INIT = 1;
    public static final int SERIAL_DOES_NOT_EXISTS_AFTER_INIT = 4;


    @BeforeAll
    static void setUp() {
        List<BanknoteData> banknoteDataList = new ArrayList<>();
        banknoteDataList.add(new BanknoteData(MAIN_DENOMINATION, SERIAL_EXISTING_AFTER_INIT));
        banknoteDataList.add(new BanknoteData(MAIN_DENOMINATION,2));
        banknoteDataList.add(new BanknoteData(SECONDARY_DENOMINATION,3));
        banknoteDataList.add(BANKNOTE_TO_REMOVE);
        BANKNOTE_DAO.addBanknotes(banknoteDataList);
    }

    @Test
    @DisplayName("adding proper banknotes -> doesn't throw exception")
    void whenAddBanknotesThereNoExceptions() {
        List<BanknoteData> banknotes = List.of(new BanknoteData(MAIN_DENOMINATION, SERIAL_DOES_NOT_EXISTS_AFTER_INIT));
        Assertions.assertDoesNotThrow(() -> BANKNOTE_DAO.addBanknotes(banknotes));
    }

    @Test
    @DisplayName("add banknotes and serial exist -> throws exception")
    void whenAddBanknotesWithExistingSerialThrowsExceptions() {
        List<BanknoteData> banknotesProducesException = List.of(new BanknoteData(MAIN_DENOMINATION, SERIAL_EXISTING_AFTER_INIT));
        Assertions.assertThrows(BanknoteDuplicateSerialException.class,() -> BANKNOTE_DAO.addBanknotes(banknotesProducesException));
    }

    @Test
    @DisplayName("getting banknotes by denomination -> doesn't throw exception and returns more than 0 records")
    void whenGetBanknotesByDenominationNoExceptionsAndSizeGreaterThanZero() {
        int size = Assertions.assertDoesNotThrow(() -> BANKNOTE_DAO.getBanknotesByDenomination(MAIN_DENOMINATION)).size();
        Assertions.assertTrue(size > 0,"Size of the 'getBanknotesByDenomination' method result should be greater than 0");
    }

    @Test
    @DisplayName("getting all banknotes -> doesn't throw exception and returns more than 0 records")
    void whenGetAllBanknotesNoExceptionsAndSizeGreaterThanZero() {
        int size = Assertions.assertDoesNotThrow(BANKNOTE_DAO::getAll).size();
        Assertions.assertTrue(size > 0,"Size of the 'getAll' method result should be greater than 0");
    }

    @Test
    @DisplayName("deleting banknote -> doesn't throw exception")
    void whenDeleteBanknoteNoExceptionsThrown() {
        Assertions.assertDoesNotThrow(() -> BANKNOTE_DAO.removeBanknotes(List.of(BANKNOTE_TO_REMOVE)));
    }
}