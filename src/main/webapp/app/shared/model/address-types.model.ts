export interface IAddressTypes {
  id?: number;
  name?: string;
  refer?: string;
}

export class AddressTypes implements IAddressTypes {
  constructor(public id?: number, public name?: string, public refer?: string) {}
}
