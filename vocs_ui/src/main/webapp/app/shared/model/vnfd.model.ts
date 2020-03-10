export interface IVnfd {
  vnfProductName?: string;
  vnfProvider?: string;
  vdu?: any;
  lifeCycleManagementScript?: any;
  vnfProductInfoDescription?: any;
  intVirtualLinkDesc?: any;
  configurableProperties?: any;
  requiredOutputs?: any;
  autoScale?: any;
  modifiableAttributes?: any;
  vnfExtCpd?: any;
  localizationLanguage?: any;
  securityGroupRule?: any;
  defaultLocalizationLanguage?: string;
  vnfProductInfoName?: string;
  vnfSoftwareVersion?: string;
  vnfdVersion?: string;
  vnfmInfo?: any;
  vnfdId?: any;
  virtualComputeDesc?: any;
  vnfIndicator?: any;
  virtualStorageDesc?: any;
  deploymentFlavour?: any;
  swImageDesc?: any;
  vnfScriptsLocation?: string;
}

export class Vnfd implements IVnfd {
  constructor(
    public vnfProductName?: string,
    public vnfProvider?: string,
    public vdu?: any,
    public lifeCycleManagementScript?: any,
    public vnfProductInfoDescription?: any,
    public intVirtualLinkDesc?: any,
    public configurableProperties?: any,
    public requiredOutputs?: any,
    public autoScale?: any,
    public modifiableAttributes?: any,
    public vnfExtCpd?: any,
    public localizationLanguage?: any,
    public securityGroupRule?: any,
    public defaultLocalizationLanguage?: string,
    public vnfProductInfoName?: string,
    public vnfSoftwareVersion?: string,
    public vnfdVersion?: string,
    public vnfmInfo?: any,
    public vnfdId?: any,
    public virtualComputeDesc?: any,
    public vnfIndicator?: any,
    public virtualStorageDesc?: any,
    public deploymentFlavour?: any,
    public swImageDesc?: any,
    public vnfScriptsLocation?: string
  ) {}
}
