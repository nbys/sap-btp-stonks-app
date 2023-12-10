package nbys.stonks.eventhandlers;

import org.springframework.stereotype.Component;

import com.sap.cds.services.EventContext;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cds.gen.myservice.MyService_;

@Component
@ServiceName(MyService_.CDS_NAME)
public class TickerEventHandler implements EventHandler {
    static final Logger logger = LoggerFactory.getLogger(TickerEventHandler.class);

    @On()
    public void onReadTicker(EventContext req) {
        System.out.println("HAHAHAHAHA" + req.toString());
        logger.info("HAHAHAHAHA" + req.toString());
        req.setCompleted();
    }
}
