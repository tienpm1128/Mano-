import { IRole } from './roles.model';
import { IAuthorities } from './authorities.model';

export interface IUserMano {
  id?: string;
  username?: string;
  password?: string;
  enabled?: boolean;
  roles?: IRole[];
  authorities?: IAuthorities[];
  accountNonLocked?: boolean;
  accountNonExpired?: boolean;
  credentialsNonExpired?: boolean;
}

export class UserMano implements IUserMano {
  constructor(
    public id?: string,
    public username?: string,
    public password?: string,
    public enabled?: boolean,
    public roles?: IRole[],
    public authorities?: IAuthorities[],
    public accountNonLocked?: boolean,
    public accountNonExpired?: boolean,
    public credentialsNonExpired?: boolean
  ) {}
}

export class NewUserMano {
  constructor(
    public manoUsername?: string,
    public manoUserPassword?: string,
    public assignedTenant?: string,
    public manoUserRoles?: [
      {
        roleType?: string;
      }
    ]
  ) {}
}
