package actor;

import java.util.List;

import lombok.Value;
import search.SearchResult;

/**
 * @author friendlyevil
 */
@Value
public class SearchSystemResult {
    String searchSystem;
    List<SearchResult> results;
}
