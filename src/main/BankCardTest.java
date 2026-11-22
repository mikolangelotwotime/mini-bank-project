package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankCardTest {
    private BankCard card;
    @BeforeEach
    void setUp(){
        card = new BankCard("4400-1111", "Meiirzhan", 10_000.0);
    }
    @Test
    void shouldDepositMoney(){
        card.deposit(5_000.0);
        assertEquals(15_000.0, card.getBalance(), 0.001);
    }
    @Test
    void shouldThrowExceptionWhenDepositIsNegative(){
        assertThrows(IllegalArgumentException.class, ()-> {card.deposit(-100);});
    }
    @Test
    void shouldThrowExceptionWhenNotEnoughBalance(){
        assertThrows(InsufficientFundsException.class, () -> {card.withdraw(50_000.0);});
    }
    @Test
    void shouldNotThrowWhenDepositIsValid(){
        assertDoesNotThrow(() -> card.withdraw(100.0));
        assertTrue(card.getBalance() < 10_000.0);
    }
}