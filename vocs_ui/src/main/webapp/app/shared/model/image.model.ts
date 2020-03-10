export interface IImage {
  id?: string;
  name?: string;
  provider?: string;
  version?: string;
  minRam?: string;
  minDisk?: string;
  diskFormat?: string;
  containerFormat?: string;
  createdDate?: string;
  updatedAt?: string;
  status?: string;
  userMetadata?: string;
}

export class ImageModel implements IImage {
  constructor(
    public id?: string,
    public name?: string,
    public provider?: string,
    public version?: string,
    public minRam?: string,
    public minDisk?: string,
    public diskFormat?: string,
    public containerFormat?: string,
    public createdDate?: string,
    public updatedAt?: string,
    public status?: string,
    public userMetadata?: string
  ) {}
}
