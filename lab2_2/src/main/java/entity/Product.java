package entity;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.bson.Document;

/**
 * @author friendlyevil
 */
@Value
@AllArgsConstructor
public class Product implements MongoEntity {
    String name;
    double price;
    Currency currency;

    public Product(Document document) {
        name = document.getString("name");
        price = document.getDouble("price");
        currency = Currency.valueOf(document.getString("currency"));
    }


    @Override
    public Document toDocument() {
        return new Document().append("name", name).append("price", price).append("currency", currency.toString());
    }
}
