package com.napier.sem;

import java.sql.*;

public class Query_1 {
    static final int MAX_RETRIES = 10;

    public static void main(String[] args) {
        String location;
        int delay;

        if (args.length < 2) {
            location = "localhost:33060";
            delay = 10000;
        } else {
            location = args[0];
            delay = Integer.parseInt(args[1]);
        }

        Query_1 query1 = new Query_1();
        query1.connect(location, delay);
    }

    public void connect(String location, int delay) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            e.printStackTrace();
            return;
        }

        Connection connection = null;
        try {
            int retries = 0;
            boolean shouldWait = false;
            while (retries < MAX_RETRIES) {
                if (shouldWait) {
                    Thread.sleep(delay);
                }

                try {
                    connection = DriverManager.getConnection("jdbc:mysql://" + location + "/world?useSSL=false", "root", "example");

                    String query = "SELECT * FROM country ORDER BY population DESC";
                    try (Statement statement = connection.createStatement();
                         ResultSet resultSet = statement.executeQuery(query)) {
                        while (resultSet.next()) {
                            String countryName = resultSet.getString("name");
                            int population = resultSet.getInt("population");
                            System.out.println("Country: " + countryName + ", Population: " + population);
                        }
                    }
                    break; // Successful retrieval, break out of retry loop
                } catch (SQLException e) {
                    System.out.println("Failed to connect to database attempt " + (retries + 1));
                    System.out.println(e.getMessage());
                    shouldWait = true;
                }

                retries++;
            }
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted? Should not happen.");
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
