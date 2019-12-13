export interface IUnitMeasure {
  id?: number;
  code?: string;
  name?: string;
}

export class UnitMeasure implements IUnitMeasure {
  constructor(public id?: number, public code?: string, public name?: string) {}
}
