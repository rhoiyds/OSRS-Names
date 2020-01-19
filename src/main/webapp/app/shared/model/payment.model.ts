import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';

export interface IPayment {
  id?: number;
  orderId?: string;
  timestamp?: Moment;
  user?: IUser;
}

export class Payment implements IPayment {
  constructor(public id?: number, public orderId?: string, public timestamp?: Moment, public user?: IUser) {}
}
