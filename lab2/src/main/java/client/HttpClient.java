package client;

public interface HttpClient<R> {
    R query(String query, long startTime, long endTime);
}
