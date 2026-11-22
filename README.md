# Mini Bank Core Engine (Java Core)

A console-based banking processing simulator built with core Java to practice Object-Oriented Programming, Collections, and custom exception handling.

## Key Features
* **Card Hierarchy & Polymorphism:** `BankCard` base class and `GoldCard` subclass implementing a `Cashbackable` interface (5% cashback on retail payments).
* **Idempotency / Duplicate Protection:** Tracks processed transaction IDs using a `HashSet` to prevent double-charging.
* **Fast Card Lookup:** Stores and retrieves accounts in $O(1)$ time via `HashMap`.
* **Atomic P2P Transfers with Rollback:** Transfers funds between cards without cashback and automatically rolls back the sender's balance if the recipient deposit fails.
* **Custom Exceptions:** Handles invalid operations and insufficient balance states via `InsufficientFundsException` without crashing the service.

## Project Structure
* `src/main/BankCard.java` — Base card entity with balance validation and `equals`/`hashCode` by card number.
* `src/main/GoldCard.java` — Premium card with overridden payment logic and cashback calculation.
* `src/main/Cashbackable.java` — Contract for cashback-eligible cards.
* `src/main/BankService.java` — Core business logic for payments and P2P transfers.
* `src/main/InsufficientFundsException.java` — Custom unchecked exception for overdraft protection.
* `src/main/Main.java` — Simulation runner covering payments, cashback, duplicates, and P2P transfers.

## How to Run
1. Clone the repository:
   ```bash
   git clone [https://github.com/mikolangelotwotime/mini-bank-project.git](https://github.com/mikolangelotwotime/mini-bank-project.git)