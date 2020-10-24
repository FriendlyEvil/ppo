package client;

import config.VKConfig;
import model.VKResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class VKHttpClient implements client.HttpClient<VKResponse> {
    private final VKConfig config;
    private final HttpClient client;
    private final String queryString;
    private static final String QUERY_TEMPLATE = "https://%s:%d/method/newsfeed.search?" +
            "q=%%%%23%%s" +
            "&start_time=%%d" +
            "&end_time=%%d" +
            "&access_token=%s" +
            "&v=%s";

    public VKHttpClient(VKConfig config) {
        this.config = config;
        this.queryString = String.format(QUERY_TEMPLATE, config.getHost(),
                config.getPort(), config.getToken(), config.getVersion());
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    @Override
    public VKResponse query(String query, long startTime, long endTime) {
        String body = new UrlReader(String.format(queryString, query, startTime, endTime)).getBody();
        return JsonBodyHandler.parseJson(body, VKResponse.class);
//        return queryWithHttpClient(query, startTime, endTime);
    }

    public VKResponse queryWithHttpClient(String query, long startTime, long endTime) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format(queryString, query, startTime, endTime)))
                .timeout(Duration.ofMinutes(2))
                .GET()
                .build();

        return client.sendAsync(request, new JsonBodyHandler<>(VKResponse.class))
                .thenApply(HttpResponse::body)
                .completeOnTimeout(new VKResponse(), config.getTimeout(), TimeUnit.SECONDS)
                .join();
    }
}
