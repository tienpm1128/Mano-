export interface IAlarm {
  id?: string;
  managedObjectId?: string;
  rootCauseFaultyComponent?: {
    faultyNestedNsInstanceId: string;
    faultyNsVirtualLinkInstanceId: string;
    faultyVnfInstanceId: string;
  };
  rootCauseFaultyResource?: {
    faultyResource: any;
    faultyResourceType: string;
  };
  alarmRaisedTime?: string;
  alarmChangedTime?: string;
  alarmClearedTime?: string;
  ackState?: string;
  perceivedSeverity?: string;
  eventTime?: string;
  eventType?: string;
  faultType?: string;
  probableCause?: string;
  isRootCause?: boolean;
  correlatedAlarmIds?: [];
  faultDetails?: string;
  _links?: string;
  objectInstanceId?: string;
  subObjectInstanceId?: string;
  subObjectInstanceName?: string;
  subObjectInstanceIp?: string;
  managedObjectType?: string;
  managedObjectTargetType?: string;
  enabled?: string;
}

export class Alarm implements IAlarm {
  constructor(
    public id?: string,
    public managedObjectId?: string,
    public rootCauseFaultyComponent?: {
      faultyNestedNsInstanceId: string;
      faultyNsVirtualLinkInstanceId: string;
      faultyVnfInstanceId: string;
    },
    public rootCauseFaultyResource?: {
      faultyResource: string;
      faultyResourceType: string;
    },
    public alarmRaisedTime?: string,
    public alarmChangedTime?: string,
    public alarmClearedTime?: string,
    public ackState?: string,
    public perceivedSeverity?: string,
    public eventTime?: string,
    public eventType?: string,
    public faultType?: string,
    public probableCause?: string,
    public isRootCause?: boolean,
    public correlatedAlarmIds?: [],
    public faultDetails?: string,
    public objectInstanceId?: string,
    public subObjectInstanceId?: string,
    public subObjectInstanceName?: string,
    public subObjectInstanceIp?: string,
    public managedObjectType?: string,
    public managedObjectTargetType?: string,
    public enabled?: string,
    public _links?: string
  ) {}
}
