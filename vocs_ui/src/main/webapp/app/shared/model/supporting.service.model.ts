export interface ISupportingService {
  endPoint: string;
  port: number;
  supportingServiceType: string;
  ip: string;
  id: string;
  message: string;
  enabled: boolean;
}

export class SupportingService implements ISupportingService {
  constructor(
    public endPoint: string,
    public port: number,
    public supportingServiceType: string,
    public ip: string,
    public id: string,
    public message: string,
    public enabled: boolean
  ) {}
}
