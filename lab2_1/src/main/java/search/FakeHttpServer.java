package search;

import java.util.UUID;

/**
 * @author friendlyevil
 */
public class FakeHttpServer implements HttpServer {
    @Override
    public String search(String url) {
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < 10; i++) {
            builder.append(getRandomAnswer()).append(", ");
        }
        builder.append(getRandomAnswer()).append("]");
        return builder.toString();
    }

    private String getRandomAnswer() {
        return "{\"title\": \"" + UUID.randomUUID() + "\", \"url\": \"" + UUID.randomUUID() + "\", \"description\": \"" + UUID.randomUUID() + "\"}";
    }
}
