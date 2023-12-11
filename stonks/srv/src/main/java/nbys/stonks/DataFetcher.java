package nbys.stonks;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DataFetcher {
    private static final Logger logger = LoggerFactory.getLogger(DataFetcher.class);

    private final WebClient webClient = WebClient.create();

    @Value("${stonks.data.api.url}")
    private String URL;

    public String unmarshalJSON() throws JsonProcessingException {
        String json = "{\n" +
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
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);

        String symbol = rootNode.get("Meta Data").get("2. Symbol").asText();

        return symbol;
    }

    public Mono<String> fetchIntraDayData(String symbol) {
        return webClient.get()
                .uri(URL)
                .retrieve()
                .bodyToMono(String.class);
    }

    public void startFetchingData() {
        while (true) {
            try {
                Thread.sleep(800000);
                logger.info("Fetching data...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
