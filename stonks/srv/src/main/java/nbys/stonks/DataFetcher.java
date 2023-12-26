package nbys.stonks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import nbys.stonks.cds.TickerHandler;

@Component
public class DataFetcher {
    String METADATA_KEY = "Meta Data";

    private static final Logger logger = LoggerFactory.getLogger(DataFetcher.class);

    private final WebClient webClient = WebClient.create();

    @Autowired
    private TickerHandler tickerHandler;

    @Value("${stonks.data.api.url}")
    private String URL;

    public String fetchIntraDayData(String symbol) {
        return webClient.get()
                .uri(URL)
                .retrieve()
                .bodyToMono(String.class).block();
    }

    public void startFetchingData() {
        while (true) {
            try {
                Thread.sleep(800000);
                logger.info("Fetching data...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
