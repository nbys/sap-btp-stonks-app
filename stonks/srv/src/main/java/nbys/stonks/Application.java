package nbys.stonks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private DataFetcher dataFetcher;

	@Override
	public void run(ApplicationArguments args) {
		// run fetcher as a separate thread
		new Thread() {
			public void run() {
				dataFetcher.startDataProcess();
			}
		}.start();
	}

}
