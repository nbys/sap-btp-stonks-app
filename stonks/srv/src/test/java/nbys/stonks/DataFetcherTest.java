package nbys.stonks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import cds.gen.nbys.stonks.IntraDay;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import nbys.stonks.cds.TickerHandler;

@SpringBootTest
@EnableAutoConfiguration
@ContextConfiguration(classes = { DataFetcher.class, TickerHandler.class })
@ActiveProfiles("test")
public class DataFetcherTest {
    private static final Logger logger = LoggerFactory.getLogger(DataFetcher.class);

    @Autowired
    private DataFetcher dataFetcher;

    @Test
    public void test() {
        System.out.println("Hello World");
    }

}
