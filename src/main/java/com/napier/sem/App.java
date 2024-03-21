package com.napier.sem;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class App
{
    /**
     * This is a test
     * @param args
     */

    public static void main(String[] args)
    {
        // Connect to MongoDB
        // Connect to MongoDB
        MongoClient mongoClient = new MongoClient("mongo-dbserver");
        // Get a database - will create when we use it
        MongoDatabase database = mongoClient.getDatabase("mydb");
        // Get a collection from the database
        MongoCollection<Document> collection = database.getCollection("test");
        // Create a document to store
        Document doc = new Document("name", "Callum Sim")
                .append("class", "Software Engineering Methods")
                .append("year", "2024")
                .append("result", new Document("CW", 95).append("EX", 85));
        // Add document to collection
        collection.insertOne(doc);

        // Check document in collection
        Document myDoc = collection.find().first();
        System.out.println(myDoc.toJson());

        // Create new Query_22 instance
        Query_22 query22 = new Query_22();

        // Connect to database
        query22.connect();

        // Extract top populated countries information in Europe
        ArrayList<Country> countries = query22.getTopPopulatedCountriesInRegion("Europe");

        // Test the size of the returned data
        System.out.println("Number of top populated countries in Europe: " + countries.size());

        // Disconnect from database
        query22.disconnect();

    }
}