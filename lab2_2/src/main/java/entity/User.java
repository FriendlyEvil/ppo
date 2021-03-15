package entity;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.bson.Document;

/**
 * @author friendlyevil
 */
@Value
@AllArgsConstructor
public class User implements MongoEntity {
    long id;
    Currency currency;

    public User(Document document) {
        id = document.getLong("id");
        currency = Currency.valueOf(document.getString("currency"));
    }

    @Override
    public Document toDocument() {
        return new Document().append("id", id).append("currency", currency.toString());
    }
}
