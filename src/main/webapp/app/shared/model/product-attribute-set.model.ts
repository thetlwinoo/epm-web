export interface IProductAttributeSet {
  id?: number;
  name?: string;
  productOptionSetValue?: string;
  productOptionSetId?: number;
}

export class ProductAttributeSet implements IProductAttributeSet {
  constructor(public id?: number, public name?: string, public productOptionSetValue?: string, public productOptionSetId?: number) {}
}
