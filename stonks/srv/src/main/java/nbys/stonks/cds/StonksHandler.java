package nbys.stonks.cds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sap.cds.Result;
import com.sap.cds.services.cds.CdsReadEventContext;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cds.gen.nbys.stonks.Stonks_;
import cds.gen.stonks.Ticker_;
import cds.gen.nbys.stonks.IntraDay;
import cds.gen.nbys.stonks.IntraDay_;
import com.sap.cds.ql.Insert;
import com.sap.cds.ql.Select;
import java.util.ArrayList;

@Component
@ServiceName(Stonks_.CDS_NAME)
public class StonksHandler implements EventHandler {
    static final Logger logger = LoggerFactory.getLogger(StonksHandler.class);

    @Autowired
    private PersistenceService db;

    public StonksHandler(PersistenceService db) {
        this.db = db;
    }

    @On(event = CqnService.EVENT_READ, entity = Ticker_.CDS_NAME)
    public Result onReadTicker(
            CdsReadEventContext req) {
        logger.info(req.getEvent() + req.getTarget().toString());
        logger.info(req.getCqn().toString());

        Result res = db.run(req.getCqn());
        if (res == null) {
            logger.info("No results");
            return res;
        }

        res.forEach(row -> {
            // logger.info(row.toString());
            row.put("name", String.format("%s - %S", row.get("name"), row.get("symbol")));
        });
        return res;
    }

    public void insertIntraDay(IntraDay intraDay) {
        Result res = this.db.run(Insert.into(IntraDay_.class).entry(intraDay));
        logger.info(res.toString());
    }

    public ArrayList<String> getTickers() {
        ArrayList<String> tickers = new ArrayList<String>();
        Result res = this.db.run(Select.from(Ticker_.class).columns(t -> t.symbol()));
        res.forEach(row -> {
            tickers.add(row.get("symbol").toString());
        });
        return tickers;
    }

    public ArrayList<IntraDay> getIntraDays() {
        ArrayList<IntraDay> intraDays = new ArrayList<IntraDay>();
        Result res = this.db.run(Select.from(IntraDay_.class));
        res.forEach(row -> {
            intraDays.add(row.as(IntraDay.class));
        });
        return intraDays;
    }

}