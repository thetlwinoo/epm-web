import { Moment } from 'moment';

export interface IPackageTypes {
  id?: number;
  name?: string;
  validFrom?: Moment;
  validTo?: Moment;
}

export class PackageTypes implements IPackageTypes {
  constructor(public id?: number, public name?: string, public validFrom?: Moment, public validTo?: Moment) {}
}
