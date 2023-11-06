package org.example;

import java.sql.*;
import java.util.Scanner;

public class LogIn {
    Scanner cin = new Scanner(System.in);

    public void logInAs() {
        System.out.println("""
                I'm a:
                1. Customer
                2. Administrator""");
        int logIn = cin.nextInt();

        boolean isClear = true;
        while(isClear){
            if(logIn < 1 || logIn > 2) {
                System.out.println("Wrong option.");
                logIn = cin.nextInt();
            }
            else {
                isClear = false;
            }
        }

        switch (logIn) {
            case 1:
                logInAsCustomer();
                break;
            case 2:
                logInAsAdministrator();
                break;
        }
    }
    public void logInAsCustomer() {
        System.out.print("Enter your E-Mail: ");
        String email = cin.next();

        boolean isEmailExists = true;
        while (isEmailExists) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "aa");
                String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    if (count > 0) {
                        isEmailExists = false;
                    }
                    else {
                        System.err.println("Invalid E-Mail or yr not registered yet. Try again.");
                        email = cin.next();
                    }
                }

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        System.out.print("Enter password: ");
        String password = cin.next();

        boolean isPasswordExists = true;
        while (isPasswordExists) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "aa");
                String sql = "SELECT COUNT(*) FROM users WHERE password = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, password);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    if (count > 0) {
                        isPasswordExists = false;
                        System.out.println("\n You've successfully logged in.");
                    }
                    else {
                        System.err.println("Invalid Password. Try again.");
                        password = cin.next();
                    }
                }

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void logInAsAdministrator() {
        System.out.print("Enter your E-Mail: ");
        String email = cin.next();

        boolean isEmailExists = true;
        while (isEmailExists) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "aa");
                String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    if (count > 0) {
                        String checkRole = "SELECT COUNT(*) FROM users WHERE email = ? AND role = 'admin'";
                        PreparedStatement ps = connection.prepareStatement(checkRole);
                        ps.setString(1, email);
                        ResultSet rs = ps.executeQuery();

                        if (rs.next()) {
                            int countRole = rs.getInt(1);
                            if (countRole > 0) {
                                isEmailExists = false;
                            } else {
                                System.err.println("Invalid E-Mail or you are not an Admin. Try again.");
                                email = cin.next();
                            }
                        }
                    } else {
                        System.out.println("Invalid E-Mail or you are not registered yet. Try again.");
                        email = cin.next();
                    }
                }

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        System.out.print("Enter password: ");
        String password = cin.next();

        boolean isPasswordExists = true;
        while (isPasswordExists) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "aa");
                String sql = "SELECT COUNT(*) FROM users WHERE password = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, password);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    if (count > 0) {
                        isPasswordExists = false;
                        System.out.println("\n You've successfully logged in.");

                        boolean stillCreate = true;
                        while (stillCreate) {
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
                                stillCreate = false;
                            }
                        }
                    }
                    else {
                        System.err.println("Invalid Password. Try again.");
                        password = cin.next();
                    }
                }

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}