package nbys.stonks;

import org.junit.jupiter.api.Test;

import cds.gen.nbys.stonks.IntraDay;
import nbys.stonks.json.IntradayUnmarshaller;
import java.util.List;

public class IntraDay2Test {

    @Test
    public void test() {
        String jsonString = "{\n" +
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
                "        \"2023-11-17 04:05:00\": {\n" +
                "            \"1. open\": \"153.2600\",\n" +
                "            \"2. high\": \"153.2700\",\n" +
                "            \"3. low\": \"153.2600\",\n" +
                "            \"4. close\": \"153.2700\",\n" +
                "            \"5. volume\": \"40\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        // try {
        // List<IntraDay> json = IntraDayJSON.unmarhal(jsonString);
        // System.out.println(json);
        // } catch (Exception e) {
        // System.out.println(e);
        // }
    }

}
