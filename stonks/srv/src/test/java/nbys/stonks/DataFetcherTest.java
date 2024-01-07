package nbys.stonks;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import nbys.stonks.ApplicationProperties.Api.IntraDay;
import nbys.stonks.cds.StonksHandler;
import nbys.stonks.fetcher.DataFetcher;

import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;

@SpringBootTest
@EnableAutoConfiguration
@ContextConfiguration(classes = { DataFetcher.class, StonksHandler.class, ApplicationProperties.class })
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class DataFetcherTest {
    private static final Logger logger = LoggerFactory.getLogger(DataFetcher.class);

    @Autowired
    private ApplicationProperties testAppProps;

    @Test
    public void test() {
        System.out.println("Hello World");
    }

    @Test
    public void testFetch() {
        // DataFetcher mockDataFetcher = Mockito.mock(DataFetcher.class);
        // IntraDay intradayAPI = this.testAppProps.getApi().getIntraDay();
        // Mockito.when(
        // mockDataFetcher.fetchIntraDayData("APPL", intradayAPI.getUrl(),
        // intradayAPI.getMethod()))
        // .thenReturn("jsonString");
    }
}
