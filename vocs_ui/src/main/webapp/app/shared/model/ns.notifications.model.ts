export interface INsNotifications {
  id?: string;
  notificationType?: string;
  subscriptionId?: string;
  timeStamp?: string;
  crossingDirectionType?: string;
  performanceMetric?: string;
  performanceValue?: string;
  thresholdId?: string;
  objectInstanceId?: string;
}

export class NsNotifications implements INsNotifications {
  constructor(
    public id?: string,
    public notificationType?: string,
    public subscriptionId?: string,
    public timeStamp?: string,
    public crossingDirectionType?: string,
    public performanceMetric?: string,
    public performanceValue?: string,
    public thresholdId?: string,
    public objectInstanceId?: string
  ) {}
}
