//  Query 1: All the countries in the world organised by largest population to smallest
package com.napier.sem;

import java.sql.*;

public class Query_4
{
    public static void main(String[] args)
    {
        App a = new App();
        System.out.println("going in to connect");
        if (args.length < 1) {
            a.connect("localhost:33060", 10000);
        } else {
            a.connect(args[0], Integer.parseInt(args[1]));
        }



    }
    /**
     * Fetching all countries in the World largest to smallest, limited by 10
     */
    static Connection con = null;
    public void connect(String location, int delay) {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        boolean shouldWait = false;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                if (shouldWait) {
                    // Wait a bit for db to start
                    Thread.sleep(delay);
                }
                try {
                    // Establishing a connection to the database
                    Connection connection = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false", "root", "example");

                    // Creating and executing the SQL query
                    String query = "SELECT * FROM country ORDER BY population DESC LIMIT 10";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);

                    // Processing the results
                    while(resultSet.next()) {
                        // Accessing and printing data for each country
                        String countryName = resultSet.getString("name");
                        int population = resultSet.getInt("population");
                        System.out.println("Country: " + countryName + ", Population: " + population);
                    }

                    // Closing the resources
                    resultSet.close();
                    statement.close();
                    connection.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                // Connect to database
                System.out.println("Going in to  connect");
                con = DriverManager.getConnection("jdbc:mysql://" + location
                                + "/world?allowPublicKeyRetrieval=true&useSSL=false",
                        "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(sqle.getMessage());

                // Let's wait before attempting to reconnect
                shouldWait = true;
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }

    }


}