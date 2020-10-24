package api;


import client.UrlReader;
import com.xebialabs.restito.server.StubServer;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Consumer;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.status;
import static com.xebialabs.restito.semantics.Action.stringContent;
import static com.xebialabs.restito.semantics.Condition.method;


public class HashTagStatisticsStubServerTest {

    private static final int PORT = 3535;

    private UrlReader urlReader;

    @Test
    public void goodTest() {
        withStubServer(PORT, s -> {
                    whenHttp(s)
                            .match(method(Method.GET))
                            .then(stringContent("result"));

                    urlReader = new UrlReader(String.format("http://localhost:%d/test", PORT));
                    String body = urlReader.getBody();
                    Assert.assertEquals(body, "result");
                }
        );
    }

    @Test(expected = UncheckedIOException.class)
    public void test404() {
        withStubServer(PORT, s -> {
                    whenHttp(s)
                            .match(method(Method.GET))
                            .then(status(HttpStatus.BAD_REQUEST_400));

                    urlReader = new UrlReader(String.format("http://localhost:%d/test", PORT));
                    Assert.assertEquals(urlReader.getStatusCode(), 400);
                    urlReader.getBody();
                }
        );
    }

    private void withStubServer(int port, Consumer<StubServer> callback) {
        StubServer stubServer = null;
        try {
            stubServer = new StubServer(port).run();
            callback.accept(stubServer);
        } finally {
            if (stubServer != null) {
                stubServer.stop();
            }
        }
    }
}