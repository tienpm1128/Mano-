import { IAccessInfo } from './accessInfo.model';

export interface IVimConnectionInfo {
  id?: string;
  vimId?: string;
  vimName?: string;
  vimType?: string;
  interfaceInfo?: {};
  accessInfo?: IAccessInfo;
  extra?: {};
}

export class VimConnectionInfo implements IVimConnectionInfo {
  constructor(
    public id?: string,
    public vimId?: string,
    public vimName?: string,
    public vimType?: string,
    public interfaceInfo?: {},
    public accessInfo?: IAccessInfo,
    public extra?: {}
  ) {}
}
