export interface IProductOptionSet {
  id?: number;
  value?: string;
}

export class ProductOptionSet implements IProductOptionSet {
  constructor(public id?: number, public value?: string) {}
}
