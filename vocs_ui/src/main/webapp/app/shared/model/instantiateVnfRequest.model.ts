import { InstantiatedVnfInfoModel } from './instantiatedVnfInfo.model';
import { IVimConnectionInfo } from './vimConnectionInfo.model';

export interface IInstantiateVnfRequest {
  flavourId?: string;
  additionalParams?: {};
  instantiationLevelId?: string;
  extVirtualLinks?: string[];
  extManagedVirtualLinks?: string[];
  vimConnectionInfo?: IVimConnectionInfo[];
  localizationLanguage?: string;
  init(instantiatedVnfInfo: InstantiatedVnfInfoModel);
}

export class InstantiateVnfRequest implements IInstantiateVnfRequest {
  constructor(
    public flavourId?: string,
    public additionalParams?: {},
    public instantiationLevelId?: string,
    public extVirtualLinks?: string[],
    public extManagedVirtualLinks?: string[],
    public vimConnectionInfo?: IVimConnectionInfo[],
    public localizationLanguage?: string
  ) {}

  init(instantiatedVnfInfo: InstantiatedVnfInfoModel) {
    this.flavourId = instantiatedVnfInfo.flavourId;
    this.localizationLanguage = instantiatedVnfInfo.localizationLanguage;
    this.extVirtualLinks = instantiatedVnfInfo.extVirtualLinkInfo;
    this.extManagedVirtualLinks = instantiatedVnfInfo.extManagedVirtualLinkInfo;
  }
}
