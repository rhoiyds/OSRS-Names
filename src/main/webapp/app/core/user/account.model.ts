import { TierType } from './user.model';

export class Account {
  constructor(
    public id: number,
    public activated: boolean,
    public authorities: string[],
    public email: string,
    public firstName: string,
    public langKey: string,
    public lastName: string,
    public username: string,
    public imageUrl: string,
    public tier: TierType
  ) {}
}
