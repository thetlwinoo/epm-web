import { Moment } from 'moment';

export interface IColdRoomTemperatures {
  id?: number;
  coldRoomSensorNumber?: number;
  recordedWhen?: Moment;
  temperature?: number;
  validFrom?: Moment;
  validTo?: Moment;
}

export class ColdRoomTemperatures implements IColdRoomTemperatures {
  constructor(
    public id?: number,
    public coldRoomSensorNumber?: number,
    public recordedWhen?: Moment,
    public temperature?: number,
    public validFrom?: Moment,
    public validTo?: Moment
  ) {}
}
