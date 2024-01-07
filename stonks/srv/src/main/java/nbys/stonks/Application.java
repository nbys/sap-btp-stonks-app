package nbys.stonks;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import nbys.stonks.cds.StonksHandler;
import nbys.stonks.fetcher.DataFetcher;
import nbys.stonks.fetcher.DataFetcherFactory;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sap.cds.CdsData;

@SpringBootApplication
public class Application implements ApplicationRunner {
	private static final Logger logger = LoggerFactory.getLogger(DataFetcher.class);

	@Autowired
	private ApplicationProperties properties;

	@Autowired
	private StonksHandler stonksHandler;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		new Thread() {
			public void run() {
				while (true) {
					try {
						ArrayList<String> tickers = stonksHandler.getTickers();
						for (String ticker : tickers) {
							DataFetcherFactory.handlerCache.forEach((label, handler) -> {
r								logger.info(String.format("Fetching data for %s", label));
								String JSONString = handler.fetchFromAPI(ticker);
								try {
									List<CdsData> data = handler.unmarhal(JSONString);
									handler.persist(data);
								} catch (JsonProcessingException e) {
									logger.error(e.getMessage());
								}
							});
						}
						Thread.sleep(properties.getRefreshTimeout());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
}
