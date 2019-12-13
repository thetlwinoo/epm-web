import { Moment } from 'moment';

export interface ICountries {
  id?: number;
  name?: string;
  formalName?: string;
  isoAplha3Code?: string;
  isoNumericCode?: number;
  countryType?: string;
  latestRecordedPopulation?: number;
  continent?: string;
  region?: string;
  subregion?: string;
  border?: string;
  validFrom?: Moment;
  validTo?: Moment;
}

export class Countries implements ICountries {
  constructor(
    public id?: number,
    public name?: string,
    public formalName?: string,
    public isoAplha3Code?: string,
    public isoNumericCode?: number,
    public countryType?: string,
    public latestRecordedPopulation?: number,
    public continent?: string,
    public region?: string,
    public subregion?: string,
    public border?: string,
    public validFrom?: Moment,
    public validTo?: Moment
  ) {}
}
