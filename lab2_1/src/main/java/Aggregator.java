import java.util.List;
import java.util.concurrent.CompletableFuture;

import actor.MasterActor;
import actor.SearchSystemConfiguration;
import actor.SearchSystemResult;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import scala.concurrent.duration.Duration;
import search.SearchException;
import search.SearchServer;

/**
 * @author friendlyevil
 */
public class Aggregator {
    private final SearchServer searchServer;
    private final List<SearchSystemConfiguration> configurations;

    public Aggregator(SearchServer searchServer, List<SearchSystemConfiguration> configurations) {
        this.searchServer = searchServer;
        this.configurations = configurations;
    }


    public List<SearchSystemResult> aggregate(String query, Duration timeout) {
        ActorSystem actorSystem = ActorSystem.create("search-actor-system");

        CompletableFuture<List<SearchSystemResult>> result = new CompletableFuture<>();

        ActorRef masterActor = actorSystem.actorOf(Props.create(MasterActor.class, searchServer, configurations,
                timeout, result));
        masterActor.tell(query, ActorRef.noSender());
        try {
            return result.get();
        } catch (Exception e) {
            throw new SearchException();
        } finally {
            actorSystem.terminate();
        }
    }
}
