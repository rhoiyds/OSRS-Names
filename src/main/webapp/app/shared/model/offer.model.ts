import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IListing } from 'app/shared/model/listing.model';

export enum OfferStatus {
  ACCEPTED = 'ACCEPTED',
  PENDING = 'PENDING',
  DECLINED = 'DECLINED',
  CANCELLED = 'CANCELLED'
}

export interface IOffer {
  id?: number;
  timestamp?: Moment;
  description?: string;
  status?: OfferStatus;
  owner?: IUser;
  listing?: IListing;
}

export class Offer implements IOffer {
  constructor(
    public id?: number,
    public timestamp?: Moment,
    public description?: string,
    public status?: OfferStatus,
    public owner?: IUser,
    public listing?: IListing
  ) {}
}
