package client;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class JsonBodyHandler<W> implements HttpResponse.BodyHandler<W> {

    private final Class<W> clazz;

    public JsonBodyHandler(Class<W> clazz) {
        this.clazz = clazz;
    }

    @Override
    public HttpResponse.BodySubscriber<W> apply(HttpResponse.ResponseInfo responseInfo) {
        return asJSON(clazz);
    }

    public static <T> T parseJson(String body, Class<T> targetType) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(objectMapper.readTree(body).get("response").toString(), targetType);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static <T> HttpResponse.BodySubscriber<T> asJSON(Class<T> targetType) {
        HttpResponse.BodySubscriber<String> upstream = HttpResponse.BodySubscribers.ofString(StandardCharsets.UTF_8);

        return HttpResponse.BodySubscribers.mapping(
                upstream,
                (String body) -> parseJson(body, targetType));
    }
}
