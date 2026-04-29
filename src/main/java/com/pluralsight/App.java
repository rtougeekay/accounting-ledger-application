package com.pluralsight;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
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
            String description = App.input.nextLine().trim();

            System.out.print("Enter vendor: ");
            String vendor = App.input.nextLine().trim();

            System.out.print("Enter amount: ");
            double amount = Double.parseDouble(App.input.nextLine().trim());

            if (!isDeposit) {
                amount = amount * -1;
            }

            String date = java.time.LocalDate.now().toString();
            String time = java.time.LocalTime.now().withNano(0).toString();

            Transaction transaction = new Transaction(date, time, description, vendor, amount);

            FileWriter fileWriter = new FileWriter(fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(transaction.toCsvLine());
            bufferedWriter.newLine();

            bufferedWriter.close();

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
                String[] parts = line.split("\\|");

                String date = parts[0];
                String time = parts[1];
                String description = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);

                Transaction transaction = new Transaction(date, time, description, vendor, amount);
                transactions.add(transaction);

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
                    displayTransactions();
                    break;
                case "D":
                    displayTransactions();
                    break;
                case "P":
                    displayTransactions();
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

    private static void showReports() {
    }

    private static void displayTransactions() {
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
}







    
