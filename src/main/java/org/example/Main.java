package org.example;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);

        while (true) {
            System.out.println("""
                Welcome to BYE Airlines! Select one of the options:
                
                1. Sign up
                2. Log in
                0. Exit
                """);

            int choice = cin.nextInt();

            switch (choice) {

                case 1:
                    SignUp signUp = new SignUp();
                    signUp.SignAs();
                case 2:
                    LogIn logIn = new LogIn();
                    logIn.logInAs();
                case 0:
                    System.exit(0);
                default:
                    System.out.println("Wrong option. Enter in exist one");
            }
        }
    }
}
