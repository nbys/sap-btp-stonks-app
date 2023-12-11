package nbys.stonks.eventhandlers;

import org.springframework.stereotype.Component;

import com.sap.cds.services.cds.CdsReadEventContext;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.After;
import com.sap.cds.services.handler.annotations.ServiceName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cds.gen.myservice.MyService_;

@Component
@ServiceName(MyService_.CDS_NAME)
public class TickerEventHandler implements EventHandler {
    static final Logger logger = LoggerFactory.getLogger(TickerEventHandler.class);

    @After(event = CqnService.EVENT_READ, entity = "MyService.Ticker")
    public void onReadTicker(CdsReadEventContext req) {
        logger.info(req.getEvent() + req.getTarget().toString());
        logger.info(req.getResult().toString());
        req.setCompleted();
    }
}