export interface IAccessInfo {
  USER?: string;
  USER_NAME?: string;
  PASSWORD?: string;
  PROJECT?: string;
}

export class AccessInfo implements IAccessInfo {
  constructor(public USER?: string, public USER_NAME?: string, public PASSWORD?: string, public PROJECT?: string) {}
}
