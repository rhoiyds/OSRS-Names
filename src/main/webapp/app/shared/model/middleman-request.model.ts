import { Moment } from 'moment';
import { ITrade } from 'app/shared/model/trade.model';
import { IUser } from 'app/core/user/user.model';

export interface IMiddlemanRequest {
  id?: number;
  timestamp?: Moment;
  description?: string;
  trade?: ITrade;
  owner?: IUser;
  recipient?: IUser;
}

export class MiddlemanRequest implements IMiddlemanRequest {
  constructor(
    public id?: number,
    public timestamp?: Moment,
    public description?: string,
    public trade?: ITrade,
    public owner?: IUser,
    public recipient?: IUser
  ) {}
}
