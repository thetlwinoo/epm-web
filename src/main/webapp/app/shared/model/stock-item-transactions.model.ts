import { Moment } from 'moment';

export interface IStockItemTransactions {
  id?: number;
  transactionOccuredWhen?: Moment;
  quantity?: number;
  lastEditedBy?: string;
  lastEditedWhen?: Moment;
  stockItemName?: string;
  stockItemId?: number;
  customerId?: number;
  invoiceId?: number;
  supplierName?: string;
  supplierId?: number;
  transactionTypeName?: string;
  transactionTypeId?: number;
  purchaseOrderId?: number;
}

export class StockItemTransactions implements IStockItemTransactions {
  constructor(
    public id?: number,
    public transactionOccuredWhen?: Moment,
    public quantity?: number,
    public lastEditedBy?: string,
    public lastEditedWhen?: Moment,
    public stockItemName?: string,
    public stockItemId?: number,
    public customerId?: number,
    public invoiceId?: number,
    public supplierName?: string,
    public supplierId?: number,
    public transactionTypeName?: string,
    public transactionTypeId?: number,
    public purchaseOrderId?: number
  ) {}
}
