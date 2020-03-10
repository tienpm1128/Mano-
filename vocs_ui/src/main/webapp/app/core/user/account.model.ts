export class Account {
  constructor(
    public activated: boolean,
    public authorities: string[],
    public email: string,
    public firstName: string,
    public langKey: string,
    public lastName: string,
    public login: string,
    public imageUrl: string
  ) {}
}

export class MyAccount {
  constructor(
    public access_token?: string,
    public token_type?: string,
    public expires_in?: number,
    public role?: any,
    public loggedTime?: number,
    public username?: string
  ) {}
}
