export interface IShipMethod {
  id?: number;
  name?: string;
}

export class ShipMethod implements IShipMethod {
  constructor(public id?: number, public name?: string) {}
}
