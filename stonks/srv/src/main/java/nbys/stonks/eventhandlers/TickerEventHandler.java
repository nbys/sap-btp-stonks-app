package nbys.stonks.eventhandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Component;

import com.sap.cds.Result;
import com.sap.cds.services.cds.CdsReadEventContext;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.After;
import com.sap.cds.services.handler.annotations.Before;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cds.gen.myservice.MyService_;
import cds.gen.myservice.Ticker_;

@Component
@ServiceName(MyService_.CDS_NAME)
public class TickerEventHandler implements EventHandler {
    static final Logger logger = LoggerFactory.getLogger(TickerEventHandler.class);

    @Autowired
    private PersistenceService db;

    @On(event = CqnService.EVENT_READ, entity = Ticker_.CDS_NAME)
    public Result onReadTicker(
            CdsReadEventContext req) {
        logger.info(req.getEvent() + req.getTarget().toString());
        // logger.info(req.getResult().toString());
        logger.info(req.getCqn().toString());

        Result res = db.run(req.getCqn());

        res.forEach(row -> {
            logger.info(row.toString());
            row.put("name", String.format("%s - %S", row.get("name"), row.get("symbol")));
        });
        return res;
    }
}