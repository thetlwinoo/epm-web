export interface IProductSet {
  id?: number;
  name?: string;
  noOfPerson?: number;
  isExclusive?: boolean;
}

export class ProductSet implements IProductSet {
  constructor(public id?: number, public name?: string, public noOfPerson?: number, public isExclusive?: boolean) {
    this.isExclusive = this.isExclusive || false;
  }
}
