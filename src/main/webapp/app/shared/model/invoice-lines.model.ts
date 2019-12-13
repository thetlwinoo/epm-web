import { Moment } from 'moment';

export interface IInvoiceLines {
  id?: number;
  description?: string;
  quantity?: number;
  unitPrice?: number;
  taxRate?: number;
  taxAmount?: number;
  lineProfit?: number;
  extendedPrice?: number;
  lastEditedBy?: string;
  lastEditedWhen?: Moment;
  packageTypeName?: string;
  packageTypeId?: number;
  stockItemName?: string;
  stockItemId?: number;
  invoiceId?: number;
}

export class InvoiceLines implements IInvoiceLines {
  constructor(
    public id?: number,
    public description?: string,
    public quantity?: number,
    public unitPrice?: number,
    public taxRate?: number,
    public taxAmount?: number,
    public lineProfit?: number,
    public extendedPrice?: number,
    public lastEditedBy?: string,
    public lastEditedWhen?: Moment,
    public packageTypeName?: string,
    public packageTypeId?: number,
    public stockItemName?: string,
    public stockItemId?: number,
    public invoiceId?: number
  ) {}
}
