export interface IServerGroup {
  id?: string;
  name?: string;
  rule?: string;
  members?: string[];
  metadata?: {};
}

export class Servergroup implements IServerGroup {
  constructor(public id?: string, public name?: string, public rule?: string, public members?: string[], public metadata?: {}) {}
}

export interface INewServerGroup {
  serverGroupName?: string;
  serverGroupRule?: string;
}

export class NewServergroup implements INewServerGroup {
  constructor(public serverGroupName?: string, public serverGroupRule?: string) {}
}
