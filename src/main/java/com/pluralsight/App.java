package com.pluralsight;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
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

    private static void showLedger() {
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
















}







    
