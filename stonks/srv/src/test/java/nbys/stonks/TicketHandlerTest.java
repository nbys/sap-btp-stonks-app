package nbys.stonks;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.sap.cds.services.persistence.PersistenceService;

import nbys.stonks.cds.TickerHandler;

@SpringBootTest
@EnableAutoConfiguration
@ContextConfiguration(classes = TickerHandler.class)
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class TicketHandlerTest {
    @Mock
    private PersistenceService db;

    @Test
    public void onReadTickerTest() {

    }

}
