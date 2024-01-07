package nbys.stonks.fetcher;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sap.cds.CdsData;

public interface DataFetcher {
    public String getEntityType();

    public String fetchFromAPI(String ticker);

    public List<CdsData> unmarhal(String jsonString) throws JsonProcessingException, JsonMappingException;

    public void persist(List<CdsData> cdsList);
}
