import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDbUtil {
    
    private static DB database;
    
    static{
            MongoClient mongo=new MongoClient("localhost",27017);
            database=mongo.getDB("test");
    }
    
    public static DBCollection getCollection(String collectionName){
        return database.getCollection(collectionName);
    }
}
