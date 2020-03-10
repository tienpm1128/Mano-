export interface IScaleVnfRequest {
  type?: string;
  aspectId?: string;
  numberOfSteps?: number;
  additionalParams?: {};
}

export class ScaleVnfRequest implements IScaleVnfRequest {
  constructor(public type?: string, public aspectId?: string, public numberOfSteps?: number, public additionalParams?: {}) {}
}
