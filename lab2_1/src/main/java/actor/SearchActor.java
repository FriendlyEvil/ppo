package actor;

import java.util.List;
import java.util.stream.Collectors;

import akka.actor.UntypedActor;
import lombok.AllArgsConstructor;
import search.SearchResult;
import search.SearchServer;

/**
 * @author friendlyevil
 */
@AllArgsConstructor
public class SearchActor extends UntypedActor {
    private final static String URL_FORMAT = "https://%s/%s/?%s=%s";

    SearchServer searchServer;
    SearchSystemConfiguration configuration;


    @Override
    public void onReceive(Object o) {
        if (!(o instanceof String)) {
            throw new IllegalArgumentException();
        }

        List<SearchResult> result = searchServer.search(String.format(URL_FORMAT, configuration.getHost(),
                configuration.getPath(), configuration.getSearchParam(), o)).stream()
                .limit(configuration.getLimit())
                .collect(Collectors.toList());


        try {
            sender().tell(new SearchSystemResult(configuration.getHost(), result), getSelf());
        } catch (Exception ignored) {
        }
    }
}
