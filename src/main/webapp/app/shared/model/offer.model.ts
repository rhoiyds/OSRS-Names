import { Moment } from 'moment';
import { ITrade } from 'app/shared/model/trade.model';
import { IUser } from 'app/core/user/user.model';
import { IComment } from 'app/shared/model/comment.model';
import { IListing } from 'app/shared/model/listing.model';

export interface IOffer {
  id?: number;
  timestamp?: Moment;
  description?: string;
  trade?: ITrade;
  user?: IUser;
  comments?: IComment[];
  listing?: IListing;
}

export class Offer implements IOffer {
  constructor(
    public id?: number,
    public timestamp?: Moment,
    public description?: string,
    public trade?: ITrade,
    public user?: IUser,
    public comments?: IComment[],
    public listing?: IListing
  ) {}
}
