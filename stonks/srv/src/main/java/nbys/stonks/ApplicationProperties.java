package nbys.stonks;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "stonks")
@Getter
@Setter
public class ApplicationProperties {
    private Integer refreshTimeout;

    private Api api;

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "stonks.api")
    public static class Api {

        private IntraDay intraDay;
        private Incomes incomes;

        @Getter
        @Setter
        @ConfigurationProperties(prefix = "stonks.api.intraday")
        public static class IntraDay {
            private String url;
            private String method;
        }

        @Getter
        @Setter
        @ConfigurationProperties(prefix = "stonks.api.incomes")
        public static class Incomes {
            private String url;
            private String method;
        }
    }
}
