package client;

import org.apache.maven.surefire.shade.org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class UrlReader implements AutoCloseable {
    private final HttpURLConnection connection;

    public UrlReader(String query) {
        try {
            URL url = new URL(query);
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public int getStatusCode() {
        try {
            return connection.getResponseCode();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public String getBody() {
        try {
            return IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void close() {
        if (connection != null) {
            connection.disconnect();
        }
    }
}
