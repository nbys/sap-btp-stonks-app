package nbys.stonks;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DataFetcher {
    private static final Logger logger = LoggerFactory.getLogger(DataFetcher.class);

    public void startFetchingData() {
        while (true) {
            try {
                Thread.sleep(1000);
                logger.info("Fetching data...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
