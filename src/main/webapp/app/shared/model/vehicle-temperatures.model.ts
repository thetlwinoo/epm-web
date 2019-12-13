export interface IVehicleTemperatures {
  id?: number;
  vehicleRegistration?: number;
  chillerSensorNumber?: string;
  recordedWhen?: number;
  temperature?: number;
  isCompressed?: boolean;
  fullSensorData?: string;
  compressedSensorData?: string;
}

export class VehicleTemperatures implements IVehicleTemperatures {
  constructor(
    public id?: number,
    public vehicleRegistration?: number,
    public chillerSensorNumber?: string,
    public recordedWhen?: number,
    public temperature?: number,
    public isCompressed?: boolean,
    public fullSensorData?: string,
    public compressedSensorData?: string
  ) {
    this.isCompressed = this.isCompressed || false;
  }
}
