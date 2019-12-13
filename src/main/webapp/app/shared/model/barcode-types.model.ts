export interface IBarcodeTypes {
  id?: number;
  name?: string;
}

export class BarcodeTypes implements IBarcodeTypes {
  constructor(public id?: number, public name?: string) {}
}
