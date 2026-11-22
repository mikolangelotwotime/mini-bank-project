package main;
public class Main {
    public static void main(String[] args) {
        BankService bank = new BankService();
        GoldCard goldCard = new GoldCard("44000321", "Meiirzhan", 50000);
        BankCard regularCard = new BankCard("44009999", "Arman", 10000);
        bank.addCard(goldCard);
        bank.addCard(regularCard);
        System.out.println(bank.processPayment("TX-1", "44000321", 30000));
        System.out.println(bank.processPayment("TX-1", "44000321", 30000));
        System.out.println(bank.processPayment("TX-2", "44000321", 30000));
        System.out.println(bank.transfer("TX-3", "44000321", "44009999", 5000));
        System.out.println(bank.transfer("TX-4", "44000321", "44000321", 1000));
        System.out.println("\nИтоговые балансы:");
        System.out.println(goldCard);
        System.out.println(regularCard);
    }
}