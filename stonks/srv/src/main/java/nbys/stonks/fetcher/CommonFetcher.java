package nbys.stonks.fetcher;

import java.util.List;

import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sap.cds.CdsData;
import com.sap.cds.ql.Insert;
import com.sap.cds.services.persistence.PersistenceService;

import nbys.stonks.json.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;

public class CommonFetcher implements DataFetcher {

    @Autowired
    private WebClient webClient;

    @Value("${stonks.api.key}")
    private String apiKey;
    private String URL;
    private String method;

    private Unmarshaller unmarshaller;

    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Autowired
    private PersistenceService db;

    @Override
    public String fetchFromAPI(String ticker) {
        String URL = this.URL.replace("${ticker}", ticker)
                .replace("{apikey}", this.apiKey);
        String result = webClient.method(HttpMethod.valueOf(method))
                .uri(URL)
                .retrieve()
                .bodyToMono(String.class).block();

        return result;
    }

    @Override
    public List<CdsData> unmarhal(String jsonString) throws JsonProcessingException, JsonMappingException {
        return this.unmarshaller.unmarshal(jsonString);
    }

    @Override
    public void persist(List<CdsData> cdsList) {
        this.db.run(Insert.into(cdsList.get(0).getClass().getName()).entries(cdsList));
    }

    @Override
    public String getEntityType() {
        throw new UnsupportedOperationException("Unimplemented method 'getLabel'");
    }

}
