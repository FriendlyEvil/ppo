package config;

import java.util.Properties;

public class VKConfig {
    private static final String HOST_PROPERTY_NAME = "host";
    private static final String PORT_PROPERTY_NAME = "port";
    private static final String TOKEN_PROPERTY_NAME = "token";
    private static final String VERSION_PROPERTY_NAME = "version";
    private static final String TIMEOUT_PROPERTY_NAME = "timeout";
    private static final long DEFAULT_TIMEOUT = 5;

    private final String host;
    private final int port;
    private final String token;
    private final String version;
    private final long timeout;

    public VKConfig(Properties properties) {
        host = getOrThrow(properties, HOST_PROPERTY_NAME);
        port = getOrThrowInt(properties, PORT_PROPERTY_NAME);
        token = getOrThrow(properties, TOKEN_PROPERTY_NAME);
        version = getOrThrow(properties, VERSION_PROPERTY_NAME);
        timeout = getOrDefault(properties, TIMEOUT_PROPERTY_NAME, DEFAULT_TIMEOUT);
    }


    private String getOrThrow(Properties properties, String propertyName) {
        String value = properties.getProperty(propertyName);
        if (value == null) {
            throw new PropertyNotFoundException(propertyName);
        }
        return value;
    }

    private long getOrDefault(Properties properties, String propertyName, long defaultValue) {
        String value = properties.getProperty(propertyName);
        if (value == null) {
            return defaultValue;
        }
        return Long.parseLong(value);
    }

    private int getOrThrowInt(Properties properties, String propertyName) {
        String result = getOrThrow(properties, propertyName);
        return Integer.parseInt(result);
    }


    public String getToken() {
        return token;
    }

    public String getVersion() {
        return version;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public long getTimeout() {
        return timeout;
    }
}
