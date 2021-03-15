package actor;

import lombok.Value;

/**
 * @author friendlyevil
 */
@Value
public class SearchSystemConfiguration {
    String host;
    String path;
    String searchParam;
    int limit;
}
