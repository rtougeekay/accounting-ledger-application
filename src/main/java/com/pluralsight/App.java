package com.pluralsight;

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

    private static void addTransaction(boolean b) {

    }


}







    
