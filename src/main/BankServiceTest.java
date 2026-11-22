package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BankServiceTest {

    private BankService bankService;
    private GoldCard goldCard;
    private BankCard regularCard;

    @BeforeEach
    void setUp() {
        bankService = new BankService();
        goldCard = new GoldCard("44000321", "Darkhan", 50_000.0);
        regularCard = new BankCard("44009999", "Arman", 10_000.0);
        bankService.addCard(goldCard);
        bankService.addCard(regularCard);
    }

    @Test
    void shouldApplyCashbackOnGoldCardPayment() {
        // Оплата 30 000 с GoldCard: списание 30 000 + кэшбэк 5% (1 500) = остаток 21 500
        ApiResponse<BankCard> response = bankService.processPayment("TX-1", "44000321", 30_000.0);

        assertTrue(response.isSuccess());
        assertEquals(21_500.0, goldCard.getBalance(), 0.001);
    }

    @Test
    void shouldNotApplyCashbackOnRegularCardPayment() {
        ApiResponse<BankCard> response = bankService.processPayment("TX-2", "44009999", 4_000.0);

        assertTrue(response.isSuccess());
        assertEquals(6_000.0, regularCard.getBalance(), 0.001);
    }

    @Test
    void shouldRejectDuplicateTransactionId() {
        bankService.processPayment("TX-1", "44000321", 10_000.0);
        ApiResponse<BankCard> duplicate = bankService.processPayment("TX-1", "44000321", 10_000.0);

        assertFalse(duplicate.isSuccess());
        // Баланс должен измениться только один раз: 50 000 - 10 000 + 500 = 40 500
        assertEquals(40_500.0, goldCard.getBalance(), 0.001);
    }

    @Test
    void shouldFailWhenInsufficientFunds() {
        ApiResponse<BankCard> response = bankService.processPayment("TX-3", "44009999", 25_000.0);

        assertFalse(response.isSuccess());
        assertEquals(10_000.0, regularCard.getBalance(), 0.001);
    }

    @Test
    void shouldTransferMoneyWithoutCashback() {
        // При переводе через withdraw() кэшбэк начисляться не должен
        ApiResponse<BankCard> response = bankService.transfer("TX-4", "44000321", "44009999", 5_000.0);

        assertTrue(response.isSuccess());
        assertEquals(45_000.0, goldCard.getBalance(), 0.001);
        assertEquals(15_000.0, regularCard.getBalance(), 0.001);
    }

    @Test
    void shouldRejectSelfTransfer() {
        ApiResponse<BankCard> response = bankService.transfer("TX-5", "44000321", "44000321", 1_000.0);

        assertFalse(response.isSuccess());
        assertEquals(50_000.0, goldCard.getBalance(), 0.001);
    }

    @Test
    void shouldCalculateTotalSpentAndTop3Transactions() {
        bankService.processPayment("TX-1", "44000321", 10_000.0);
        bankService.transfer("TX-2", "44000321", "44009999", 25_000.0);
        bankService.processPayment("TX-3", "44000321", 5_000.0);
        bankService.processPayment("TX-4", "44009999", 2_000.0);

        assertEquals(40_000.0, bankService.getTotalSpent("44000321"), 0.001);

        List<Transaction> top3 = bankService.getTop3Transactions();
        assertEquals(3, top3.size());
        assertEquals(25_000.0, top3.get(0).getAmount(), 0.001);
        assertEquals(10_000.0, top3.get(1).getAmount(), 0.001);
        assertEquals(5_000.0, top3.get(2).getAmount(), 0.001);
    }
}