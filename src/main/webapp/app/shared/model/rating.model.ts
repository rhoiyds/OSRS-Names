import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { ITrade } from 'app/shared/model/trade.model';

export interface IRating {
  id?: number;
  timestamp?: Moment;
  rating?: number;
  description?: string;
  user?: IUser;
  user?: IUser;
  trade?: ITrade;
}

export class Rating implements IRating {
  constructor(
    public id?: number,
    public timestamp?: Moment,
    public rating?: number,
    public description?: string,
    public user?: IUser,
    public user?: IUser,
    public trade?: ITrade
  ) {}
}
