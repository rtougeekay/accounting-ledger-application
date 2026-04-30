# Accounting Ledger Application

## Project Description

This is a Java console application that works like a simple accounting ledger. The app allows a user to track financial transactions such as deposits and payments.

The user can:

- Add a deposit
- Make a payment
- View all transactions
- View only deposits
- View only payments
- Run reports
- Search transactions by vendor
- Exit the application

All transactions are saved inside a CSV file named `transactions.csv`.

Each transaction stores:

- Date
- Time
- Description
- Vendor
- Amount

Example transaction format:

```text
2026-04-30|11:15:00|Paycheck|Intuit|1000.0