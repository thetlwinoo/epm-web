import { Moment } from 'moment';

export interface IDeliveryMethods {
  id?: number;
  name?: string;
  validFrom?: Moment;
  validTo?: Moment;
}

export class DeliveryMethods implements IDeliveryMethods {
  constructor(public id?: number, public name?: string, public validFrom?: Moment, public validTo?: Moment) {}
}
