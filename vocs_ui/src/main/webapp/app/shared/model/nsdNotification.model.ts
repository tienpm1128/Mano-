export interface INsdNotification {
  id?: string;
  notificationType?: string;
  subscriptionId?: string;
  timeStamp?: string;
  nsdInfoId?: string;
  nsdId?: string;
  nsdOperationalState?: string;
  _links?: string;
}

export class NsdNotification implements INsdNotification {
  constructor(
    public id?: string,
    public notificationType?: string,
    public subscriptionId?: string,
    public timeStamp?: string,
    public nsdInfoId?: string,
    public nsdId?: string,
    public nsdOperationalState?: string,
    public _links?: string
  ) {}
}
