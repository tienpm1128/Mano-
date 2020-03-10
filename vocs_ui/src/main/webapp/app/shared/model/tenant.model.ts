export interface ITenant {
  resourceToIdMap: string;
  name: string;
  description: string;
  id: string;
  users: [
    {
      password: string;
      roles: [
        {
          authority: string;
          roleType: string;
        }
      ];
      credentialsNonExpired: boolean;
      accountNonExpired: boolean;
      id: string;
      enabled: boolean;
      authorities: [
        {
          authority: string;
          roleType: string;
        }
      ];
      username: string;
      accountNonLocked: boolean;
    }
  ];
}

export class Tenant implements ITenant {
  constructor(
    public resourceToIdMap: string,
    public name: string,
    public description: string,
    public id: string,
    public users: [
      {
        password: string;
        roles: [
          {
            authority: string;
            roleType: string;
          }
        ];
        credentialsNonExpired: boolean;
        accountNonExpired: boolean;
        id: string;
        enabled: boolean;
        authorities: [
          {
            authority: string;
            roleType: string;
          }
        ];
        username: string;
        accountNonLocked: boolean;
      }
    ]
  ) {}
}
