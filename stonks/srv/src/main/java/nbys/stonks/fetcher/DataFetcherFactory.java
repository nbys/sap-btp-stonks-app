package nbys.stonks.fetcher;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DataFetcherFactory {
    @Autowired
    private List<DataFetcher> handlers;

    public static final Map<String, DataFetcher> handlerCache = new HashMap<>();

    @PostConstruct
    public void initCache() {
        for (DataFetcher handler : handlers) {
            handlerCache.put(handler.getEntityType(), handler);
        }
    }

    public static DataFetcher getHandler(String label) {
        return handlerCache.get(label);
    }
}
