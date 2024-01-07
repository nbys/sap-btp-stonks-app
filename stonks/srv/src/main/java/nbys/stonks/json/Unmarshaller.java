package nbys.stonks.json;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sap.cds.CdsData;

public interface Unmarshaller {
    public List<CdsData> unmarshal(String jsonString) throws JsonProcessingException, JsonMappingException;
}
