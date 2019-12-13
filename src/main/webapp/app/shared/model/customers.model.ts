export interface ICustomers {
  id?: number;
  accountNumber?: string;
  userLogin?: string;
  userId?: number;
}

export class Customers implements ICustomers {
  constructor(public id?: number, public accountNumber?: string, public userLogin?: string, public userId?: number) {}
}
