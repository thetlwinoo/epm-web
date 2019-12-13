export interface IPersonEmailAddress {
  id?: number;
  emailAddress?: string;
  defaultInd?: boolean;
  activeInd?: boolean;
  personFullName?: string;
  personId?: number;
}

export class PersonEmailAddress implements IPersonEmailAddress {
  constructor(
    public id?: number,
    public emailAddress?: string,
    public defaultInd?: boolean,
    public activeInd?: boolean,
    public personFullName?: string,
    public personId?: number
  ) {
    this.defaultInd = this.defaultInd || false;
    this.activeInd = this.activeInd || false;
  }
}
