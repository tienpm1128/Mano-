export interface IInstantiatedVnfInfo {
  flavourId?: string;
  vnfState?: string;
  scaleStatus?: string;
  extCpInfo?: string;
  extVirtualLinkInfo?: string[];
  extManagedVirtualLinkInfo?: string[];
  monitoringParameters?: string;
  localizationLanguage?: string;
  vnfcResourceInfo?: string;
  vnfVirtualLinkResourceInfo?: string;
  virtualStorageResourceInfo?: string;
  vnfcInfo?: any[];
}

export class InstantiatedVnfInfoModel implements IInstantiatedVnfInfo {
  constructor(
    public flavourId?: string,
    public vnfState?: string,
    public scaleStatus?: string,
    public extCpInfo?: string,
    public extVirtualLinkInfo?: string[],
    public extManagedVirtualLinkInfo?: string[],
    public monitoringParameters?: string,
    public localizationLanguage?: string,
    public vnfcResourceInfo?: string,
    public vnfVirtualLinkResourceInfo?: string,
    public virtualStorageResourceInfo?: string,
    public vnfcInfo?: any[]
  ) {}
}
