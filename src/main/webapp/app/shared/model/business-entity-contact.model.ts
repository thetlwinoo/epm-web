export interface IBusinessEntityContact {
  id?: number;
  personId?: number;
  contactTypeName?: string;
  contactTypeId?: number;
}

export class BusinessEntityContact implements IBusinessEntityContact {
  constructor(public id?: number, public personId?: number, public contactTypeName?: string, public contactTypeId?: number) {}
}
