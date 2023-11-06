package org.example;


import Database.DatabaseConnection;

import java.sql.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp {
    Scanner cin = new Scanner(System.in);

    public void SignAs() {
        System.out.println("""
                I'm a:
                1. Customer
                2. Administrator""");
        int signUp = cin.nextInt();

        boolean isClear = true;
        while(isClear){
            if(signUp < 1 || signUp > 2) {
                System.out.println("Wrong option.");
                signUp = cin.nextInt();
            }
            else {
                isClear = false;
            }
        }

        switch (signUp) {
            case 1:
                signAsCustomer();
                break;
            case 2:
                signAsAdministrator();
                break;
        }
    }


    public void signAsCustomer() {
        System.out.print("Enter Your First Name: ");
        String firstName = cin.next();
        System.out.print("Enter Your Last Name: ");
        String lastName = cin.next();
        System.out.println("Enter Your E-Mail");
        String email = cin.next();
        while (!isValidEmail(email)){
            System.err.printf("Error: Invalid email address");
            System.out.println();
            System.out.println("Enter Your E-Mail");
            email = cin.next();
        }

        boolean isEmailClear = true;
        while (isEmailClear) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "aa");
                String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (((ResultSet) resultSet).next()) {
                    int count = resultSet.getInt(1);
                    if (count > 0) {
                        System.err.println("Given E-Mail is already exists. Please enter other E-Mail.");
                        email = cin.next();
                    }
                    else {
                        isEmailClear = false;
                    }
                }

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Create a Password");
        String password = cin.next();

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "aa")) {
            String sql = "INSERT INTO users (first_name, last_name, email, password, role) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, "user");

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User registration successful.");
            } else {
                System.err.println("User registration failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void signAsAdministrator() {
            System.out.print("Enter Your First Name: ");
            String firstName = cin.next();
            System.out.print("Enter Your Last Name: ");
            String lastName = cin.next();
            System.out.println("Enter Your E-Mail");
            String email = cin.next();
            while (!isValidEmail(email)){
                System.err.printf("Error: Invalid email address");
                System.out.println();
                System.out.println("Enter Your E-Mail");
                email = cin.next();
            }


        boolean isEmailClear = true;
        while (isEmailClear) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "aa");
                String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (((ResultSet) resultSet).next()) {
                    int count = resultSet.getInt(1);
                    if (count > 0) {
                        System.err.println("Given E-Mail is already exists. Please enter other E-Mail.");
                        email = cin.next();
                    }
                    else {
                        isEmailClear = false;
                    }
                }

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Create a Password");
        String password = cin.next();

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "aa")) {
            String sql = "INSERT INTO users (first_name, last_name, email, password, role) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, "admin");

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User registration successful.");

                System.out.println("""
                                1. Create a flight
                                2. Close""");

                while (true) {
                    System.out.println("""
                                1. Create a flight
                                2. Close""");
                    int adminChoose = cin.nextInt();
                    if (adminChoose == 1) {
                        Admin admin = new Admin();
                        admin.createFlight();
                        admin.handleEvent();
                    }
                    else {
                        break;
                    }
                }
            } else {
                System.err.println("User registration failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("(^[A-Za-z0-9+_.-]+@(.+)$)");
        Matcher matcher = pattern.matcher(email);
        if (matcher.find()) return true;
        return false;
    }
}
