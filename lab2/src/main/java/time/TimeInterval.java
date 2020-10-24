package time;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TimeInterval {
    public static List<Long> getTimeIntervals(long end, long duration, int count) {
        return IntStream.range(0, count).mapToLong(i -> end - (count - i) * duration).boxed().collect(Collectors.toList());
    }

    public static List<Long> getTimeIntervalsFromNow(Duration duration, int count) {
        return getTimeIntervals(System.currentTimeMillis() / 1000L, duration.toSeconds(), count);
    }
}
