import { Moment } from 'moment';
import { IPurchaseOrderLines } from 'app/shared/model/purchase-order-lines.model';

export interface IPurchaseOrders {
  id?: number;
  orderDate?: Moment;
  expectedDeliveryDate?: Moment;
  supplierReference?: string;
  isOrderFinalized?: number;
  comments?: string;
  internalComments?: string;
  lastEditedBy?: string;
  lastEditedWhen?: Moment;
  purchaseOrderLineLists?: IPurchaseOrderLines[];
  contactPersonFullName?: string;
  contactPersonId?: number;
  supplierName?: string;
  supplierId?: number;
  deliveryMethodName?: string;
  deliveryMethodId?: number;
}

export class PurchaseOrders implements IPurchaseOrders {
  constructor(
    public id?: number,
    public orderDate?: Moment,
    public expectedDeliveryDate?: Moment,
    public supplierReference?: string,
    public isOrderFinalized?: number,
    public comments?: string,
    public internalComments?: string,
    public lastEditedBy?: string,
    public lastEditedWhen?: Moment,
    public purchaseOrderLineLists?: IPurchaseOrderLines[],
    public contactPersonFullName?: string,
    public contactPersonId?: number,
    public supplierName?: string,
    public supplierId?: number,
    public deliveryMethodName?: string,
    public deliveryMethodId?: number
  ) {}
}
