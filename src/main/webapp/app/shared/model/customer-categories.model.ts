import { Moment } from 'moment';

export interface ICustomerCategories {
  id?: number;
  name?: string;
  validFrom?: Moment;
  validTo?: Moment;
}

export class CustomerCategories implements ICustomerCategories {
  constructor(public id?: number, public name?: string, public validFrom?: Moment, public validTo?: Moment) {}
}
