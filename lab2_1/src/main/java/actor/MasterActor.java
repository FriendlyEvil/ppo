package actor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import akka.actor.Props;
import akka.actor.ReceiveTimeout;
import akka.actor.UntypedActor;
import scala.concurrent.duration.Duration;
import search.SearchServer;

/**
 * @author friendlyevil
 */

public class MasterActor extends UntypedActor {
    private final SearchServer searchServer;
    private final List<SearchSystemConfiguration> configurations;
    private final List<SearchSystemResult> results = new ArrayList<>();
    private final CompletableFuture<List<SearchSystemResult>> futureResult;

    public MasterActor(SearchServer searchServer, List<SearchSystemConfiguration> configurations, Duration timeout,
                       CompletableFuture<List<SearchSystemResult>> futureResult) {
        this.searchServer = searchServer;
        this.configurations = configurations;
        this.futureResult = futureResult;
        getContext().setReceiveTimeout(timeout);
    }

    @Override
    public void onReceive(Object o) {
        if (o instanceof String) {
            configurations.forEach(config -> getContext().actorOf(Props.create(SearchActor.class, searchServer, config))
                    .tell(o, getSelf()));
        } else if (o instanceof SearchSystemResult) {
            results.add((SearchSystemResult) o);

            if (results.size() == configurations.size()) {
                sendResult();
            }
        } else if (o instanceof ReceiveTimeout) {
            sendResult();
        }

    }

    private void sendResult() {
        futureResult.complete(results);
    }
}
