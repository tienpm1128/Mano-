export interface IfmNotifications {
  timeStamp?: string;
  _links?: string;
  alarm?: {
    isRootCause?: boolean;
    rootCauseFaultyResource?: string;
    alarmRaisedTime?: string;
    alarmClearedTime?: string;
    serviceInfo?: string;
    eventType?: string;
    alarmChangedTime?: string;
    ackState?: string;
    managedObjectId?: string;
    _links?: string;
    perceivedSeverity?: string;
    probableCause?: string;
    eventTime?: string;
    faultType?: string;
    correlatedAlarmIds?: string;
    faultDetails?: string;
    id?: string;
    rootCauseFaultyComponent?: string;
  };
  id?: string;
  notificationType?: string;
  subscriptionId?: string;
}

export class FmNotifications implements IfmNotifications {
  constructor(
    public timeStamp?: string,
    public _links?: string,
    public alarm?: {
      isRootCause?: boolean;
      rootCauseFaultyResource?: string;
      alarmRaisedTime?: string;
      alarmClearedTime?: string;
      serviceInfo?: string;
      eventType?: string;
      alarmChangedTime?: string;
      ackState?: string;
      managedObjectId?: string;
      _link?: string;
      perceivedSeverity?: string;
      probableCause?: string;
      eventTime?: string;
      faultType?: string;
      correlatedAlarmIds?: string;
      faultDetails?: string;
      id?: string;
      rootCauseFaultyComponent?: string;
    },
    public id?: string,
    public notificationType?: string,
    public subscriptionId?: string
  ) {}
}
