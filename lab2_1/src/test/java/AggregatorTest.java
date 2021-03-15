import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import actor.SearchSystemConfiguration;
import actor.SearchSystemResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import scala.concurrent.duration.Duration;
import search.FakeHttpServer;
import search.SearchServer;

import static org.mockito.Matchers.anyString;

/**
 * @author friendlyevil
 */
@RunWith(MockitoJUnitRunner.class)
public class AggregatorTest {
    private static final SearchSystemConfiguration yandex = new SearchSystemConfiguration("yandex", "search", "text",
            5);
    private static final SearchSystemConfiguration google = new SearchSystemConfiguration("google", "search", "q", 5);
    private static final SearchSystemConfiguration bing = new SearchSystemConfiguration("bing", "search", "q", 5);

    private final Duration ZERO = Duration.create(0, TimeUnit.SECONDS);
    private final Duration ONE_SECOND = Duration.create(1, TimeUnit.SECONDS);
    private final Duration FIVE_SECOND = Duration.create(5, TimeUnit.SECONDS);

    @Mock
    private FakeHttpServer httpServer;

    @Before
    public void before() {
        httpServer = new FakeHttpServer();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test() {
        Aggregator aggregator = new Aggregator(new SearchServer(httpServer), List.of(yandex, google, bing));
        mockWithTimeout(ZERO, ZERO, ZERO);
        List<SearchSystemResult> result = aggregator.aggregate("query", ONE_SECOND);

        Assert.assertEquals(result.size(), 3);
        Assert.assertEquals(result.get(0).getResults().size(), 5);
        Assert.assertEquals(result.get(1).getResults().size(), 5);
        Assert.assertEquals(result.get(2).getResults().size(), 5);

        Set<String> systems = result.stream().map(SearchSystemResult::getSearchSystem).collect(Collectors.toSet());
        Assert.assertTrue(systems.contains(yandex.getHost()));
        Assert.assertTrue(systems.contains(google.getHost()));
        Assert.assertTrue(systems.contains(bing.getHost()));
    }

    @Test
    public void slowTest() {
        Aggregator aggregator = new Aggregator(new SearchServer(httpServer), List.of(yandex, google, bing));
        mockWithTimeout(ZERO, ONE_SECOND, FIVE_SECOND);
        List<SearchSystemResult> result = aggregator.aggregate("query", ONE_SECOND);

        Assert.assertEquals(result.size(), 2);
        Assert.assertEquals(result.get(0).getResults().size(), 5);
        Assert.assertEquals(result.get(0).getSearchSystem(), yandex.getHost());
        Assert.assertEquals(result.get(1).getResults().size(), 5);
        Assert.assertEquals(result.get(1).getSearchSystem(), google.getHost());
    }

    private void mockWithTimeout(Duration yandexTimeout, Duration googleTimeout, Duration bingTimeout) {
        Mockito.when(httpServer.search(anyString())).thenAnswer(i -> {
            String url = i.getArgumentAt(0, String.class);
            Thread.sleep(getTimeout(url, yandexTimeout, googleTimeout, bingTimeout).toMillis());
            return i.callRealMethod();
        });
    }

    private Duration getTimeout(String url, Duration yandexTimeout, Duration googleTimeout, Duration bingTimeout) {
        if (isSystem(url, yandex.getHost())) {
            return yandexTimeout;
        } else if (isSystem(url, google.getHost())) {
            return googleTimeout;
        } else if (isSystem(url, bing.getHost())) {
            return bingTimeout;
        }
        return ZERO;
    }

    private boolean isSystem(String url, String system) {
        return url.contains("http://" + system) || url.contains("https://" + system);
    }
}