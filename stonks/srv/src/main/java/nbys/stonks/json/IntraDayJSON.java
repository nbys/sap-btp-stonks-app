package nbys.stonks.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import static com.sap.cds.Struct.access;
import java.lang.reflect.Field;
import java.time.Instant;

import java.util.HashMap;

import cds.gen.myservice.Ticker;
import cds.gen.nbys.stonks.IntraDay;

public class IntraDayJSON {
    @JsonProperty("1. open")
    public String open;

    @JsonProperty("2. high")
    public String high;

    @JsonProperty("3. low")
    public String low;

    @JsonProperty("4. close")
    public String close;

    @JsonProperty("5. volume")
    public String volume;

    public Instant time;

    public String ticker_symbol;

    public IntraDay toCDS()
            throws IllegalAccessException, NoSuchFieldException {
        HashMap<String, Object> map = new HashMap<>();
        Field[] fields = IntraDayJSON.class.getDeclaredFields();

        for (Field field : fields) {
            String name = field.getName();
            if (name == "ticker") {
                continue;
            }
            map.put(name, this.getClass()
                    .getDeclaredField(name.toLowerCase()).get(this));
        }
        IntraDay intraDay = access(map).as(IntraDay.class);
        Ticker t = Ticker.create();

        t.setSymbol(this.ticker_symbol);
        intraDay.setTicker(t);
        return intraDay;
    }
}
