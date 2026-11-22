package main;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BankService {
    private Map<String,BankCard> cards = new HashMap<>();
    private Set<String> proccessedTxIds = new HashSet<>();
    public void addCard(BankCard card) {
        cards.put(card.getCardNumber(), card);
    }
    public ApiResponse<BankCard> processPayment(String txId, String cardNumber, double amount) {
        try {
            if(proccessedTxIds.contains(txId)){
                throw new IllegalArgumentException("Дубликат транзакции: " + txId);
            }
            BankCard card = cards.get(cardNumber);
            if(card == null){
                throw new IllegalArgumentException("Карта не найдена: " + cardNumber);
            }
            card.pay(amount);
            proccessedTxIds.add(txId);
            return ApiResponse.ok("Оплата прошла успешно!. ", card);
        } catch (IllegalArgumentException | InsufficientFundsException e) {
            return ApiResponse.error(e.getMessage());
        }
    }
    public ApiResponse<BankCard> transfer(String txId, String fromCardNum, String toCardNum, double amount){
        try{
            if(proccessedTxIds.contains(txId)){
                throw new IllegalArgumentException("Дубликат транзакции ");
            }
            if(fromCardNum.equals(toCardNum)){
                throw new IllegalArgumentException("Нельзя перевести самому себе ");
            }
            BankCard from = cards.get(fromCardNum);
            BankCard to = cards.get(toCardNum);
            if(from == null || to == null){
                throw new IllegalArgumentException("Одна из карт не найдена");
            }
            from.withdraw(amount);
            try{
                to.deposit(amount);
            }catch (Exception e){
                from.deposit(amount);
                throw e;
            }
            proccessedTxIds.add(txId);
            return ApiResponse.ok("Перевод выполнен успешно",  from);
        }catch (IllegalArgumentException | InsufficientFundsException e){
            return ApiResponse.error(e.getMessage());
        }
    }
}
