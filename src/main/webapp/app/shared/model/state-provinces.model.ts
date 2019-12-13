import { Moment } from 'moment';

export interface IStateProvinces {
  id?: number;
  code?: string;
  name?: string;
  salesTerritory?: string;
  border?: string;
  latestRecordedPopulation?: number;
  validFrom?: Moment;
  validTo?: Moment;
  countryName?: string;
  countryId?: number;
}

export class StateProvinces implements IStateProvinces {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public salesTerritory?: string,
    public border?: string,
    public latestRecordedPopulation?: number,
    public validFrom?: Moment,
    public validTo?: Moment,
    public countryName?: string,
    public countryId?: number
  ) {}
}
