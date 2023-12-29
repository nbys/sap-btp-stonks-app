package nbys.stonks;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import nbys.stonks.cds.StonksHandler;

@SpringBootTest
@EnableAutoConfiguration
@ContextConfiguration(classes = { DataFetcher.class, StonksHandler.class, ApplicationProperties.class })
@ActiveProfiles("test")
public class DataFetcherTest {
    private static final Logger logger = LoggerFactory.getLogger(DataFetcher.class);

    @Test
    public void test() {
        System.out.println("Hello World");
    }

}
