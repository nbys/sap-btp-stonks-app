package nbys.stonks.json;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.FileCopyUtils;
import com.sap.cds.CdsData;
import org.springframework.core.io.Resource;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Value;
import java.io.InputStreamReader;
import java.util.List;

import cds.gen.nbys.stonks.IntraDay;

@SpringBootTest
@EnableAutoConfiguration
@ContextConfiguration(classes = IntradayUnmarshaller.class)
@ActiveProfiles("test")
public class IntradayUnmarshallerTest {

    @Autowired
    private IntradayUnmarshaller unmarshaller;

    @Value("classpath:tests/intraday.json")
    private Resource intradayJsonFile;

    @Test
    public void testUnmarshal() {
        assertDoesNotThrow(() -> {
            // String intradayJson = this.intradayJsonFile.getInputStream().toString();
            InputStreamReader reader = new InputStreamReader(this.intradayJsonFile.getInputStream(), "UTF-8");
            String intradayJson = FileCopyUtils.copyToString(reader);

            List<CdsData> result = unmarshaller.unmarshal(intradayJson);
            assertEquals(2688, result.size());
            result.forEach((item) -> {
                IntraDay intraday = (IntraDay) item;
                assertEquals("TSLA", intraday.getTickerSymbol());
            });
        });
    }

}
