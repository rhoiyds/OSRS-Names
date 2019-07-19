import { IComment } from 'app/shared/model/comment.model';
import { IOffer } from 'app/shared/model/offer.model';
import { IMiddlemanRequest } from 'app/shared/model/middleman-request.model';
import { IRating } from 'app/shared/model/rating.model';

export const enum TradeStatusType {
  AWAITING_MIDDLEMAN = 'AWAITING_MIDDLEMAN',
  CANCELLED = 'CANCELLED',
  COMPLETED = 'COMPLETED'
}

export interface ITrade {
  id?: number;
  status?: TradeStatusType;
  comments?: IComment[];
  offer?: IOffer;
  middlemanRequest?: IMiddlemanRequest;
  rating?: IRating;
}

export class Trade implements ITrade {
  constructor(
    public id?: number,
    public status?: TradeStatusType,
    public comments?: IComment[],
    public offer?: IOffer,
    public middlemanRequest?: IMiddlemanRequest,
    public rating?: IRating
  ) {}
}
