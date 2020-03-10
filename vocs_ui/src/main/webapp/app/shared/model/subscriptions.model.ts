export interface ISubscriptions {
  id?: string;
  callbackUri?: string;
  filter?: {
    lcmOpNameImpactingNsComponent?: string;
    lcmOpOccStatusImpactingNsComponent?: string;
    notificationTypes?: string[];
    nsComponentTypes?: string[];
    nsInstanceSubscriptionFilter?: {
      nsInstanceIds?: string[];
      nsInstanceNames?: string[];
      nsdIds?: string[];
      pnfdIds?: string[];
      vnfdIds?: string[];
    };
    operationStates?: string[];
    operationTypes?: string[];
  };
  _links?: string;
}

export class Subscriptions implements ISubscriptions {
  constructor(
    public id?: string,
    public callbackUri?: string,
    public filter?: {
      lcmOpNameImpactingNsComponent?: string;
      lcmOpOccStatusImpactingNsComponent?: string;
      notificationTypes?: string[];
      nsComponentTypes?: string[];
      nsInstanceSubscriptionFilter?: {
        nsInstanceIds?: string[];
        nsInstanceNames?: string[];
        nsdIds?: string[];
        pnfdIds?: string[];
        vnfdIds?: string[];
      };
      operationStates?: string[];
      operationTypes?: string[];
    },
    public _links?: string
  ) {}
}
