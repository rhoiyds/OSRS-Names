import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { ITrade } from 'app/shared/model/trade.model';

export interface IRating {
  id?: number;
  timestamp?: Moment;
  rating?: number;
  description?: string;
  owner?: IUser;
  recipient?: IUser;
  trade?: ITrade;
}

export class Rating implements IRating {
  constructor(
    public id?: number,
    public timestamp?: Moment,
    public rating?: number,
    public description?: string,
    public owner?: IUser,
    public recipient?: IUser,
    public trade?: ITrade
  ) {}
}
