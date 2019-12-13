import { Moment } from 'moment';
import { IStockItems } from 'app/shared/model/stock-items.model';

export interface IProducts {
  id?: number;
  name?: string;
  handle?: string;
  productNumber?: string;
  searchDetails?: any;
  sellCount?: number;
  thumbnailList?: string;
  activeInd?: boolean;
  lastEditedBy?: string;
  lastEditedWhen?: Moment;
  productDocumentId?: number;
  stockItemLists?: IStockItems[];
  supplierName?: string;
  supplierId?: number;
  productCategoryName?: string;
  productCategoryId?: number;
  productBrandName?: string;
  productBrandId?: number;
}

export class Products implements IProducts {
  constructor(
    public id?: number,
    public name?: string,
    public handle?: string,
    public productNumber?: string,
    public searchDetails?: any,
    public sellCount?: number,
    public thumbnailList?: string,
    public activeInd?: boolean,
    public lastEditedBy?: string,
    public lastEditedWhen?: Moment,
    public productDocumentId?: number,
    public stockItemLists?: IStockItems[],
    public supplierName?: string,
    public supplierId?: number,
    public productCategoryName?: string,
    public productCategoryId?: number,
    public productBrandName?: string,
    public productBrandId?: number
  ) {
    this.activeInd = this.activeInd || false;
  }
}
