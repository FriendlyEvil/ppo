package api;

import client.HttpClient;
import model.VKResponse;
import time.TimeInterval;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HashTagStatistics {
    private static final Duration DEFAULT_DURATION = Duration.ofHours(1);
    private final Duration duration;
    private final HttpClient<VKResponse> httpClient;

    public HashTagStatistics(HttpClient<VKResponse> httpClient) {
        this(httpClient, DEFAULT_DURATION);
    }

    public HashTagStatistics(HttpClient<VKResponse> httpClient, Duration duration) {
        this.httpClient = httpClient;
        this.duration = duration;
    }

    public List<Integer> getStatistic(String hashTag, int hoursCount) {
        if (hoursCount < 1 || hoursCount > 24) {
            throw new IllegalArgumentException("'hoursCount' must be between 1 and 24");
        }
        List<Long> timeIntervals = TimeInterval.getTimeIntervalsFromNow(duration, hoursCount + 1);
        return IntStream.range(0, hoursCount).parallel()
                .mapToObj(i -> httpClient.query(hashTag, timeIntervals.get(i), timeIntervals.get(i + 1)))
                .map(VKResponse::getCount)
                .collect(Collectors.toList());
    }
}
