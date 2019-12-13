export interface IPhoneNumberType {
  id?: number;
  name?: string;
}

export class PhoneNumberType implements IPhoneNumberType {
  constructor(public id?: number, public name?: string) {}
}
