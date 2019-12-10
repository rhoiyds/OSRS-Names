import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';

export interface IComment {
  id?: number;
  timestamp?: Moment;
  text?: string;
  owner?: IUser;
}

export class Comment implements IComment {
  constructor(public id?: number, public timestamp?: Moment, public text?: string, public owner?: IUser) {}
}
