export interface IProjects {
  projectId?: string;
  projectName?: string;
  projectDescription?: string;
  domainName?: string;
  userRoles?: any;
  enabled?: boolean;
}

export class Projects implements IProjects {
  constructor(
    public projectId?: string,
    public projectName?: string,
    public projectDescription?: string,
    public domainName?: string,
    public userRoles?: any,
    public enabled?: boolean
  ) {}
}

export interface IAddnewProjects {
  projectId?: string;
  projectName?: string;
  projectDescription?: string;
  enabled?: boolean;
}

export class AddNewProjects implements IAddnewProjects {
  constructor(
    public projectId?: string,
    public projectName?: string,
    public projectDescription?: string,
    public userRoles?: any[],
    public enabled?: boolean
  ) {}
}

export interface IAddnewUserProjects {
  grantUserIds?: [];
  grantProjectId?: string;
  roleId?: string;
}

export class AddnewUserProjects implements IAddnewUserProjects {
  constructor(public grantUserIds?: [], public grantProjectId?: string, public roleId?: string) {}
}
