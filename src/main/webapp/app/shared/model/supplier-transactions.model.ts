import { Moment } from 'moment';

export interface ISupplierTransactions {
  id?: number;
  supplierInvoiceNumber?: string;
  transactionDate?: Moment;
  amountExcludingTax?: number;
  taxAmount?: number;
  transactionAmount?: number;
  outstandingBalance?: number;
  finalizationDate?: Moment;
  isFinalized?: boolean;
  lastEditedBy?: string;
  lastEditedWhen?: Moment;
  supplierName?: string;
  supplierId?: number;
  transactionTypeName?: string;
  transactionTypeId?: number;
  purchaseOrderId?: number;
}

export class SupplierTransactions implements ISupplierTransactions {
  constructor(
    public id?: number,
    public supplierInvoiceNumber?: string,
    public transactionDate?: Moment,
    public amountExcludingTax?: number,
    public taxAmount?: number,
    public transactionAmount?: number,
    public outstandingBalance?: number,
    public finalizationDate?: Moment,
    public isFinalized?: boolean,
    public lastEditedBy?: string,
    public lastEditedWhen?: Moment,
    public supplierName?: string,
    public supplierId?: number,
    public transactionTypeName?: string,
    public transactionTypeId?: number,
    public purchaseOrderId?: number
  ) {
    this.isFinalized = this.isFinalized || false;
  }
}
