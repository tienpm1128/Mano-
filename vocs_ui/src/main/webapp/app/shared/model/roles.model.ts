export interface IRole {
  roleType?: string;
  authority?: string;
}

export class RoleModel implements IRole {
  constructor(public roleType?: string, public authority?: string) {}
}
