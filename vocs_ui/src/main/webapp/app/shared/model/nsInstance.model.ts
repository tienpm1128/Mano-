import { IVnfInstance } from 'app/shared/model/vnfInstance.model';

export interface INsInstance {
  id?: string;
  nsInstanceName?: string;
  nsInstanceDescription?: string;
  nsdId?: string;
  nsdInfoId?: string;
  flavourId?: string;
  nsdOperationalState?: string;
  vnfInstances?: IVnfInstance[];
  pnfInfo?: string;
  virtualLinkInfo?: string;
  vnffgInfo?: string;
  sapInfo?: string;
  nestedNsInstanceId?: string;
  nsState?: string;
  monitoringParameter?: string;
  nsScaleStatus?: string;
}

export class NsInstance implements INsInstance {
  constructor(
    public id?: string,
    public nsInstanceName?: string,
    public nsInstanceDescription?: string,
    public nsdId?: string,
    public nsdInfoId?: string,
    public flavourId?: string,
    public nsdOperationalState?: string,
    public vnfInstances?: IVnfInstance[],
    public pnfInfo?: string,
    public virtualLinkInfo?: string,
    public vnffgInfo?: string,
    public sapInfo?: string,
    public nestedNsInstanceId?: string,
    public nsState?: string,
    public monitoringParameter?: string,
    public nsScaleStatus?: string
  ) {}
}

export class NewNsInstance implements INsInstance {
  constructor(public nsdId?: string, public nsName?: string, public nsDescription?: string) {}
}
