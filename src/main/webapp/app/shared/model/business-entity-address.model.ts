export interface IBusinessEntityAddress {
  id?: number;
  addressId?: number;
  personId?: number;
  addressTypeName?: string;
  addressTypeId?: number;
}

export class BusinessEntityAddress implements IBusinessEntityAddress {
  constructor(
    public id?: number,
    public addressId?: number,
    public personId?: number,
    public addressTypeName?: string,
    public addressTypeId?: number
  ) {}
}
