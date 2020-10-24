package api;


import client.HttpClient;
import model.VKResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;


public class HashTagStatisticsTest {
    private HashTagStatistics statistics;

    @Mock
    private HttpClient<VKResponse> httpClient;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        statistics = new HashTagStatistics(httpClient);
    }

    @Test
    public void testZeroResult() {
        when(httpClient.query(anyString(), anyLong(), anyLong())).thenReturn(new VKResponse(0));
        List<Integer> results = statistics.getStatistic("tag", 1);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(Integer.valueOf(0), results.get(0));
    }

    @Test
    public void testOneAnswer() {
        when(httpClient.query(anyString(), anyLong(), anyLong())).thenReturn(new VKResponse(42));
        List<Integer> results = statistics.getStatistic("tag", 1);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(Integer.valueOf(42), results.get(0));
    }

    @Test
    public void testSomeResult() {
        when(httpClient.query(anyString(), anyLong(), anyLong())).thenReturn(new VKResponse(2));
        List<Integer> results = statistics.getStatistic("tag", 12);
        Assert.assertEquals(12, results.size());
        Assert.assertEquals(Integer.valueOf(2), results.get(0));
    }
}