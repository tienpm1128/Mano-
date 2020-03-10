export interface IAnalyze {
  nsDescriptors?: number;
  nsInstances?: number;
  vnfDesciptions?: number;
  vnfInstances?: number;
}

export class Analyze implements IAnalyze {
  constructor(public nsDescriptors?: number, public nsInstances?: number, public vnfDesciptions?: number, public vnfInstances?: number) {}
}
