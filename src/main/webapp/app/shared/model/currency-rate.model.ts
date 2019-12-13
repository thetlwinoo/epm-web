import { Moment } from 'moment';

export interface ICurrencyRate {
  id?: number;
  currencyRateDate?: Moment;
  fromcode?: string;
  tocode?: string;
  averageRate?: number;
  endOfDayRate?: number;
  lastEditedBy?: string;
  lastEditedWhen?: Moment;
}

export class CurrencyRate implements ICurrencyRate {
  constructor(
    public id?: number,
    public currencyRateDate?: Moment,
    public fromcode?: string,
    public tocode?: string,
    public averageRate?: number,
    public endOfDayRate?: number,
    public lastEditedBy?: string,
    public lastEditedWhen?: Moment
  ) {}
}
