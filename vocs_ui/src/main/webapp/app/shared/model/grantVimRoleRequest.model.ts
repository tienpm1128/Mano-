export interface IGrantVimRoleRequest {
  manoUserGrantedId?: string;
  vimRole?: string;
}

export class GrantVimRoleRequestModel implements IGrantVimRoleRequest {
  constructor(public manoUserGrantedId?: string, public vimRole?: string) {}
}
