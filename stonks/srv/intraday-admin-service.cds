using {nbys.stonks as stonks} from '../db/intraday';

service MyService {
    entity Ticker   as projection on stonks.Ticker;
    entity Intraday as projection on stonks.IntraDay;
}
