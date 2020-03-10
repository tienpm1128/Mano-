export interface IFaultSubscription {
  filter?: {
    perceivedSeverities?: string[];
    nsInstanceSubscriptionFilter?: {
      nsdIds?: string[];
      vnfdIds?: string[];
      pnfdIds?: string[];
      nsInstanceIds?: string[];
      nsInstanceNames?: string[];
    };
    faultyResourceTypes?: string[];
    probableCauses?: string[];
    notificationTypes?: string[];
    eventTypes?: string[];
  };
  _links?: any;
  callbackUri?: string;
  id?: string;
}

export class FaultSubscription implements IFaultSubscription {
  constructor(
    public filter?: {
      perceivedSeverities?: string[];
      nsInstanceSubscriptionFilter?: {
        nsdIds?: string[];
        vnfdIds?: string[];
        pnfdIds?: string[];
        nsInstanceIds?: string[];
        nsInstanceNames?: string[];
      };
      faultyResourceTypes?: string[];
      probableCauses?: string[];
      notificationTypes?: string[];
      eventTypes?: string[];
    },
    public _links?: string,
    public callbackUri?: string,
    public id?: string
  ) {}
}
