package nbys.stonks.fetcher;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.sap.cds.CdsData;
import com.sap.cds.ql.Insert;

import cds.gen.nbys.stonks.IntraDay_;
import nbys.stonks.ApplicationProperties;
import nbys.stonks.json.Unmarshaller;

@Component
public class IntradayFetcher extends CommonFetcher {

    public IntradayFetcher(@Autowired @Qualifier("IntraDayJSON") Unmarshaller unmarshaller,
            ApplicationProperties properties) {
        super();
        this.setUnmarshaller(unmarshaller);
        this.setMethod(properties.getApi().getIntraDay().getMethod());
        this.setURL(properties.getApi().getIntraDay().getUrl());
    }

    @Override
    public String getEntityType() {
        return "nbys.stonks.IntraDay";
    }

    @Override
    public void persist(List<CdsData> cdsList) {
        this.db.run(Insert.into(IntraDay_.class).entries(cdsList));
    }
}
