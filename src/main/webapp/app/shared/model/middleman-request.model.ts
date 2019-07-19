import { Moment } from 'moment';
import { ITrade } from 'app/shared/model/trade.model';
import { IUser } from 'app/core/user/user.model';
import { IComment } from 'app/shared/model/comment.model';

export interface IMiddlemanRequest {
  id?: number;
  timestamp?: Moment;
  description?: string;
  trade?: ITrade;
  user?: IUser;
  user?: IUser;
  comments?: IComment[];
}

export class MiddlemanRequest implements IMiddlemanRequest {
  constructor(
    public id?: number,
    public timestamp?: Moment,
    public description?: string,
    public trade?: ITrade,
    public user?: IUser,
    public user?: IUser,
    public comments?: IComment[]
  ) {}
}
