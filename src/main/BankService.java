package main;

import java.util.*;
import java.util.Optional;

public class BankService {
    private List<Transaction> history = new ArrayList<>();
    private Map<String,BankCard> cards = new HashMap<>();
    private Set<String> proccessedTxIds = new HashSet<>();
    public void addCard(BankCard card) {
        cards.put(card.getCardNumber(), card);
    }
    public Optional<BankCard> findCard(String cardNumber){
        return Optional.ofNullable(cards.get(cardNumber));
    }
    public ApiResponse<BankCard> processPayment(String txId, String cardNumber, double amount) {
        try {
            if(proccessedTxIds.contains(txId)){
                throw new IllegalArgumentException("Дубликат транзакции: " + txId);
            }
            BankCard card = findCard(cardNumber)
                    .orElseThrow(() -> new IllegalArgumentException("Карта не найдена: " + cardNumber));
            card.pay(amount);
            proccessedTxIds.add(txId);
            history.add(new Transaction(txId, amount, cardNumber, null, TxType.PAYMENT));            return ApiResponse.ok("Оплата прошла успешно!. ", card);
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
            BankCard from = findCard(fromCardNum)
                    .orElseThrow(()-> new IllegalArgumentException("Карта отправителя не найдена: " + fromCardNum));
            BankCard to = findCard(toCardNum)
                    .orElseThrow(()-> new IllegalArgumentException("Карта получателя не найдена: " + toCardNum));
            from.withdraw(amount);
            try{
                to.deposit(amount);
            }catch (Exception e){
                from.deposit(amount);
                throw e;
            }
            proccessedTxIds.add(txId);
            history.add(new Transaction(txId, amount, fromCardNum, toCardNum, TxType.TRANSFER));
            return ApiResponse.ok("Перевод выполнен успешно",  from);
        }catch (IllegalArgumentException | InsufficientFundsException e){
            return ApiResponse.error(e.getMessage());
        }
    }
    public ApiResponse<List<Transaction>> getCardHistory(String cardNumber){
        List<Transaction> result = history.stream()
                .filter(tx -> tx.getFromCard().equals(cardNumber))
                .toList();
        return ApiResponse.ok("История операций по карте " + cardNumber, result);
    }
    public double getTotalSpent(String cardNumber){
        return history.stream()
                .filter(tx -> tx.getFromCard().equals(cardNumber))
                .mapToDouble(tx -> tx.getAmount())
                .sum();
    }
    public List<Transaction> getTop3Transactions(){
        return history.stream()
                .sorted(Comparator.comparingDouble(Transaction::getAmount).reversed())
                .limit(3)
                .toList();
    }
}

