import { Moment } from 'moment';

export interface ICities {
  id?: number;
  name?: string;
  location?: string;
  latestRecordedPopulation?: number;
  validFrom?: Moment;
  validTo?: Moment;
  stateProvinceName?: string;
  stateProvinceId?: number;
}

export class Cities implements ICities {
  constructor(
    public id?: number,
    public name?: string,
    public location?: string,
    public latestRecordedPopulation?: number,
    public validFrom?: Moment,
    public validTo?: Moment,
    public stateProvinceName?: string,
    public stateProvinceId?: number
  ) {}
}
