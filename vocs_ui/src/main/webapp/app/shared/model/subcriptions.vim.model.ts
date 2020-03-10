export interface ISubscriptionsVim {
  id?: string;
  filter?: {
    computeFilters?: [
      {
        computeId?: string;
        computeIp?: string;
      }
    ];
  };
  callbackUri?: string;
}

export class SubscriptionsVim implements ISubscriptionsVim {
  constructor(
    public id?: string,
    public filter?: {
      computeFilters?: [
        {
          computeId?: string;
          computeIp?: string;
        }
      ];
    },
    public callbackUri?: string
  ) {}
}
