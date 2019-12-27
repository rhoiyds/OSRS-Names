import { Moment } from 'moment';
import { IOffer } from 'app/shared/model/offer.model';
import { IUser } from 'app/core/user/user.model';

export interface IComment {
  id?: number;
  timestamp?: Moment;
  text?: string;
  offer?: IOffer;
  owner?: IUser;
}

export class Comment implements IComment {
  constructor(public id?: number, public timestamp?: Moment, public text?: string, public offer?: IOffer, public owner?: IUser) {}
}
