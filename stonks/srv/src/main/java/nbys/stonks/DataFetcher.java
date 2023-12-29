package nbys.stonks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.ArrayList; // Add missing import

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;

import nbys.stonks.ApplicationProperties.Api.IntraDay;
import nbys.stonks.cds.StonksHandler;

@Component
public class DataFetcher {
    private static final Logger logger = LoggerFactory.getLogger(DataFetcher.class);

    private final WebClient webClient = WebClient.create();

    @Autowired
    private StonksHandler stonksHandler;

    @Autowired
    private ApplicationProperties appProperties;

    public String fetchIntraDayData(
            String symbol,
            String URL,
            String method) {
        return webClient.method(HttpMethod.valueOf(method))
                .uri(URL)
                .retrieve()
                .bodyToMono(String.class).block();
    }

    public void startDataProcess() {
        while (true) {
            try {
                ArrayList<String> tickers = stonksHandler.getTickers();
                for (String ticker : tickers) {
                    logger.info(String.format("Fetching data for %s", ticker));

                    IntraDay intradayAPI = this.appProperties.getApi().getIntraDay();

                    String JSONString = fetchIntraDayData(ticker, intradayAPI.getUrl(),
                            intradayAPI.getMethod());
                    logger.info(JSONString);
                }
                Thread.sleep(this.appProperties.getRefreshTimeout());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
