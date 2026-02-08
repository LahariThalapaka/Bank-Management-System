package com.pst.atm.test;

import java.util.Scanner;
import com.pst.atm.service.ATM;

public class Test {

    public static void main(String[] args) {

        ATM atm = new ATM();
        Scanner sc = new Scanner(System.in);

        int choice;

        do {

            System.out.println("\n===== ONLINE ATM SYSTEM =====");
            System.out.println("1. Create New Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Balance Enquiry");
            System.out.println("5. Exit");
            System.out.println("Enter your choice:");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.println("Enter account number:");
                    int accNo = sc.nextInt();

                    System.out.println("Enter user name:");
                    String name = sc.next();

                    System.out.println("Enter Gender:");
                    String gender = sc.next();

                    System.out.println("Enter mobile number:");
                    long mobile = sc.nextLong();

                    System.out.println("Enter initial amount:");
                    double amount = sc.nextDouble();

                    System.out.println("Set 4-digit PIN:");
                    int pin = sc.nextInt();

                    atm.createNewAccount(accNo, name, gender, mobile, amount, pin);
                    break;

                case 2:
                    System.out.println("Enter account number:");
                    int depositAcc = sc.nextInt();

                    System.out.println("Enter PIN:");
                    int depositPin = sc.nextInt();

                    if (atm.verifyPin(depositAcc, depositPin)) {

                        System.out.println("Enter deposit amount:");
                        double depositAmt = sc.nextDouble();

                        atm.depositAmount(depositAcc, depositAmt);

                    } else {
                        System.out.println("Invalid PIN");
                    }
                    break;

                case 3:
                    System.out.println("Enter account number:");
                    int withdrawAcc = sc.nextInt();

                    System.out.println("Enter PIN:");
                    int withdrawPin = sc.nextInt();

                    if (atm.verifyPin(withdrawAcc, withdrawPin)) {

                        System.out.println("Enter withdraw amount:");
                        double withdrawAmt = sc.nextDouble();

                        atm.withdrawAmount(withdrawAcc, withdrawAmt);

                    } else {
                        System.out.println("Invalid PIN");
                    }
                    break;

                case 4:
                    System.out.println("Enter account number:");
                    int balanceAcc = sc.nextInt();

                    System.out.println("Enter PIN:");
                    int balancePin = sc.nextInt();

                    if (atm.verifyPin(balanceAcc, balancePin)) {

                        atm.balanceEnquiry(balanceAcc);

                    } else {
                        System.out.println("Invalid PIN");
                    }
                    break;

                case 5:
                    System.out.println("Thank you for using ATM");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 5);

        sc.close();
    }
}
