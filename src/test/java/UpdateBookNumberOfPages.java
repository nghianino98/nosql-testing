import com.lordofthejars.nosqlunit.annotation.ShouldMatchDataSet;
import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.lordofthejars.nosqlunit.mongodb.ManagedMongoDb;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import model.Book;
import mongodb.BookManager;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;

import static com.lordofthejars.nosqlunit.mongodb.ManagedMongoDb.MongoServerRuleBuilder.newManagedMongoDbRule;
import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;

/**
 * @author minhpham on 23/10/2019
 * @project DatabaseUnitTesting
 */

 @UsingDataSet(locations = "initialData.json", loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
 public class UpdateBookNumberOfPages {
     @ClassRule
     public static ManagedMongoDb managedMongoDb = newManagedMongoDbRule().mongodPath("/usr").appendSingleCommandLineArguments("-vvv")
     .build();

     @Rule
    public MongoDbRule remoteMongoDbRule = newMongoDbRule().defaultManagedMongoDb("test");
    
    @Inject
    private MongoClient mongo;

    @Test
    @ShouldMatchDataSet(location = "expectedBookNumberOfPages.json")
    public void manager_should_update_book_title(){
        
        BookManager bookManager = new BookManager(bookCollection());
        
        bookManager.updateNumberOfPages("The Hobbit", 1234);
        
    }

    private DBCollection bookCollection() {
        return mongo.getDB("test").getCollection(Book.class.getSimpleName());
    }
    
 }

