import { Moment } from 'moment';

export interface ICustomerTransactions {
  id?: number;
  transactionDate?: Moment;
  amountExcludingTax?: number;
  taxAmount?: number;
  transactionAmount?: number;
  outstandingBalance?: number;
  finalizationDate?: Moment;
  isFinalized?: boolean;
  lastEditedBy?: string;
  lastEditedWhen?: Moment;
  customerId?: number;
  paymentTransactionId?: number;
  transactionTypeName?: string;
  transactionTypeId?: number;
  invoiceId?: number;
}

export class CustomerTransactions implements ICustomerTransactions {
  constructor(
    public id?: number,
    public transactionDate?: Moment,
    public amountExcludingTax?: number,
    public taxAmount?: number,
    public transactionAmount?: number,
    public outstandingBalance?: number,
    public finalizationDate?: Moment,
    public isFinalized?: boolean,
    public lastEditedBy?: string,
    public lastEditedWhen?: Moment,
    public customerId?: number,
    public paymentTransactionId?: number,
    public transactionTypeName?: string,
    public transactionTypeId?: number,
    public invoiceId?: number
  ) {
    this.isFinalized = this.isFinalized || false;
  }
}
