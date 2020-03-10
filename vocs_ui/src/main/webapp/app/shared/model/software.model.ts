export interface ISoftWare {
  id?: string;
  fileName?: string;
  version?: string;
  fileType?: string;
  size?: string;
  fileDownloadUri?: string;
  installedFolderPath?: string;
  cmdExecutors?: string[];
}

export class SoftwareModel implements ISoftWare {
  constructor(
    public id?: string,
    public fileName?: string,
    public version?: string,
    public fileType?: string,
    public size?: string,
    public fileDownloadUri?: string,
    public installedFolderPath?: string,
    public cmdExecutors?: string[]
  ) {}
}
