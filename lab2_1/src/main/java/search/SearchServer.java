package search;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author friendlyevil
 */
public class SearchServer {
    private final HttpServer httpServer;

    public SearchServer(HttpServer httpServer) {
        this.httpServer = httpServer;
    }

    public List<SearchResult> search(String url) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(httpServer.search(url), new TypeReference<List<SearchResult>>() {});
        } catch (JsonProcessingException e) {
            throw new ParseException();
        }
    }
}
