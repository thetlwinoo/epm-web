export interface IUploadActionTypes {
  id?: number;
  name?: string;
}

export class UploadActionTypes implements IUploadActionTypes {
  constructor(public id?: number, public name?: string) {}
}
