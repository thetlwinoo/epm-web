export interface IPersonPhone {
  id?: number;
  phoneNumber?: string;
  defaultInd?: boolean;
  activeInd?: boolean;
  personFullName?: string;
  personId?: number;
  phoneNumberTypeName?: string;
  phoneNumberTypeId?: number;
}

export class PersonPhone implements IPersonPhone {
  constructor(
    public id?: number,
    public phoneNumber?: string,
    public defaultInd?: boolean,
    public activeInd?: boolean,
    public personFullName?: string,
    public personId?: number,
    public phoneNumberTypeName?: string,
    public phoneNumberTypeId?: number
  ) {
    this.defaultInd = this.defaultInd || false;
    this.activeInd = this.activeInd || false;
  }
}
