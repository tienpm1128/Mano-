export interface INetWork {
  id?: string;
  networkResourceName?: string;
  subnet?: string[];
  networkPort?: string[];
  bandwidth?: string;
  networkType?: string;
  segmentType?: string;
  networkQoS?: string[];
  isShared?: boolean;
  sharingCriteria?: string;
  zoneId?: string;
  operationalState?: string;
  metadata?: string;
  providerPhyNet?: string;
  routerExternal?: boolean;
}

export class NetworkModel implements INetWork {
  constructor(
    public id?: string,
    public networkResourceName?: string,
    public subnet?: string[],
    public networkPort?: string[],
    public bandwidth?: string,
    public networkType?: string,
    public segmentType?: string,
    public networkQoS?: string[],
    public isShared?: boolean,
    public sharingCriteria?: string,
    public zoneId?: string,
    public operationalState?: string,
    public metadata?: string,
    public providerPhyNet?: string,
    public routerExternal?: boolean
  ) {}
}
