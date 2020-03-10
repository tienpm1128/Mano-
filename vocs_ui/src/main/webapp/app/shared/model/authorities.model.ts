export interface IAuthorities {
  roleType?: string;
  authority?: string;
}

export class AuthoritiesModel implements IAuthorities {
  constructor(public roleType?: string, public authority?: string) {}
}
