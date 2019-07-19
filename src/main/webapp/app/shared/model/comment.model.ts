import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { ITrade } from 'app/shared/model/trade.model';
import { IOffer } from 'app/shared/model/offer.model';
import { IMiddlemanRequest } from 'app/shared/model/middleman-request.model';

export interface IComment {
  id?: number;
  timestamp?: Moment;
  comment?: string;
  user?: IUser;
  owner?: ITrade;
  owner?: IOffer;
  owner?: IMiddlemanRequest;
}

export class Comment implements IComment {
  constructor(
    public id?: number,
    public timestamp?: Moment,
    public comment?: string,
    public user?: IUser,
    public owner?: ITrade,
    public owner?: IOffer,
    public owner?: IMiddlemanRequest
  ) {}
}
