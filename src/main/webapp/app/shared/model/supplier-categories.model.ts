import { Moment } from 'moment';

export interface ISupplierCategories {
  id?: number;
  name?: string;
  validFrom?: Moment;
  validTo?: Moment;
}

export class SupplierCategories implements ISupplierCategories {
  constructor(public id?: number, public name?: string, public validFrom?: Moment, public validTo?: Moment) {}
}
