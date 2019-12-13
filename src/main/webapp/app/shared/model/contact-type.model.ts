export interface IContactType {
  id?: number;
  name?: string;
}

export class ContactType implements IContactType {
  constructor(public id?: number, public name?: string) {}
}
