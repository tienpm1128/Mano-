export interface INsd {
  id?: string;
  nsdName?: string;
  vnfPkgIds?: string;
  _links?: string;
  nestedNsdInfoIds?: string;
  nsdUsageState?: string;
  pnfdInfoIds?: string;
  nsdInvariantId?: string;
  nsdDesigner?: string;
  nsdVersion?: string;
  onboardingFailureDetails?: string;
  nsdId?: string;
  nsdOperationalStateType?: string;
  nsdOnboardingStateType?: string;
  userDefinedData?: string;
}

export class Nsd implements INsd {
  constructor(
    public id?: string,
    public nsdName?: string,
    public vnfPkgIds?: string,
    public _links?: string,
    public nestedNsdInfoIds?: string,
    public nsdUsageState?: string,
    public pnfdInfoIds?: string,
    public nsdInvariantId?: string,
    public nsdDesigner?: string,
    public nsdVersion?: string,
    public onboardingFailureDetails?: string,
    public nsdId?: string,
    public nsdOperationalStateType?: string,
    public nsdOnboardingStateType?: string,
    public userDefinedData?: string
  ) {}
}

export interface INsdInfoModifications extends INsd {
  nsdOperationalState?: string;
  timestamp?: number;
}

export class NsdInfoModifications implements INsdInfoModifications {
  public nsdOperationalState?: string;
  public userDefinedData?: string;
  public timestamp?: number;
}
