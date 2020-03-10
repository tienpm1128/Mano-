export interface IThresholdCrossedNotification {
  id?: string;
  notificationType?: string;
  subscriptionId?: string;
  timeStamp?: number;
  thresholdId?: string;
  crossingDirection?: string;
  objectInstanceId?: string;
  performanceMetric?: string;
  performanceValue?: string;
  _links?: {
    subscription?: {
      href?: string;
    };
    objectInstance?: {
      href?: string;
    };
    threshold?: {
      href?: string;
    };
  };
}

export class ThresholdCrossedNotification implements IThresholdCrossedNotification {
  constructor(
    public id?: string,
    public notificationType?: string,
    public subscriptionId?: string,
    public timeStamp?: number,
    public thresholdId?: string,
    public crossingDirection?: string,
    public objectInstanceId?: string,
    public performanceMetric?: string,
    public performanceValue?: string,
    public _links?: {
      subscription?: {
        href?: string;
      };
      objectInstance?: {
        href?: string;
      };
      threshold?: {
        href?: string;
      };
    }
  ) {}
}
