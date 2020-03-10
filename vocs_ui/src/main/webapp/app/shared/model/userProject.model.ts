export interface IUserProject {
  grantUserIds?: string[];
  grantProjectId?: string;
  roleId?: string;
}

export class UserProject implements IUserProject {
  constructor(public grantUserIds?: string[], public grantProjectId?: string, public roleId?: string) {}
}
