package ru.otus.homework.atm.domain.impl.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.atm.api.Atm;
import ru.otus.homework.atm.api.exceptions.NotEnoughBanknotesException;
import ru.otus.homework.atm.impl.AtmImpl;
import ru.otus.homework.atm.impl.Banknote;
import ru.otus.homework.atm.impl.dao.banknote.BanknoteDAO;
import ru.otus.homework.atm.impl.dao.banknote.BanknoteData;
import ru.otus.homework.atm.impl.mapper.BanknoteMapper;
import ru.otus.homework.atm.impl.service.CalculationService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class AtmImplTest {

    @Mock
    public static BanknoteMapper banknoteMapper;

    @Mock
    private static CalculationService calculationService;

    @Mock
    private static BanknoteDAO<BanknoteData> banknoteDAO;

    //@Mock
    private static Atm<Banknote> atm;

    private static final List<BanknoteData> BANKNOTES_FIFTY_DENOMINATION = List.of(
            new BanknoteData(50, 100),
            new BanknoteData(50, 101)
    );
    private static final List<BanknoteData> BANKNOTES_TWENTY_DENOMINATION = List.of(
            new BanknoteData(20, 100),
            new BanknoteData(20, 101)
    );

    @BeforeEach
    void setUp() {
        atm = new AtmImpl(calculationService, banknoteDAO, banknoteMapper);
    }

    @Test
    @DisplayName("requesting money -> doesn't throw exception and doesn't have side effects")
    void giveOutMoneyDoesNotHaveSideEffects() {
        //given
        int availableDenomination = BANKNOTES_FIFTY_DENOMINATION.get(1).getDenomination();
        int expectedBanknotesCount = BANKNOTES_FIFTY_DENOMINATION.size();
        int amountNeeded = BANKNOTES_FIFTY_DENOMINATION.stream()
                .map(BanknoteData::getDenomination)
                .reduce(0, (subtotal, banknote) -> subtotal + banknote);
        Map<Integer, List<BanknoteData>> value = Map.of(availableDenomination, BANKNOTES_FIFTY_DENOMINATION);
        Map<Integer, Integer> availableAmount = value.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, el -> el.getValue().size()));


        //mock
        Mockito.when(banknoteDAO.getAll()).thenReturn(value);
        Mockito.when(banknoteDAO.getBanknotesByDenomination(availableDenomination)).thenReturn(BANKNOTES_FIFTY_DENOMINATION);
        Mockito.when(calculationService.calcAmount(availableAmount, amountNeeded)).thenReturn(Map.of(availableDenomination, expectedBanknotesCount));

        //Assert
        int actualSize = Assertions.assertDoesNotThrow(() -> atm.giveOutMoney(amountNeeded)).size();
        Assertions.assertEquals(expectedBanknotesCount, actualSize);

    }

    @Test
    @DisplayName("requesting money when few banknotes -> throw exception")
    void giveOutMoneyWillThrowExceptionIfNotEnoughBanknotes() {
        //given
        int availableDenomination = BANKNOTES_FIFTY_DENOMINATION.get(1).getDenomination();
        int expectedBanknotesCount = BANKNOTES_FIFTY_DENOMINATION.size();
        int amountNeeded = BANKNOTES_FIFTY_DENOMINATION.stream()
                .map(BanknoteData::getDenomination)
                .reduce(0, (subtotal, banknote) -> subtotal + banknote);
        Map<Integer, List<BanknoteData>> mapAllBanknotes = Map.of(availableDenomination, BANKNOTES_FIFTY_DENOMINATION);
        Map<Integer, Integer> availableAmount = mapAllBanknotes.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, el -> el.getValue().size()));
        List<BanknoteData> incorrectBanknotesList = List.of();//BANKNOTES_FIFTY_DENOMINATION.get(0));

        //mock
        Mockito.when(banknoteDAO.getAll()).thenReturn(mapAllBanknotes);

        Mockito.when(banknoteDAO.getBanknotesByDenomination(availableDenomination)).thenReturn(incorrectBanknotesList);
        Mockito.when(calculationService.calcAmount(availableAmount, amountNeeded)).thenReturn(Map.of(availableDenomination, expectedBanknotesCount));

        //Assert
        Assertions.assertThrows(NotEnoughBanknotesException.class, () -> atm.giveOutMoney(amountNeeded));

    }

    @Test
    @DisplayName("putting in money -> doesn't throw exception and return proper data")
    void acceptMoneyDoesNotThrowException() {
        //given
        List<Banknote> banknotes = List.of(new Banknote(100, 1));
        //Assert
        Assertions.assertDoesNotThrow(() -> atm.acceptMoney(banknotes));
    }

    @Test
    @DisplayName("getting balance -> doesn't throw exception and return proper data")
    void getBalanceDoesNotThrowExceptionAndReturnProperData() {
        //Given
        Map<Integer, List<BanknoteData>> mapAllBanknotes = Map.of(
                BANKNOTES_FIFTY_DENOMINATION.get(0).getDenomination(), BANKNOTES_FIFTY_DENOMINATION,
                BANKNOTES_TWENTY_DENOMINATION.get(0).getDenomination(), BANKNOTES_TWENTY_DENOMINATION
        );
        int expectedCountDenominations = mapAllBanknotes.entrySet().size();

        //Mock
        Mockito.when(banknoteDAO.getAll()).thenReturn(mapAllBanknotes);

        //Assert
        Map<Integer, Integer> actualBalance = Assertions.assertDoesNotThrow(() -> atm.getBalance());
        Assertions.assertEquals(expectedCountDenominations, actualBalance.entrySet().size());
        Assertions.assertEquals(actualBalance.get(BANKNOTES_FIFTY_DENOMINATION.get(0).getDenomination()), BANKNOTES_FIFTY_DENOMINATION.size());
        Assertions.assertEquals(actualBalance.get(BANKNOTES_TWENTY_DENOMINATION.get(0).getDenomination()), BANKNOTES_TWENTY_DENOMINATION.size());

    }
}