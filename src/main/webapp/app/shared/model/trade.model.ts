import { IOffer } from 'app/shared/model/offer.model';

export enum TradeStatus {
  PENDING = 'PENDING',
  CONFIRMED = 'CONFIRMED',
  DECLINED = 'DECLINED'
}

export interface ITrade {
  id?: number;
  listingOwnerStatus?: TradeStatus;
  offerOwnerStatus?: TradeStatus;
  offer?: IOffer;
}

export class Trade implements ITrade {
  constructor(public id?: number, public listingOwnerStatus?: TradeStatus, public offerOwnerStatus?: TradeStatus, public offer?: IOffer) {}
}
