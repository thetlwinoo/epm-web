import { Moment } from 'moment';

export interface IBuyingGroups {
  id?: number;
  name?: string;
  validFrom?: Moment;
  validTo?: Moment;
}

export class BuyingGroups implements IBuyingGroups {
  constructor(public id?: number, public name?: string, public validFrom?: Moment, public validTo?: Moment) {}
}
