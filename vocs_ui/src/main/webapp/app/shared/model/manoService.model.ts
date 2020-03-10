export interface IManoService {
  id?: string;
  clusterName?: string;
  clusterIp?: string;
  clusterPort?: number;
  instanceName?: string;
  defaultState?: string;
  microServiceType?: string;
  enabled?: boolean;
  master?: boolean;
  microServiceAddress?: {
    amqpAddress?: {
      exchange?: string;
      routingKey?: string;
      queueName?: string;
    };
    restAddress?: {
      ipExternal?: string;
      ipInternal?: string;
      port?: number;
    };
  };
}

export class ManoService implements IManoService {
  constructor(
    public id?: string,
    public clusterName?: string,
    public clusterIp?: string,
    public clusterPort?: number,
    public instanceName?: string,
    public defaultState?: string,
    public microServiceType?: string,
    public enabled?: boolean,
    public master?: boolean,
    public microServiceAddress?: {
      amqpAddress?: {
        exchange?: string;
        routingKey?: string;
        queueName?: string;
      };
      restAddress?: {
        ipExternal?: string;
        ipInternal?: string;
        port?: number;
      };
    }
  ) {}
}
