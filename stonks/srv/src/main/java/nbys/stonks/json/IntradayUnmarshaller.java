package nbys.stonks.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.cds.CdsData;

import cds.gen.nbys.stonks.IntraDay;
import cds.gen.stonks.Ticker;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ArrayList;

@Component
@Qualifier("IntraDayJSON")
public class IntradayUnmarshaller extends CdsUnmarshaller {
    private static final String METADATA_KEY = "Meta Data";
    private static final String TIME_SERIES_KEY = "Time Series (5min)";

    static class Metadata {
        @JsonProperty("1. Information")
        public String information;
        @JsonProperty("2. Symbol")
        public String symbol;
        @JsonProperty("3. Last Refreshed")
        public String lastRefreshed;
        @JsonProperty("4. Interval")
        public String interval;
        @JsonProperty("5. Output Size")
        public String outputSize;
        @JsonProperty("6. Time Zone")
        public String timeZone;
    }

    @JsonProperty("1. open")
    public String _open;

    @JsonProperty("2. high")
    public String _high;

    @JsonProperty("3. low")
    public String _low;

    @JsonProperty("4. close")
    public String _close;

    @JsonProperty("5. volume")
    public String _volume;

    public Instant _time;

    public String _ticker_symbol;

    @Override
    protected <T> T toCDS(Class<T> clazz) throws IllegalAccessException, NoSuchFieldException {
        T instance = super.toCDS(clazz);

        IntraDay intraday = (IntraDay) instance;
        Ticker t = Ticker.create();
        t.setSymbol(this._ticker_symbol);
        intraday.setTicker(t);

        return instance;
    }

    @Override
    public List<CdsData> unmarshal(String jsonString)
            throws JsonProcessingException, JsonMappingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonString);
        Metadata metadata = mapper.treeToValue(rootNode.get(METADATA_KEY), Metadata.class);
        Map<String, IntradayUnmarshaller> timeSeries = mapper.convertValue(rootNode.get(TIME_SERIES_KEY),
                new TypeReference<Map<String, IntradayUnmarshaller>>() {
                });

        if (timeSeries == null) {
            throw new JsonMappingException(null, "Time series is null");
        }

        List<CdsData> timeSeriesList = new ArrayList<>();

        timeSeries.forEach((time, v) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            Instant utcTimeDate = LocalDateTime.parse(time, formatter).atZone(ZoneId.of(metadata.timeZone)).toInstant();
            v._time = utcTimeDate;
            v._ticker_symbol = metadata.symbol;
            try {
                IntraDay intraday = v.toCDS(IntraDay.class);
                timeSeriesList.add(intraday);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        });
        return timeSeriesList;
    }
}
