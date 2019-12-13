export interface ICulture {
  id?: number;
  code?: string;
  name?: string;
}

export class Culture implements ICulture {
  constructor(public id?: number, public code?: string, public name?: string) {}
}
