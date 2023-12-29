package nbys.stonks.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.sap.cds.Struct.access;
import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cds.gen.stonks.Ticker;
import cds.gen.nbys.stonks.IntraDay;

@SuppressWarnings("unused")
public class IntraDayJSON {
    private static final String METADATA_KEY = "Meta Data";

    static class IntraDayMetaJSON {
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
    private String _open;

    @JsonProperty("2. high")
    private String _high;

    @JsonProperty("3. low")
    private String _low;

    @JsonProperty("4. close")
    private String _close;

    @JsonProperty("5. volume")
    private String _volume;

    private Instant _time;

    private String _ticker_symbol;

    private IntraDay toCDS()
            throws IllegalAccessException, NoSuchFieldException {
        HashMap<String, Object> map = new HashMap<>();
        Field[] fields = IntraDayJSON.class.getDeclaredFields();

        for (Field field : fields) {
            String name = field.getName();
            if (!name.startsWith("_")) {
                continue;
            }

            map.put(name.substring(1), this.getClass()
                    .getDeclaredField(name.toLowerCase()).get(this));
        }
        IntraDay intraDay = access(map).as(IntraDay.class);
        Ticker t = Ticker.create();

        t.setSymbol(this._ticker_symbol);
        intraDay.setTicker(t);
        return intraDay;
    }

    public static List<IntraDay> unmarshalIntradayResponse(String jsonString)
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
                intraDayJSON._time = utcTimeDate;
                intraDayJSON._ticker_symbol = meta.symbol;

                intraDayList.add(intraDayJSON.toCDS());
            }
        }

        return intraDayList;
    }
}
