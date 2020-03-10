import { IRole } from './roles.model';
import { IAuthorities } from './authorities.model';
import { IProjects } from './projects.model';

export interface IUserVim {
  id?: string;
  username?: string;
  password?: string;
  enabled?: boolean;
  roles?: IRole[];
  domainId?: string;
  domainName?: string;
  projects?: IProjects[];
  email?: string;
  description?: string;
  authorities?: IAuthorities;
  accountNonLocked?: string;
  accountNonExpired?: string;
  credentialsNonExpired?: string;
}

export class UserVim implements IUserVim {
  constructor(
    public id?: string,
    public username?: string,
    public password?: string,
    public enabled?: boolean,
    public roles?: IRole[],
    public domainId?: string,
    public domainName?: string,
    public projects?: IProjects[],
    public email?: string,
    public description?: string,
    public authorities?: IAuthorities,
    public accountNonExpired?: string,
    public accountNonLocked?: string,
    public credentialsNonExpired?: string
  ) {}
}

export class NewUserVim implements IUserVim {
  constructor(
    public username?: string,
    public password?: string,
    public enabled?: boolean,
    public roles?: IRole[],
    public domainId?: string,
    public domainName?: string,
    public projects?: IProjects[],
    public email?: string,
    public description?: string
  ) {}
}
