package nbys.stonks.fetcher;

import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;

import nbys.stonks.ApplicationProperties;
import nbys.stonks.WebClientConfig;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sap.cds.CdsData;
import nbys.stonks.json.Unmarshaller;

import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@EnableAutoConfiguration
@ContextConfiguration(classes = { CommonFetcher.class, WebClientConfig.class, ApplicationProperties.class })
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommonFetcherTest {
    private MockWebServer mockWebServer;

    @Autowired
    private CommonFetcher commonFetcher;

    class MockUnmarshaller implements Unmarshaller {
        @Override
        public List<CdsData> unmarshal(String jsonString) throws JsonProcessingException, JsonMappingException {
            List<CdsData> list = new ArrayList<>();
            list.add(CdsData.create());
            return list;
        }
    }

    @BeforeAll
    public void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        commonFetcher.setURL(mockWebServer.url("/").toString());
        commonFetcher.setMethod("GET");
        commonFetcher.setUnmarshaller(new MockUnmarshaller());
    }

    @AfterAll
    public void teardown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void fetchFromAPITest() {
        MockResponse mockResponse = new MockResponse().setResponseCode(200).setBody("Hello World");
        mockWebServer.enqueue(mockResponse);

        String res = commonFetcher.fetchFromAPI("TSLA");
        assertEquals(res, "Hello World");
    }

    @Test
    public void unmarshalTest() {
        try {
            List<CdsData> res = commonFetcher.unmarhal("Hello World");
            assertEquals(res.size(), 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
