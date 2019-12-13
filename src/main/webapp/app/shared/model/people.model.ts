import { Moment } from 'moment';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface IPeople {
  id?: number;
  fullName?: string;
  preferredName?: string;
  searchName?: string;
  gender?: Gender;
  isPermittedToLogon?: boolean;
  logonName?: string;
  isExternalLogonProvider?: boolean;
  isSystemUser?: boolean;
  isEmployee?: boolean;
  isSalesPerson?: boolean;
  isGuestUser?: boolean;
  emailPromotion?: number;
  userPreferences?: string;
  phoneNumber?: string;
  emailAddress?: string;
  photo?: string;
  customFields?: string;
  otherLanguages?: string;
  validFrom?: Moment;
  validTo?: Moment;
  userLogin?: string;
  userId?: number;
  cartId?: number;
  wishlistId?: number;
  compareId?: number;
}

export class People implements IPeople {
  constructor(
    public id?: number,
    public fullName?: string,
    public preferredName?: string,
    public searchName?: string,
    public gender?: Gender,
    public isPermittedToLogon?: boolean,
    public logonName?: string,
    public isExternalLogonProvider?: boolean,
    public isSystemUser?: boolean,
    public isEmployee?: boolean,
    public isSalesPerson?: boolean,
    public isGuestUser?: boolean,
    public emailPromotion?: number,
    public userPreferences?: string,
    public phoneNumber?: string,
    public emailAddress?: string,
    public photo?: string,
    public customFields?: string,
    public otherLanguages?: string,
    public validFrom?: Moment,
    public validTo?: Moment,
    public userLogin?: string,
    public userId?: number,
    public cartId?: number,
    public wishlistId?: number,
    public compareId?: number
  ) {
    this.isPermittedToLogon = this.isPermittedToLogon || false;
    this.isExternalLogonProvider = this.isExternalLogonProvider || false;
    this.isSystemUser = this.isSystemUser || false;
    this.isEmployee = this.isEmployee || false;
    this.isSalesPerson = this.isSalesPerson || false;
    this.isGuestUser = this.isGuestUser || false;
  }
}
