export interface IPmJobs {
  id?: string;
  objectInstanceIds?: string[];
  criteria?: {
    performanceMetric?: string[];
    performanceMetricGroup?: string[];
    collectionPeriod?: string;
    reportingPeriod?: string;
    reportingBoundary?: string;
  };
  reports?: any;
  _links?: any;
  performanceReports?: string;
}

export class PmJobs implements IPmJobs {
  constructor(
    public id?: string,
    public objectInstanceIds?: string[],
    public criteria?: {
      performanceMetric?: string[];
      performanceMetricGroup?: string[];
      collectionPeriod?: string;
      reportingPeriod?: string;
      reportingBoundary?: string;
    },
    public reports?: any,
    public _links?: any,
    performanceReports?: string
  ) {}
}
