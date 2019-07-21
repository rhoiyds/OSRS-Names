import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';

export const enum ListingType {
  WANT = 'WANT',
  HAVE = 'HAVE'
}

export interface IListing {
  id?: number;
  timestamp?: Moment;
  type?: ListingType;
  rsn?: string;
  amount?: number;
  description?: string;
  owner?: IUser;
}

export class Listing implements IListing {
  constructor(
    public id?: number,
    public timestamp?: Moment,
    public type?: ListingType,
    public rsn?: string,
    public amount?: number,
    public description?: string,
    public owner?: IUser
  ) {}
}
