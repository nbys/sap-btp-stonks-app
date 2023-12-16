package nbys.stonks;

import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import com.sap.cds.ql.Select;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.services.cds.CdsReadEventContext;
import com.sap.cds.services.persistence.PersistenceService;
import cds.gen.nbys.stonks.Ticker;
import cds.gen.myservice.Ticker_;
import nbys.stonks.cds.TickerHandler;

import com.sap.cds.Result;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@SpringBootTest
@EnableAutoConfiguration
@ContextConfiguration(classes = TickerHandler.class)
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class TicketHandlerTest {
    static final Logger logger = LoggerFactory.getLogger(TickerHandler.class);

    @FunctionalInterface
    private interface TriFunction<T, U, V, R> {
        R apply(T t, U u, V v);
    }

    @Autowired
    private PersistenceService db;

    @Test
    public void onReadTickerTest() {
        // helper function to create Ticker objects
        TriFunction<String, String, String, Ticker> tickerFactory = (name, symbol, sector) -> {
            Ticker t = Ticker.create();
            t.setName(name);
            t.setSymbol(symbol);
            t.setSector(sector);
            return t;
        };

        // build test cases
        Map<String, List<Object>> tickers = Stream.of(new Object[][] {
                { "correct test", Select.from(Ticker_.class).where(t -> t.symbol().eq("AMZN")),
                        tickerFactory.apply("Amazon", "AMZN", "Technology") },
        }).collect(Collectors.toMap(data -> (String) data[0], data -> List.of(data[1], (Ticker) data[2])));

        TickerHandler tickerHandler = new TickerHandler(db);

        for (Map.Entry<String, List<Object>> entry : tickers.entrySet()) {
            String testName = entry.getKey();
            List<Object> testData = entry.getValue();
            CqnSelect select = (CqnSelect) testData.get(0);
            Ticker expected = (Ticker) testData.get(1);

            CdsReadEventContext req = CdsReadEventContext.create(Ticker_.CDS_NAME);
            req.setCqn(select);

            logger.info(testName);
            logger.info(select.toString());
            Result got = tickerHandler.onReadTicker(req);
            logger.info(got.toString());
            // assert (got.getName().equals(expected.getName()));
            // assert (got.getSymbol().equals(expected.getSymbol()));
            // assert (got.getSector().equals(expected.getSector()));
        }

        // Ticker ticker = Ticker.create();
        // ticker.setName("Amazon");
        // ticker.setSymbol("AMZN");
        // ticker.setSector("Technology");

        // CqnInsert insert = Insert.into(Ticker_.class).entry(ticker);
        // Result res = db.run(insert);
        // logger.info(res.toString());

        // TickerHandler tickerHandler = new TickerHandler(db);
        // CdsReadEventContext req = CdsReadEventContext.create(Ticker_.CDS_NAME);
        // req.setCqn(Select.from(Ticker_.class));

        // tickerHandler.onReadTicker(req);
    }

}
