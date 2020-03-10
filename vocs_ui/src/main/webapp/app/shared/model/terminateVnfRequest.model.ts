export interface ITerminateVnfRequest {
  terminationType?: string;
  additionalParams?: {};
}

export class TerminateVnfRequest implements ITerminateVnfRequest {
  constructor(public terminationType?: string, public additionalParams?: {}) {}
}
