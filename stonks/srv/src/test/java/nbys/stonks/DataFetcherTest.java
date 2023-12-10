package nbys.stonks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@EnableAutoConfiguration
@ContextConfiguration(classes = DataFetcher.class)
@ActiveProfiles("test")
public class DataFetcherTest {
    @Test
    public void test() {
        System.out.println("Hello World");
    }

    @Test
    public void testUnmarshal() {
        DataFetcher dataFetcher = new DataFetcher();
        String json;
        try {
            json = dataFetcher.unmarshalJSON();
        } catch (Exception e) {
            System.out.println(e);
            return;
        }
        assertEquals("IBM", json);
    }
}
