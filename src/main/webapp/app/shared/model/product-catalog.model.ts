export interface IProductCatalog {
  id?: number;
  productCategoryName?: string;
  productCategoryId?: number;
  productName?: string;
  productId?: number;
}

export class ProductCatalog implements IProductCatalog {
  constructor(
    public id?: number,
    public productCategoryName?: string,
    public productCategoryId?: number,
    public productName?: string,
    public productId?: number
  ) {}
}
