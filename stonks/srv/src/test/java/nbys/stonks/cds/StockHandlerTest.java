package nbys.stonks.cds;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import cds.gen.nbys.stonks.IntraDay;
import cds.gen.nbys.stonks.Ticker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

import java.util.List;

@SpringBootTest
@EnableAutoConfiguration
@ContextConfiguration(classes = StonksHandler.class)
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class StockHandlerTest {
    static final Logger logger = LoggerFactory.getLogger(StonksHandler.class);

    @Autowired
    private StonksHandler stonksHandler;

    @Test
    public void getTickersTest() {
        List<String> tickers = stonksHandler.getTickers();
        assertEquals(2, tickers.size());
    }

    @Test
    public void insertIntraDay() {
        IntraDay intraDay = IntraDay.create();
        Ticker ticker = Ticker.create();
        ticker.setSymbol("ATTT");
        intraDay.setTickerSymbol("ATTT");
        intraDay.setTicker(ticker);
        stonksHandler.insertIntraDay(intraDay);

        List<IntraDay> intraDays = stonksHandler.getIntraDays();
        // filter intradays
        intraDays.removeIf(intraday -> !intraday.getTickerSymbol().equals("ATTT"));
        assertEquals(1, intraDays.size());
    }

}
