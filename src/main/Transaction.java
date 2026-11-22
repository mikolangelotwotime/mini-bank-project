package main;

import java.time.LocalDateTime;

public class Transaction {
    private String id;
    private TxType type;
    private String fromCard;
    private String toCard;
    private double amount;
    private LocalDateTime timestamp;

    public Transaction(String id, double amount, String fromCard, String toCard, TxType type) {
        this.id = id;
        this.amount = amount;
        this.fromCard = fromCard;
        this.toCard = toCard;
        this.type = type;
        this.timestamp = LocalDateTime.now();
    }
    public String getId() {
        return id;
    }

    public TxType getType() {
        return type;
    }

    public String getFromCard() {
        return fromCard;
    }

    public String getToCard() {
        return toCard;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", fromCard='" + fromCard + '\'' +
                ", toCard='" + toCard + '\'' +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                '}';
    }
}
