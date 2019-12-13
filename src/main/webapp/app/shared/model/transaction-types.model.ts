import { Moment } from 'moment';

export interface ITransactionTypes {
  id?: number;
  name?: string;
  validFrom?: Moment;
  validTo?: Moment;
}

export class TransactionTypes implements ITransactionTypes {
  constructor(public id?: number, public name?: string, public validFrom?: Moment, public validTo?: Moment) {}
}
