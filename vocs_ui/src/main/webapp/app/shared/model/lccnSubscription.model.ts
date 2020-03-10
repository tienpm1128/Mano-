export interface ILccnSubscription {
  id?: string;
  filter?: any;
  callbackUri?: string;
  _links?: string;
}

export class LccnSubscription implements ILccnSubscription {
  constructor(public id?: string, public filter?: any, public callbackUri?: string, public _links?: string) {}
}
