export interface ICompareProducts {
  id?: number;
  productName?: string;
  productId?: number;
  compareId?: number;
}

export class CompareProducts implements ICompareProducts {
  constructor(public id?: number, public productName?: string, public productId?: number, public compareId?: number) {}
}
