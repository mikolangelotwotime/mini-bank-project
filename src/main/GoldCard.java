package main;

public class GoldCard extends  BankCard implements  Cashbackable{
    public GoldCard(String cardNumber, String owner, double balance) {
        super(cardNumber, owner, balance);
    }
    public double calculateCashback(double amount){
        return amount * 0.05;
    }
    @Override
    public void pay(double amount){
        super.pay(amount);
        double cashback = calculateCashback(amount);
        this.balance += cashback;
    }
}
