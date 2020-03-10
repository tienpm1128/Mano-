export interface IPmNotification {
  id?: string;
  notificationType?: string;
  subscriptionId?: string;
  timeStamp?: number;
  objectInstanceId?: number;
  performanceMetric?: string;
  performanceValue?: any;
  _links?: any;
}

export class PmNotification implements IPmNotification {
  constructor(
    public id?: string,
    public notificationType?: string,
    public subscriptionId?: string,
    public timeStamp?: number,
    public objectInstanceId?: number,
    public performanceMetric?: string,
    public performanceValue?: any,
    public _links?: any
  ) {}
}
