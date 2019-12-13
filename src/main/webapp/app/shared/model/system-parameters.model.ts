export interface ISystemParameters {
  id?: number;
  applicationSettings?: string;
  deliveryCityName?: string;
  deliveryCityId?: number;
  postalCityName?: string;
  postalCityId?: number;
}

export class SystemParameters implements ISystemParameters {
  constructor(
    public id?: number,
    public applicationSettings?: string,
    public deliveryCityName?: string,
    public deliveryCityId?: number,
    public postalCityName?: string,
    public postalCityId?: number
  ) {}
}
