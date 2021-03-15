package server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.mongodb.rx.client.MongoClients;
import entity.Currency;
import entity.Product;
import entity.User;
import io.reactivex.netty.protocol.http.server.HttpServer;
import mongo.MongoDriver;
import rx.Observable;

/**
 * @author friendlyevil
 */
public class Server {
    private final MongoDriver mongoDriver;
    private final Map<Action, Function<Map<String, List<String>>, Observable<String>>> actions;

    public Server() {
        mongoDriver = new MongoDriver(MongoClients.create());

        actions = new HashMap<>();
        actions.put(Action.REGISTER, this::register);
        actions.put(Action.ADD, this::add);
        actions.put(Action.GET, this::get);
    }

    public void start(int port) {
        HttpServer
                .newServer(port)
                .start((req, resp) -> {
                    String action = req.getDecodedPath().substring(1);
                    try {
                        Observable<String> result =
                                actions.get(Action.valueOf(action.toUpperCase())).apply(req.getQueryParameters());
                        return resp.writeString(result);
                    } catch (Exception e) {
                        return resp.writeString(Observable.just("something wrong"));
                    }
                }).awaitShutdown();
    }

    public Observable<String> register(Map<String, List<String>> queryParams) {
        long id = Integer.parseInt(queryParams.get("id").get(0));
        Currency currency = Currency.valueOf(queryParams.get("currency").get(0).toUpperCase());

        return mongoDriver.createUser(new User(id, currency)).map(s -> "User was created");
    }

    public Observable<String> add(Map<String, List<String>> queryParams) {
        String name = queryParams.get("name").get(0);
        double price = Double.parseDouble(queryParams.get("price").get(0));
        Currency currency = Currency.valueOf(queryParams.get("currency").get(0).toUpperCase());

        return mongoDriver.createProduct(new Product(name, price, currency)).map(s -> "Product was created");
    }

    public Observable<String> get(Map<String, List<String>> queryParams) {
        long id = Integer.parseInt(queryParams.get("id").get(0));

        return mongoDriver.getUserById(id)
                .map(User::getCurrency)
                .flatMap(userCurrency -> mongoDriver.getAllProducts()
                        .map(product -> product.getName() + " = " +
                                product.getCurrency().convert(product.getPrice(), userCurrency) + userCurrency.toString() + "\n"))
                .defaultIfEmpty("user don't exist or product list is empty");
    }
}
