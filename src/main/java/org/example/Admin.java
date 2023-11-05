package org.example;

import java.sql.*;
import java.util.Scanner;

public class Admin {
    public void createFlight() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter flight number: ");
        String flight_number = scanner.next();
        System.out.println("Enter departure city: ");
        String from_city = scanner.next();
        System.out.println("Enter destination city: ");
        String to_city = scanner.next();
        System.out.println("Enter departure date: ");
        String departure_date = scanner.next();
        System.out.println("Enter departure time: ");
        String departure_time = scanner.next();
        System.out.println("Enter arrival date: ");
        String arrival_date = scanner.next();
        System.out.println("Enter arrival time: ");
        String arrival_time = scanner.next();

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "aa");
            String sql = "INSERT INTO flights (flight_number, from_city, to_city, departure_date, departure_time, arrival_date, arrival_time) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, flight_number);
            preparedStatement.setString(2, from_city);
            preparedStatement.setString(3, to_city);
            preparedStatement.setString(4, departure_date);
            preparedStatement.setString(5, departure_time);
            preparedStatement.setString(6, arrival_date);
            preparedStatement.setString(7, arrival_time);
            int rowsInserted = preparedStatement.executeUpdate(); // = 1

            if (rowsInserted > 0) {
                System.out.println("A new flight has been created successfully.");
            }

            connection.close();
        } catch (SQLException e) {
            // Handle the exception
            e.printStackTrace(); // You can replace this with appropriate error handling
        }
    }

}
