export interface IPlan {
  id?: string;
  name?: string;
  cost?: string;
}

export class Plan implements IPlan {
  constructor(public id?: string, public name?: string, public cost?: string) {}
}
