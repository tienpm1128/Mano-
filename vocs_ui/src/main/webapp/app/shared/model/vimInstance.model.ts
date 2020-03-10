import { IOpenStackUsers } from './openStackUsers.model';

export interface IVimInstance {
  id?: string;
  name?: string;
  authUrl?: string;
  controllerHaIp?: string;
  controllerPhysicalIps?: string[];
  domain?: string;
  type?: string;
  location?: {
    id: string;
    latitude: string;
    longitude: string;
    name: string;
  };
  description?: string;
  active?: boolean;
  managementSubnet?: string;
  version?: string;
  openStackUsers?: IOpenStackUsers[];
}

export class VimInstanceModel implements IVimInstance {
  constructor(
    public id?: string,
    public name?: string,
    public authUrl?: string,
    public controllerHaIp?: string,
    public controllerPhysicalIps?: string[],
    public domain?: string,
    public type?: string,
    public location?: {
      id: string;
      latitude: string;
      longitude: string;
      name: string;
    },
    public description?: string,
    public active?: boolean,
    public managementSubnet?: string,
    public version?: string,
    public openStackUsers?: IOpenStackUsers[]
  ) {}
}

export class NewVimInstance implements IVimInstance {
  constructor(
    public name?: string,
    public authUrl?: string,
    public controllerHaIp?: string,
    public controllerPhysicalIps?: string[],
    public domain?: string,
    public type?: string,
    public location?: any,
    public description?: string,
    public managementSubnet?: string
  ) {}
}

export interface IVimSubscriptionRequest {
  filter?: {
    vimIds?: string[];
  };
  callbackUri?: string;
}

export class VimSubscriptionRequest implements IVimSubscriptionRequest {
  constructor(
    public filter?: {
      vimIds?: string[];
    },
    public callbackUri?: string
  ) {}
}
