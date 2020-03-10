export interface IPerformanceNotification {
  id?: string;
  notificationType?: string;
  subscriptionId?: string;
  timeStamp?: number;
  objectInstanceId?: string;
  _links?: {
    subscription?: {
      href?: string;
    };
    objectInstance?: {
      href?: string;
    };
    pmJob?: {
      href?: string;
    };
    performanceReport?: {
      href?: string;
    };
  };
}

export class PerformanceNotification implements IPerformanceNotification {
  constructor(
    public id?: string,
    public notificationType?: string,
    public subscriptionId?: string,
    public timeStamp?: number,
    public objectInstanceId?: string,
    public _links?: {
      subscription?: {
        href?: string;
      };
      objectInstance?: {
        href?: string;
      };
      pmJob?: {
        href?: string;
      };
      performanceReport?: {
        href?: string;
      };
    }
  ) {}
}
