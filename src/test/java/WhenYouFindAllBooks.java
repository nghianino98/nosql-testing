import static com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb.InMemoryMongoRuleBuilder.newInMemoryMongoDbRule;
import static com.lordofthejars.nosqlunit.mongodb.ManagedMongoDb.MongoServerRuleBuilder.newManagedMongoDbRule;
import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIn.isIn;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.lordofthejars.nosqlunit.mongodb.ManagedMongoDb;
import com.mongodb.MongoClient;
import model.Book;
import mongodb.BookManager;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;

import com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

@UsingDataSet(locations = "initialData.json", loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
public class WhenYouFindAllBooks {


//    @ClassRule
//    public static final InMemoryMongoDb IN_MEMORY_MONGO_DB = newInMemoryMongoDbRule().build();
    
    @ClassRule
    public static ManagedMongoDb managedMongoDb = newManagedMongoDbRule().mongodPath("/usr").appendSingleCommandLineArguments("-vvv")
            .build();
    
//    @Rule
//    public MongoDbRule remoteMongoDbRule = newMongoDbRule().defaultEmbeddedMongoDb("test");
    
    @Rule
    public MongoDbRule remoteMongoDbRule = newMongoDbRule().defaultManagedMongoDb("test");
    
    @Inject
    private MongoClient mongo;
    
    @Test
    public void manager_should_return_all_inserted_books() {
        
        BookManager bookManager = new BookManager(bookCollection());
        List<Book> books = bookManager.findAll();
        
//        Book expectedBook = new Book("The Hobbit", 293);
//
//        assertThat(books, hasSize(1));
//        assertThat(expectedBook, isIn(books));
    
        Book expectedBook1 = new Book("The Hobbit", 293);
        Book expectedBook2 = new Book("Harry Potter and the Philosopher's Stone", 293);
    
        List<Book> expectedBooks = new ArrayList<Book>();
    
        expectedBooks.add(expectedBook1);
        expectedBooks.add(expectedBook2);
    
        assertThat(books, hasSize(2));
    
        for(Book expectedBook : expectedBooks){
        
            assertThat(expectedBook, isIn(books));
        
        }
        
    }
    
    @Test
    @UsingDataSet(loadStrategy = LoadStrategyEnum.DELETE_ALL)
    public void manager_should_return_empty_list_when_no_books() {
        
        BookManager bookManager = new BookManager(bookCollection());
        List<Book> books = bookManager.findAll();
        
        assertThat(books, hasSize(0));
        
    }
    
    private DBCollection bookCollection() {
        return mongo.getDB("test").getCollection(Book.class.getSimpleName());
    }
}
