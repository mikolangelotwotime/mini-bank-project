package main;
import java.util.Objects;
public class BankCard {
    protected String cardNumber;
    protected String owner;
    protected double balance;
    public BankCard(String cardNumber, String owner, double balance) {
        if(cardNumber != null && !cardNumber.trim().isEmpty()) {
            this.cardNumber = cardNumber;
        }
        if(owner != null && !owner.trim().isEmpty()) {
            this.owner = owner;
        }
        if(balance >= 0) {
            this.balance = balance;
        }
    }
    public String getCardNumber() {
        return cardNumber;
    }
    public String getOwner() {
        return owner;
    }
    public double getBalance() {
        return balance;
    }
    public void withdraw(double amount){
        if(amount <= 0){
            throw new IllegalArgumentException("Сумма операции должна быть больше 0");
        }
        if(amount > balance){
            throw new InsufficientFundsException("Недостаточно средств на карте " + cardNumber);
        }
        this.balance -= amount;
    }
    public void pay(double amount){
        withdraw(amount);
    }
    public void deposit(double amount){
        if(amount <= 0){
            throw new IllegalArgumentException("Сумма операции должна быть больше 0");
        }
        this.balance += amount;
    }
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BankCard bankCard = (BankCard) o;
        return Objects.equals(cardNumber, bankCard.cardNumber);
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(cardNumber);
    }
    @Override
    public String toString() {
        return "BankCard{" +
                "cardNumber='" + cardNumber + '\'' +
                ", owner='" + owner + '\'' +
                ", balance=" + balance +
                '}';
    }
}

