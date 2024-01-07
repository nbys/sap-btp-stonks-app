package nbys.stonks.fetcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

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
}
