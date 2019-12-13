import { Moment } from 'moment';

export interface IPaymentTransactions {
  id?: number;
  returnedCompletedPaymentData?: any;
  lastEditedBy?: string;
  lastEditedWhen?: Moment;
  paymentOnOrderId?: number;
}

export class PaymentTransactions implements IPaymentTransactions {
  constructor(
    public id?: number,
    public returnedCompletedPaymentData?: any,
    public lastEditedBy?: string,
    public lastEditedWhen?: Moment,
    public paymentOnOrderId?: number
  ) {}
}
