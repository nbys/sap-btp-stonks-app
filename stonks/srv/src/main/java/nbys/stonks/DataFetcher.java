package nbys.stonks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nbys.stonks.cds.TickerHandler;
import nbys.stonks.json.IntraDayJSON;
import nbys.stonks.json.IntraDayMetaJSON;
import cds.gen.nbys.stonks.IntraDay;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Iterator;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.Instant;

@Component
public class DataFetcher {
    String METADATA_KEY = "Meta Data";

    private static final Logger logger = LoggerFactory.getLogger(DataFetcher.class);

    private final WebClient webClient = WebClient.create();

    @Autowired
    private TickerHandler tickerHandler;

    @Value("${stonks.data.api.url}")
    private String URL;

    public List<IntraDay> unmarshalIntradayResponse(String jsonString)
            throws JsonProcessingException, IllegalAccessException, NoSuchFieldException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonString);

        List<IntraDay> intraDayList = new ArrayList<>();

        Iterator<Map.Entry<String, JsonNode>> fieldsIterator = rootNode.fields();

        IntraDayMetaJSON meta = objectMapper.treeToValue(rootNode.get(METADATA_KEY), IntraDayMetaJSON.class);

        while (fieldsIterator.hasNext()) {
            Map.Entry<String, JsonNode> field = fieldsIterator.next();
            String key = field.getKey();
            JsonNode value = field.getValue();
            if (key.equals(METADATA_KEY)) {
                continue;
            }
            Iterator<Map.Entry<String, JsonNode>> timeSeriesIterator = value.fields();
            while (timeSeriesIterator.hasNext()) {
                Map.Entry<String, JsonNode> timeSeries = timeSeriesIterator.next();
                String time = timeSeries.getKey();
                JsonNode timeSeriesValue = timeSeries.getValue();
                IntraDayJSON intraDayJSON = objectMapper.treeToValue(timeSeriesValue, IntraDayJSON.class);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                Instant utcTimeDate = LocalDateTime.parse(time, formatter).atZone(ZoneId.of(meta.timeZone)).toInstant();
                intraDayJSON.time = utcTimeDate;
                intraDayJSON.ticker_symbol = meta.symbol;

                intraDayList.add(intraDayJSON.toCDS());
            }
        }

        // intraDayList.forEach(intraDay -> {
        // tickerHandler.insertIntraDay(intraDay);
        // });

        return intraDayList;
    }

    public String fetchIntraDayData(String symbol) {
        return webClient.get()
                .uri(URL)
                .retrieve()
                .bodyToMono(String.class).block();
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
