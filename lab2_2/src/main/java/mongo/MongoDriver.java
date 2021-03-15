package mongo;


import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.Success;
import entity.Product;
import entity.User;
import rx.Observable;

/**
 * @author friendlyevil
 */
public class MongoDriver {
    private final MongoClient client;

    private static final String DATABASE = "lab2";
    private static final String USER_COLLECTION = "users";
    private static final String PRODUCT_COLLECTION = "products";

    public MongoDriver(MongoClient client) {
        this.client = client;
    }

    public Observable<Success> createUser(User user) {
        return client.getDatabase(DATABASE)
                .getCollection(USER_COLLECTION)
                .insertOne(user.toDocument());
    }

    public Observable<User> getUserById(long id) {
        return client.getDatabase(DATABASE)
                .getCollection(USER_COLLECTION)
                .find(Filters.eq("id", id))
                .toObservable()
                .map(User::new);
    }

    public Observable<Success> createProduct(Product product) {
        return client.getDatabase(DATABASE)
                .getCollection(PRODUCT_COLLECTION)
                .insertOne(product.toDocument());
    }

    public Observable<Product> getAllProducts() {
        return client.getDatabase(DATABASE)
                .getCollection(PRODUCT_COLLECTION)
                .find()
                .toObservable()
                .map(Product::new);
    }
}
