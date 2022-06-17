package dao;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

// shouldn't need to change anything here
public class MongoConnection {

    private static MongoClient mongoClient = new MongoClient("localhost", 27017);

    public static MongoDatabase getDb(){
        return mongoClient.getDatabase("UsersDatabase");
    }

    public static MongoCollection<Document> getCollection(String collectionName){
        return getDb().getCollection(collectionName);
    }
}
