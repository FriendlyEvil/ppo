import api.HashTagStatistics;
import client.HttpClient;
import client.VKHttpClient;
import config.VKConfig;
import model.VKResponse;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static config.PropertyLoader.loadProperties;

public class Main {
    private static final String PROPERTIES_FILENAME = "src/main/resources/application.properties";

    public static void main(String[] args) {
        HttpClient<VKResponse> client = new VKHttpClient(new VKConfig(loadProperties(PROPERTIES_FILENAME)));
        HashTagStatistics statistics = new HashTagStatistics(client);
        statistics.getStatistic("фото", 12).forEach(System.out::println);
    }
}
