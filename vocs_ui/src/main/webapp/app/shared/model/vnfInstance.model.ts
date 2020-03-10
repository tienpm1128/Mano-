import { IInstantiatedVnfInfo } from './instantiatedVnfInfo.model';

export interface IVnfInstance {
  id?: string;
  vnfInstanceName?: string;
  vnfInstanceDescription?: string;
  vnfdId?: string;
  vnfProvider?: string;
  vnfProductName?: string;
  vnfSoftwareVersion?: string;
  vnfdVersion?: string;
  vnfPkgId?: string;
  vnfConfigurableProperties?: string;
  vimId?: string;
  instantiationState?: string;
  instantiatedVnfInfo?: IInstantiatedVnfInfo;
  metadata?: string;
  extensions?: any;
  operationalState?: string;
  computeId?: string;
}

export class VnfInstance implements IVnfInstance {
  constructor(
    public id?: string,
    public vnfInstanceName?: string,
    public vnfInstanceDescription?: string,
    public vnfdId?: string,
    public vnfProvider?: string,
    public vnfProductName?: string,
    public vnfSoftwareVersion?: string,
    public vnfdVersion?: string,
    public vnfPkgId?: string,
    public vnfConfigurableProperties?: string,
    public vimId?: string,
    public instantiationState?: string,
    public instantiatedVnfInfo?: IInstantiatedVnfInfo,
    public metadata?: string,
    public extensions?: any,
    public operationalState?: string,
    public computeId?: string
  ) {}
}
