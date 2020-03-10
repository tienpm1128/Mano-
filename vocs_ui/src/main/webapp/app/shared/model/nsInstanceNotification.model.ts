export interface INsInstanceNotification {
  id?: string;
  subscriptionId?: string;
  timeStamp?: number;
  nsInstanceId?: string;
  _links?: string;
  notificationType?: string;
}

export class NsInstanceNotification implements INsInstanceNotification {
  constructor(
    public id?: string,
    public subscriptionId?: string,
    public timeStamp?: number,
    public nsInstanceId?: string,
    public _links?: string,
    public notificationType?: string
  ) {}
}
