package nbys.stonks;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import cds.gen.nbys.stonks.IntraDay;
import java.util.List;

import nbys.stonks.cds.StonksHandler;
import nbys.stonks.json.IntraDayJSON;

@SpringBootTest
@EnableAutoConfiguration
@ContextConfiguration(classes = { DataFetcher.class, StonksHandler.class, IntraDayJSON.class,
        ApplicationProperties.class })
@ActiveProfiles("test")
public class IntraDayJSONTest {
    private static final Logger logger = LoggerFactory.getLogger(DataFetcher.class);

    @Test
    public void test() {
        System.out.println("Hello World");
    }

    private String json = "{\n" +
            "    \"Meta Data\": {\n" +
            "        \"1. Information\": \"Intraday (5min) open, high, low, close prices and volume\",\n" +
            "        \"2. Symbol\": \"IBM\",\n" +
            "        \"3. Last Refreshed\": \"2023-12-08 19:50:00\",\n" +
            "        \"4. Interval\": \"5min\",\n" +
            "        \"5. Output Size\": \"Full size\",\n" +
            "        \"6. Time Zone\": \"US/Eastern\"\n" +
            "    },\n" +
            "    \"Time Series (5min)\": {\n" +
            "        \"2023-12-08 19:50:00\": {\n" +
            "            \"1. open\": \"161.9700\",\n" +
            "            \"2. high\": \"161.9700\",\n" +
            "            \"3. low\": \"161.9700\",\n" +
            "            \"4. close\": \"161.9700\",\n" +
            "            \"5. volume\": \"20\"\n" +
            "        },        \n" +
            "        \"2023-11-17 04:30:00\": {\n" +
            "            \"1. open\": \"153.1500\",\n" +
            "            \"2. high\": \"153.1500\",\n" +
            "            \"3. low\": \"153.1500\",\n" +
            "            \"4. close\": \"153.1500\",\n" +
            "            \"5. volume\": \"22\"\n" +
            "        },\n" +
            "        \"2023-11-17 04:10:00\": {\n" +
            "            \"1. open\": \"153.2400\",\n" +
            "            \"2. high\": \"153.2400\",\n" +
            "            \"3. low\": \"153.2400\",\n" +
            "            \"4. close\": \"153.2400\",\n" +
            "            \"5. volume\": \"1\"\n" +
            "        },\n" +
            "        \"2023-11-17 04:05:00\": {\n" +
            "            \"1. open\": \"153.2600\",\n" +
            "            \"2. high\": \"153.2700\",\n" +
            "            \"3. low\": \"153.2600\",\n" +
            "            \"4. close\": \"153.2700\",\n" +
            "            \"5. volume\": \"40\"\n" +
            "        }\n" +
            "    }\n" +
            "}";

    @Test
    public void testUnmarshal() {
        // DataFetcher dataFetcher = new DataFetcher();
        try {
            List<IntraDay> res = IntraDayJSON.unmarshalIntradayResponse(this.json);
            logger.info(String.format("number of days %d", res.size()));
        } catch (Exception e) {
            System.out.println(e);
            return;
        }
    }
}
