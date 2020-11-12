package statistic;

import clock.FakeClock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

public class EventStatisticImplTest {
    private FakeClock clock;
    private EventsStatistic eventsStatistic;
    private static final double DOUBLE_DELTA = 1e-12;

    @Before
    public void before() {
        clock = new FakeClock(Instant.now());
        eventsStatistic = new EventStatisticImpl(clock);
    }

    private void checkEventStatistic(String name, double expected) {
        Assert.assertEquals(eventsStatistic.getEventStatisticByName(name), expected, DOUBLE_DELTA);
    }

    private void checkAllEventStatistics(Map<String, Double> expected) {
        Map<String, Double> events = eventsStatistic.getAllEventStatistic();
        Assert.assertEquals(events.size(), expected.size());
        expected.forEach((name, res) -> Assert.assertEquals(events.get(name), res, DOUBLE_DELTA));
    }

    @Test
    public void emptyTest() {
        checkEventStatistic("event", 0.0);
        checkAllEventStatistics(Map.of());
    }

    @Test
    public void simpleTest() {
        eventsStatistic.incEvent("event");
        eventsStatistic.incEvent("event");
        checkEventStatistic("event", 2.0 / 60);
        checkAllEventStatistics(Map.of("event", 2.0 / 60));
    }

    @Test
    public void simpleExpiredTest() {
        eventsStatistic.incEvent("event");
        clock.addTime(Duration.ofMinutes(60));
        checkEventStatistic("event", 0);
        checkAllEventStatistics(Map.of());
    }


    @Test
    public void expiredEventTest() {
        eventsStatistic.incEvent("event");
        checkEventStatistic("event", 1.0 / 60);
        clock.addTime(Duration.ofMinutes(40));
        eventsStatistic.incEvent("event");
        checkEventStatistic("event", 2.0 / 60);
        clock.addTime(Duration.ofMinutes(40));
        checkEventStatistic("event", 1.0 / 60);
    }

    @Test
    public void someTest() {
        eventsStatistic.incEvent("event1");
        eventsStatistic.incEvent("event2");
        eventsStatistic.incEvent("event2");
        clock.addTime(Duration.ofMinutes(40));
        eventsStatistic.incEvent("event2");
        clock.addTime(Duration.ofMinutes(40));
        checkEventStatistic("event1", 0);
        checkEventStatistic("event2", 1.0 / 60);
        checkAllEventStatistics(Map.of("event2", 1.0 / 60));
    }

}