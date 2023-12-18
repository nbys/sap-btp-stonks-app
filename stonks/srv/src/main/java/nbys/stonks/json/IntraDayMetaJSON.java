package nbys.stonks.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IntraDayMetaJSON {
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
