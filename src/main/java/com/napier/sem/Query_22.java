package com.napier.sem;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Query_22 {

    // Assuming 'con' is defined and initialized elsewhere in your class

    public ArrayList<Country> getTopPopulatedCountriesInRegion(String region) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Create string for SQL statement
            String strSelect =
                    "SELECT "
                            + "    city.name AS Name, "
                            + "    country.name AS Country, "
                            + "    city.population AS Population "
                            + "FROM "
                            + "    city "
                            + "JOIN "
                            + "    country ON city.ID = country.capital "
                            + "WHERE "
                            + "    country.continent = '" + region + "' "
                            + "ORDER BY "
                            + "    city.population DESC "
                            + "LIMIT 10";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Extract country information
            ArrayList<Country> countries = new ArrayList<>();
            while (rset.next()) {
                Country country = new Country();
                country.setName(rset.getString("Name"));
                country.setCountry(rset.getString("Country"));
                country.setPopulation(rset.getInt("Population"));
                countries.add(country);
            }
            return countries;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get top populated countries in region");
            return null;
        }
    }
}
