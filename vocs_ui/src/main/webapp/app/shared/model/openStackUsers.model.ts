import { IProjects } from './projects.model';

export interface IOpenStackUsers {
  id?: string;
  username?: string;
  password?: string;
  enabled?: string;
  roles?: string;
  domainId?: string;
  domainName?: string;
  projects?: IProjects[];
}

export class OpenStackUsersModel implements IOpenStackUsers {
  constructor(
    public id?: string,
    public username?: string,
    public password?: string,
    public enabled?: string,
    public roles?: string,
    public domainId?: string,
    public domainName?: string,
    public projects?: IProjects[]
  ) {}
}
