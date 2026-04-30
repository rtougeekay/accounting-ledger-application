package com.pluralsight;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class App{
    static Scanner input = new Scanner(System.in);
    static String fileName = "transactions.csv";

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("=== Accounting Ledger App ===");
            System.out.println("D - Add Deposit");
            System.out.println("P - Make Payment");
            System.out.println("L - Ledger");
            System.out.println("X - Exit");
            System.out.print("Choose an option: ");

            String choice = input.nextLine().trim().toUpperCase();

            switch (choice) {
                case "D":
                    addTransaction(true);
                    break;
                case "P":
                    addTransaction(false);
                    break;
                case "L":
                    showLedger();
                    break;
                case "X":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
                    break;
            }
        }
    }

    public static void addTransaction(boolean isDeposit) {
        try {
            System.out.print("Enter description: ");
            String description = input.nextLine().trim();

            System.out.print("Enter vendor: ");
            String vendor = input.nextLine().trim();

            System.out.print("Enter amount: ");
            double amount = Double.parseDouble(input.nextLine().trim());

            if (!isDeposit) {
                amount = amount * -1;
            }

            String date = LocalDate.now().toString();
            String time = LocalTime.now().withNano(0).toString();

            Transaction transaction = new Transaction(date, time, description, vendor, amount);

            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            writer.write(transaction.toCsvLine());
            writer.newLine();
            writer.close();

            System.out.println("Transaction saved!");
        } catch (Exception e) {
            System.out.println("Something went wrong. Transaction was not saved.");
        }
    }

    public static ArrayList<Transaction> loadTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            String line = reader.readLine();

            while (line != null) {
                if (!line.startsWith("date|time")) {
                    String[] parts = line.split("\\|");

                    String date = parts[0];
                    String time = parts[1];
                    String description = parts[2];
                    String vendor = parts[3];
                    double amount = Double.parseDouble(parts[4]);

                    Transaction transaction = new Transaction(date, time, description, vendor, amount);
                    transactions.add(transaction);
                }

                line = reader.readLine();
            }

            reader.close();
        } catch (Exception e) {
            System.out.println("Could not read transactions file.");
        }

        return transactions;
    }

    public static void showLedger() {
        boolean inLedger = true;

        while (inLedger) {
            System.out.println();
            System.out.println("=== Ledger ===");
            System.out.println("A - All");
            System.out.println("D - Deposits");
            System.out.println("P - Payments");
            System.out.println("R - Reports");
            System.out.println("H - Home");
            System.out.print("Choose an option: ");

            String choice = input.nextLine().trim().toUpperCase();

            switch (choice) {
                case "A":
                    displayTransactions("ALL");
                    break;
                case "D":
                    displayTransactions("DEPOSITS");
                    break;
                case "P":
                    displayTransactions("PAYMENTS");
                    break;
                case "R":
                    showReports();
                    break;
                case "H":
                    inLedger = false;
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    public static void displayTransactions(String type) {
        ArrayList<Transaction> transactions = loadTransactions();

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);

            if (type.equals("ALL")) {
                System.out.println(transaction);
            } else if (type.equals("DEPOSITS") && transaction.getAmount() > 0) {
                System.out.println(transaction);
            } else if (type.equals("PAYMENTS") && transaction.getAmount() < 0) {
                System.out.println(transaction);
            }
        }
    }

    public static void showReports() {
        boolean inReports = true;

        while (inReports) {
            System.out.println();
            System.out.println("=== Reports ===");
            System.out.println("1 - Month To Date");
            System.out.println("2 - Previous Month");
            System.out.println("3 - Year To Date");
            System.out.println("4 - Previous Year");
            System.out.println("5 - Search by Vendor");
            System.out.println("0 - Back");
            System.out.print("Choose an option: ");

            String choice = input.nextLine().trim();

            switch (choice) {
                case "1":
                    showMonthToDateReport();
                    break;
                case "2":
                    showPreviousMonthReport();
                    break;
                case "3":
                    showYearToDateReport();
                    break;
                case "4":
                    showPreviousYearReport();
                    break;
                case "5":
                    searchByVendor();
                    break;
                case "0":
                    inReports = false;
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    public static void showMonthToDateReport() {
        ArrayList<Transaction> transactions = loadTransactions();

        LocalDate today = LocalDate.now();
        int currentMonth = today.getMonthValue();
        int currentYear = today.getYear();

        for (Transaction transaction : transactions) {
            LocalDate transactionDate = LocalDate.parse(transaction.getDate());

            if (transactionDate.getMonthValue() == currentMonth && transactionDate.getYear() == currentYear) {
                System.out.println(transaction);
            }
        }
    }

    public static void showPreviousMonthReport() {
        ArrayList<Transaction> transactions = loadTransactions();

        LocalDate previousMonth = LocalDate.now().minusMonths(1);
        int month = previousMonth.getMonthValue();
        int year = previousMonth.getYear();

        for (Transaction transaction : transactions) {
            LocalDate transactionDate = LocalDate.parse(transaction.getDate());

            if (transactionDate.getMonthValue() == month && transactionDate.getYear() == year) {
                System.out.println(transaction);
            }
        }
    }

    public static void showYearToDateReport() {
        ArrayList<Transaction> transactions = loadTransactions();

        int currentYear = LocalDate.now().getYear();

        for (Transaction transaction : transactions) {
            LocalDate transactionDate = LocalDate.parse(transaction.getDate());

            if (transactionDate.getYear() == currentYear) {
                System.out.println(transaction);
            }
        }
    }

    public static void showPreviousYearReport() {
        ArrayList<Transaction> transactions = loadTransactions();

        int previousYear = LocalDate.now().minusYears(1).getYear();

        for (Transaction transaction : transactions) {
            LocalDate transactionDate = LocalDate.parse(transaction.getDate());

            if (transactionDate.getYear() == previousYear) {
                System.out.println(transaction);
            }
        }
    }

    public static void searchByVendor() {
        System.out.print("Enter vendor name: ");
        String vendorSearch = input.nextLine().trim().toLowerCase();

        ArrayList<Transaction> transactions = loadTransactions();

        for (Transaction transaction : transactions) {
            if (transaction.getVendor().toLowerCase().contains(vendorSearch)) {
                System.out.println(transaction);
            }
        }
    }
}






    
