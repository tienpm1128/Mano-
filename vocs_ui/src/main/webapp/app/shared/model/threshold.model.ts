export interface IThreshold {
  id?: string;
  objectInstanceId?: string;
  criteria?: {
    performanceMetric?: string;
    thresholdType?: string;
    simpleThresholdDetails?: any;
  };
  _links?: any;
}

export class Threshold implements IThreshold {
  constructor(
    public id?: string,
    public objectInstanceId?: string,
    public criteria?: {
      performanceMetric?: string;
      thresholdType?: string;
      simpleThresholdDetails?: {
        thresholdValue?: string;
        hysteresis?: string;
        thresholdUpValue?: string;
        thresholdDownValue?: string;
      };
    },
    public _links?: any
  ) {}
}
