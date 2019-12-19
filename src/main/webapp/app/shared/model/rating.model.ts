import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { ITrade } from 'app/shared/model/trade.model';

export interface IRating {
  id?: number;
  score?: number;
  message?: string;
  timestamp?: Moment;
  owner?: IUser;
  recipient?: IUser;
  trade?: ITrade;
}

export class Rating implements IRating {
  constructor(
    public id?: number,
    public score?: number,
    public message?: string,
    public timestamp?: Moment,
    public owner?: IUser,
    public recipient?: IUser,
    public trade?: ITrade
  ) {}
}
