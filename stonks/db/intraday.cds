namespace nbys.stonks;

using {cuid} from '@sap/cds/common';

entity Ticker {
    key symbol   : String;
        name     : localized String;
        sector   : localized String;
        industry : localized String;
}

entity IntraDay : cuid {
    ticker : Association to Ticker;
    time   : DateTime;
    open   : Decimal;
    high   : Decimal;
    low    : Decimal;
    close  : Decimal;
    volume : Decimal;
}
