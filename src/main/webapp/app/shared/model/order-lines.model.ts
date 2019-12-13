import { Moment } from 'moment';
import { OrderLineStatus } from 'app/shared/model/enumerations/order-line-status.model';

export interface IOrderLines {
  id?: number;
  carrierTrackingNumber?: string;
  quantity?: number;
  unitPrice?: number;
  unitPriceDiscount?: number;
  lineTotal?: number;
  taxRate?: number;
  pickedQuantity?: number;
  pickingCompletedWhen?: Moment;
  status?: OrderLineStatus;
  lastEditedBy?: string;
  lastEditedWhen?: Moment;
  stockItemName?: string;
  stockItemId?: number;
  packageTypeName?: string;
  packageTypeId?: number;
  orderId?: number;
}

export class OrderLines implements IOrderLines {
  constructor(
    public id?: number,
    public carrierTrackingNumber?: string,
    public quantity?: number,
    public unitPrice?: number,
    public unitPriceDiscount?: number,
    public lineTotal?: number,
    public taxRate?: number,
    public pickedQuantity?: number,
    public pickingCompletedWhen?: Moment,
    public status?: OrderLineStatus,
    public lastEditedBy?: string,
    public lastEditedWhen?: Moment,
    public stockItemName?: string,
    public stockItemId?: number,
    public packageTypeName?: string,
    public packageTypeId?: number,
    public orderId?: number
  ) {}
}
