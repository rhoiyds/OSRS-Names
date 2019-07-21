import { IOffer } from 'app/shared/model/offer.model';

export const enum TradeStatusType {
  AWAITING_MIDDLEMAN = 'AWAITING_MIDDLEMAN',
  CANCELLED = 'CANCELLED',
  COMPLETED = 'COMPLETED'
}

export interface ITrade {
  id?: number;
  status?: TradeStatusType;
  offer?: IOffer;
}

export class Trade implements ITrade {
  constructor(public id?: number, public status?: TradeStatusType, public offer?: IOffer) {}
}
