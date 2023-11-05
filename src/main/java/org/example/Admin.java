package org.example;

import Observer.Observer;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Admin implements Observer {
    private int idDB = 0;
    // nextval('flights_id_seq'::regclass)
    public void createFlight() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter flight number: ");
        String flight_number = scanner.next();
        System.out.println("Enter departure city: ");
        String from_city = scanner.next();
        System.out.println("Enter destination city: ");
        String to_city = scanner.next();
//        LocalDateTime localDateTime = LocalDateTime.of(2023,11,10,17,10);
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
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT currval('flights_id_seq'::regclass)");
                if (rs.next()) {
                    idDB = rs.getInt(1);
                }

                System.out.println("A new flight has been created successfully.");
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleEvent() throws SQLException {
        System.out.println("Dear consumers, we have some new flights");
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "aa");
            String sql = "SELECT * FROM flights WHERE id = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, idDB);

            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                System.out.println("Flight number -> " + resultSet.getString("flight_number") + "\n" +
                        "The flight from " + resultSet.getString("from_city") + " in " + resultSet.getString("departure_date") + " at " + resultSet.getString("departure_time") + "\n" +
                        "To " + resultSet.getString("to_city") + " in " + resultSet.getString("arrival_date") + " at " + resultSet.getString("arrival_time") );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
