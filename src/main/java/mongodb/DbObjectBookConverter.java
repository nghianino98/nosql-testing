package mongodb;

import ch.lambdaj.function.convert.Converter;
import com.mongodb.DBObject;
import model.Book;

import static mongodb.MongoDbBookConverter.NUM_PAGES_FIELD;
import static mongodb.MongoDbBookConverter.TITLE_FIELD;

public class DbObjectBookConverter implements
        Converter<DBObject, Book> {

    @Override
    public Book convert(DBObject dbObject) {

        String title = (String) dbObject.get(TITLE_FIELD);
        int numberOfPages = (Integer) dbObject.get(NUM_PAGES_FIELD);

        return new Book(title, numberOfPages);

    }

}
