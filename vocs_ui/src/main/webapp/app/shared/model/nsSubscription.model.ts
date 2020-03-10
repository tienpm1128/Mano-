export interface INsSubscription {
  id?: string;
  filter?: string;
  callbackUri?: string;
  _links?: string;
}

export class NsSubscription implements INsSubscription {
  constructor(public id?: string, public filter?: string, public callbackUri?: string, public _links?: string) {}
}
