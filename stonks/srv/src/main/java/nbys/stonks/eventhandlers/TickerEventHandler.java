package nbys.stonks.eventhandlers;

import org.springframework.stereotype.Component;

import com.sap.cds.services.EventContext;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.After;
import com.sap.cds.services.handler.annotations.ServiceName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cds.gen.myservice.MyService;
import cds.gen.myservice.MyService_;
import cds.gen.myservice.Ticker;
import cds.gen.nbys.stonks.Ticker_;

@Component
@ServiceName(MyService_.CDS_NAME)
public class TickerEventHandler implements EventHandler {
    static final Logger logger = LoggerFactory.getLogger(TickerEventHandler.class);

    @After(entity = Ticker_.CDS_NAME, event = MyService.EVENT_READ)
    public void onReadTicker(EventContext req) {
        System.out.println("HAHAHAHAHA" + req.toString());
        logger.info("HAHAHAHAHA" + req.toString());
        req.setCompleted();
    }
}