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

@SpringBootApplication
public class Application implements ApplicationRunner {
	private static final Logger logger = LoggerFactory.getLogger(DataFetcher.class);

	@Autowired
	private StonksHandler stonksHandler;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		// run fetcher as a separate thread
		new Thread() {
			public void run() {
				while (true) {
					try {
						ArrayList<String> tickers = stonksHandler.getTickers();
						for (String ticker : tickers) {
							DataFetcherFactory.handlerCache.forEach((label, handler) -> {
								logger.info(String.format("Fetching data for %s", label));
								String JSONString = handler.fetchFromAPI(ticker);
								logger.info(JSONString);
							});
						}
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
}
