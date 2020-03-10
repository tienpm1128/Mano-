export interface IRoleVim {
  id?: string;
  name?: string;
}

export class RoleModel implements IRoleVim {
  constructor(public id?: string, public name?: string) {}
}

export class AddNewRoleModel implements IRoleVim {
  constructor(public name?: string) {}
}
