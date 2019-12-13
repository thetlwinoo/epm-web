export interface IProductSetDetails {
  id?: number;
  subGroupNo?: number;
  subGroupMinCount?: number;
  subGroupMinTotal?: number;
  minCount?: number;
  maxCount?: number;
  isOptional?: boolean;
}

export class ProductSetDetails implements IProductSetDetails {
  constructor(
    public id?: number,
    public subGroupNo?: number,
    public subGroupMinCount?: number,
    public subGroupMinTotal?: number,
    public minCount?: number,
    public maxCount?: number,
    public isOptional?: boolean
  ) {
    this.isOptional = this.isOptional || false;
  }
}
