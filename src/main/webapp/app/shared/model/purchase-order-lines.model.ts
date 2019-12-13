import { Moment } from 'moment';

export interface IPurchaseOrderLines {
  id?: number;
  orderedOuters?: number;
  description?: string;
  receivedOuters?: number;
  expectedUnitPricePerOuter?: number;
  lastReceiptDate?: Moment;
  isOrderLineFinalized?: boolean;
  lastEditedBy?: string;
  lastEditedWhen?: Moment;
  packageTypeName?: string;
  packageTypeId?: number;
  stockItemName?: string;
  stockItemId?: number;
  purchaseOrderId?: number;
}

export class PurchaseOrderLines implements IPurchaseOrderLines {
  constructor(
    public id?: number,
    public orderedOuters?: number,
    public description?: string,
    public receivedOuters?: number,
    public expectedUnitPricePerOuter?: number,
    public lastReceiptDate?: Moment,
    public isOrderLineFinalized?: boolean,
    public lastEditedBy?: string,
    public lastEditedWhen?: Moment,
    public packageTypeName?: string,
    public packageTypeId?: number,
    public stockItemName?: string,
    public stockItemId?: number,
    public purchaseOrderId?: number
  ) {
    this.isOrderLineFinalized = this.isOrderLineFinalized || false;
  }
}
