import { Moment } from 'moment';

export interface ISuppliers {
  id?: number;
  name?: string;
  supplierReference?: string;
  bankAccountName?: string;
  bankAccountBranch?: string;
  bankAccountCode?: string;
  bankAccountNumber?: string;
  bankInternationalCode?: string;
  paymentDays?: number;
  internalComments?: string;
  phoneNumber?: string;
  faxNumber?: string;
  websiteURL?: string;
  webServiceUrl?: string;
  creditRating?: number;
  activeFlag?: boolean;
  thumbnailUrl?: string;
  validFrom?: Moment;
  validTo?: Moment;
  userLogin?: string;
  userId?: number;
  supplierCategoryName?: string;
  supplierCategoryId?: number;
  deliveryMethodName?: string;
  deliveryMethodId?: number;
  deliveryCityName?: string;
  deliveryCityId?: number;
  postalCityName?: string;
  postalCityId?: number;
}

export class Suppliers implements ISuppliers {
  constructor(
    public id?: number,
    public name?: string,
    public supplierReference?: string,
    public bankAccountName?: string,
    public bankAccountBranch?: string,
    public bankAccountCode?: string,
    public bankAccountNumber?: string,
    public bankInternationalCode?: string,
    public paymentDays?: number,
    public internalComments?: string,
    public phoneNumber?: string,
    public faxNumber?: string,
    public websiteURL?: string,
    public webServiceUrl?: string,
    public creditRating?: number,
    public activeFlag?: boolean,
    public thumbnailUrl?: string,
    public validFrom?: Moment,
    public validTo?: Moment,
    public userLogin?: string,
    public userId?: number,
    public supplierCategoryName?: string,
    public supplierCategoryId?: number,
    public deliveryMethodName?: string,
    public deliveryMethodId?: number,
    public deliveryCityName?: string,
    public deliveryCityId?: number,
    public postalCityName?: string,
    public postalCityId?: number
  ) {
    this.activeFlag = this.activeFlag || false;
  }
}
