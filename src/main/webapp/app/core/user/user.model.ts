import { GRAVATAR_AVATAR_PATH, GRAVATAR_BASE_URL, GRAVATAR_PARAMETERS } from 'app/shared/constants/gravatar.constants';

export interface IUser {
  id?: any;
  username?: string;
  firstName?: string;
  lastName?: string;
  email?: string;
  activated?: boolean;
  langKey?: string;
  authorities?: any[];
  createdBy?: string;
  createdDate?: Date;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;
  password?: string;
  imageUrl?: string;
}

export class User implements IUser {
  constructor(
    public id?: any,
    public username?: string,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public activated?: boolean,
    public langKey?: string,
    public authorities?: any[],
    public createdBy?: string,
    public createdDate?: Date,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Date,
    public password?: string,
    public imageUrl?: string
  ) {
    this.id = id ? id : null;
    this.username = username ? username : null;
    this.firstName = firstName ? firstName : null;
    this.lastName = lastName ? lastName : null;
    this.email = email ? email : null;
    this.activated = activated ? activated : false;
    this.langKey = langKey ? langKey : null;
    this.authorities = authorities ? authorities : null;
    this.createdBy = createdBy ? createdBy : null;
    this.createdDate = createdDate ? createdDate : null;
    this.lastModifiedBy = lastModifiedBy ? lastModifiedBy : null;
    this.lastModifiedDate = lastModifiedDate ? lastModifiedDate : null;
    this.password = password ? password : null;
    this.imageUrl = imageUrl ? GRAVATAR_BASE_URL + GRAVATAR_AVATAR_PATH + imageUrl + GRAVATAR_PARAMETERS : null;
  }
}
